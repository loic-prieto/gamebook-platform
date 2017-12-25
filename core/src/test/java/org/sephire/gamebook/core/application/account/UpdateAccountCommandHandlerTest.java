package org.sephire.gamebook.core.application.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.Email;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountUpdatedEvent;
import org.sephire.gamebook.core.test.utils.MockEventEmitter;
import org.sephire.gamebook.core.test.utils.NoopUserAccountRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("An update account command,")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateAccountCommandHandlerTest {

    private MockEventEmitter mockEventEmitter;

    @BeforeEach
    private void beforeEach() {
        this.mockEventEmitter = new MockEventEmitter();
    }

    @DisplayName("when executed with valid parameters,")
    @Test
    public void testWithValidParams() {
        UpdateUserAccountCommandHandler handler = new UpdateUserAccountCommandHandler(new UserExistsUserAccountRepository(), mockEventEmitter);
        UserAccount updatingUserAccount = UserAccount.minimalAccount(new Email("test"), new Alias("alias"));
        updatingUserAccount = updatingUserAccount.updateUserData(updatingUserAccount.getUser().changeEmail(new Email("test2@mail")));

        UpdateUserAccountCommand validParams = new UpdateUserAccountCommand(updatingUserAccount);

        Either<List<CommandError>, UserAccount> result = handler.execute(validParams);

        assertTrue(result.isRight(), "should return succesful result");
        assertTrue(mockEventEmitter.hasFiredEventOfType(UserAccountUpdatedEvent.class), "should fire a UserAccountCreated event");
    }

    @DisplayName("when executed with invalid parameters,")
    @Test
    public void testWithInvalidParams() {
        UpdateUserAccountCommandHandler handler = new UpdateUserAccountCommandHandler(new FindsNoUserUserAccountRepository(), mockEventEmitter);
        UserAccount updatingUserAccount = UserAccount.minimalAccount(new Email("test"), new Alias("alias"));
        UpdateUserAccountCommand invalidParams = new UpdateUserAccountCommand(updatingUserAccount);

        Either<List<CommandError>, UserAccount> result = handler.execute(invalidParams);

        assertTrue(result.isLeft(), "should return errors");
    }

    private class UserExistsUserAccountRepository extends NoopUserAccountRepository {
        @Override
        public Option<UserAccount> findUserAccount(Alias userAlias) {
            return Option.of(UserAccount.minimalAccount(new Email("test"), userAlias));
        }
    }

    private class FindsNoUserUserAccountRepository extends NoopUserAccountRepository {
        @Override
        public Option<UserAccount> findUserAccount(Alias userAlias) {
            return Option.none();
        }
    }
}
