package org.sephire.gamebook.core.application.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.*;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;
import org.sephire.gamebook.core.test.utils.MockEventEmitter;
import org.sephire.gamebook.core.test.utils.NoopUserAccountRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A create account command,")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateAccountCommandHandlerTest {
    private MockEventEmitter mockEventEmitter;

    @BeforeEach
    private void beforeEach() {
        this.mockEventEmitter = new MockEventEmitter();
    }

    @DisplayName("when executed with valid parameters,")
    @Test
    public void testWithValidParams() {
        CreateUserAccountCommandHandler handler = new CreateUserAccountCommandHandler(new NoopUserAccountRepository(), mockEventEmitter);
        CreateUserAccountCommand validParams = new CreateUserAccountCommand("test@test.test", "alias");

        Either<List<CommandError>, UserAccount> result = handler.execute(validParams);

        assertTrue(result.isRight(), "should return succesful result");
        assertTrue(result.right().get().getAccountType().equals(AccountType.Basic), "The default account type should be Basic");
        assertTrue(mockEventEmitter.hasFiredEventOfType(UserAccountCreatedEvent.class), "should fire a UserAccountCreated event");
    }

    @DisplayName("when executed with a failing repository,")
    @Test
    public void testWithFailingRepository() {
        CreateUserAccountCommandHandler handler = new CreateUserAccountCommandHandler(new FailingUserAccountRepository(), mockEventEmitter);
        CreateUserAccountCommand validParams = new CreateUserAccountCommand("test@test.test", "alias");

        Either<List<CommandError>, UserAccount> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return errors");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should fire a DomainException event");
    }

    @DisplayName("when executed with a random exception,")
    @Test
    public void testWithRandomException() {
        CreateUserAccountCommandHandler handler = new CreateUserAccountCommandHandler(new RandomExceptionUserAccountRepository(), mockEventEmitter);
        CreateUserAccountCommand validParams = new CreateUserAccountCommand("test@test.test", "alias");

        Either<List<CommandError>, UserAccount> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return errors");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should fire a DomainException event");
    }

    @DisplayName("when the alias has already been taken by another user,")
    @Test
    public void testWithAlreadyTakenAlias() {
        CreateUserAccountCommandHandler handler = new CreateUserAccountCommandHandler(new AliasAlreadyTakenUserAccountRepository(), mockEventEmitter);
        CreateUserAccountCommand invalidParams = new CreateUserAccountCommand("test@test.test", "alias");

        Either<List<CommandError>, UserAccount> result = handler.execute(invalidParams);

        assertTrue(result.isLeft(), "should return errors");
    }

    private class FailingUserAccountRepository extends NoopUserAccountRepository {
        @Override
        public UserAccount storeUserAccount(UserAccount userAccount) {
            throw new RepositoryException();
        }
    }

    private class RandomExceptionUserAccountRepository extends NoopUserAccountRepository {
        @Override
        public UserAccount storeUserAccount(UserAccount userAccount) {
            throw new RuntimeException();
        }
    }

    private class AliasAlreadyTakenUserAccountRepository extends NoopUserAccountRepository {
        @Override
        public Option<UserAccount> findUserAccount(Alias userAlias) {
            return Option.of(UserAccount.minimalAccount(new Email("test@mail"), userAlias));
        }
    }
}
