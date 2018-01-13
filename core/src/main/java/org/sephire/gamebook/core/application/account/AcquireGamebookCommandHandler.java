package org.sephire.gamebook.core.application.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.ExceptionalError;
import org.sephire.gamebook.core.application.shared.commands.RepositoryError;
import org.sephire.gamebook.core.domain.model.account.GamebookAcquiredEvent;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.model.account.UserAccountUpdatedEvent;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;

public class AcquireGamebookCommandHandler implements CommandHandler<AcquireGamebookCommand, UserAccount> {

    private UserAccountRepository userAccountRepository;
    private GamebookRepository gamebookRepository;
    private EventEmitter eventEmitter;

    public AcquireGamebookCommandHandler(UserAccountRepository userAccountRepository, GamebookRepository gamebookRepository, EventEmitter eventEmitter) {
        this.userAccountRepository = userAccountRepository;
        this.gamebookRepository = gamebookRepository;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public Either<List<CommandError>, UserAccount> execute(AcquireGamebookCommand command) {
        List<CommandError> errors = List.empty();
        UserAccount updatedAccount = null;

        try {
            UserAccount account = userAccountRepository.findUserAccount(command.getUserAlias())
                    .getOrElseThrow(() -> new UserNotFoundError(command.getUserAlias()));
            Gamebook gamebook = gamebookRepository.findGamebook(command.getGamebookIdentifier())
                    .getOrElseThrow(() -> new GamebookNotFoundError());
            updatedAccount = account.addGamebook(gamebook);
            userAccountRepository.storeUserAccount(updatedAccount);
            eventEmitter.fireEvent(new GamebookAcquiredEvent(gamebook, updatedAccount.getUser()));
            eventEmitter.fireEvent(new UserAccountUpdatedEvent(updatedAccount, account));
        } catch (CommandError e) {
            errors = errors.append(e);
            eventEmitter.fireEvent(new DomainException(e));
        } catch (RepositoryException e) {
            errors = errors.append(new RepositoryError());
            eventEmitter.fireEvent(new DomainException(e));
        } catch (Throwable t) {
            errors = errors.append(new ExceptionalError(t));
            eventEmitter.fireEvent(new DomainException(t));
        }

        return errors.isEmpty() ?
                Either.right(updatedAccount) :
                Either.left(errors);
    }
}
