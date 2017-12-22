package org.sephire.gamebook.core.application.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.ExceptionalError;
import org.sephire.gamebook.core.application.shared.commands.RepositoryError;
import org.sephire.gamebook.core.domain.model.account.NewUserAccountSpecification;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountCreatedEvent;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;
import org.sephire.gamebook.core.domain.shared.specifications.Specification;

public class CreateUserAccountCommandHandler implements CommandHandler<CreateUserAccountCommand, UserAccount> {

    private UserAccountRepository userAccountRepository;
    private EventEmitter eventEmitter;

    public CreateUserAccountCommandHandler(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Either<List<CommandError>, UserAccount> execute(CreateUserAccountCommand command) {
        List<CommandError> errors = List.empty();
        UserAccount account = null;

        try {
            account = UserAccount.minimalAccount(command.getEmail(), command.getEmail());
            Specification<UserAccount> specification = new NewUserAccountSpecification(userAccountRepository);
            if (specification.isSatisfiedBy(account)) {
                account = userAccountRepository.storeUserAccount(account);
                eventEmitter.fireEvent(new UserAccountCreatedEvent(account));
            } else {
                errors.append(new InvalidUserAccountError());
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
