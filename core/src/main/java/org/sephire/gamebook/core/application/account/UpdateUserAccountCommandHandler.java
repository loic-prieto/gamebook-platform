package org.sephire.gamebook.core.application.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.ExceptionalError;
import org.sephire.gamebook.core.application.shared.commands.RepositoryError;
import org.sephire.gamebook.core.domain.model.account.UpdateUserAccountSpecification;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.model.account.UserAccountUpdatedEvent;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;
import org.sephire.gamebook.core.domain.shared.specifications.Specification;

public class UpdateUserAccountCommandHandler implements CommandHandler<UpdateUserAccountCommand, UserAccount> {

    private UserAccountRepository userAccountRepository;
    private EventEmitter eventEmitter;

    public UpdateUserAccountCommandHandler(UserAccountRepository userAccountRepository, EventEmitter eventEmitter) {
        this.userAccountRepository = userAccountRepository;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public Either<List<CommandError>, UserAccount> execute(UpdateUserAccountCommand command) {
        List<CommandError> errors = List.empty();
        UserAccount account = command.getAccount();

        try {
            Specification<UserAccount> specification = new UpdateUserAccountSpecification(userAccountRepository);
            if (specification.isSatisfiedBy(account)) {
                UserAccount updatedAccount = userAccountRepository.storeUserAccount(account);
                eventEmitter.fireEvent(new UserAccountUpdatedEvent(updatedAccount, account));
            } else {
                errors = errors.append(new InvalidUserAccountError());
            }
        } catch (RepositoryException e) {
            errors = errors.append(new RepositoryError());
            eventEmitter.fireEvent(new DomainException(e));
        } catch (Throwable t) {
            errors = errors.append(new ExceptionalError(t));
            eventEmitter.fireEvent(new DomainException(t));
        }

        return errors.isEmpty() ?
                Either.right(account) :
                Either.left(errors);
    }
}
