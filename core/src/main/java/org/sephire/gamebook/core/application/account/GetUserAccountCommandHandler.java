package org.sephire.gamebook.core.application.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.ExceptionalError;
import org.sephire.gamebook.core.application.shared.commands.RepositoryError;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;

public class GetUserAccountCommandHandler implements CommandHandler<GetUserAccountCommand, Option<UserAccount>> {

    private UserAccountRepository userAccountRepository;
    private EventEmitter eventEmitter;

    public GetUserAccountCommandHandler(UserAccountRepository userAccountRepository, EventEmitter eventEmitter) {
        this.userAccountRepository = userAccountRepository;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public Either<List<CommandError>, Option<UserAccount>> execute(GetUserAccountCommand command) {
        List<CommandError> errors = List.empty();
        Option<UserAccount> userAccount = Option.none();

        try {
            userAccount = userAccountRepository.findUserAccount(command.getUserAlias());
        } catch (RepositoryException e) {
            errors = errors.append(new RepositoryError());
            eventEmitter.fireEvent(new DomainException(e));
        } catch (Throwable t) {
            errors = errors.append(new ExceptionalError(t));
            eventEmitter.fireEvent(new DomainException(t));
        }

        return errors.isEmpty() ?
                Either.right(userAccount) :
                Either.left(errors);
    }
}
