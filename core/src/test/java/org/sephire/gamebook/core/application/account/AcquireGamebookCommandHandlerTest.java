package org.sephire.gamebook.core.application.account;

import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.*;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.LocalizedText;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.test.utils.*;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("An acquire gamebook account command,")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AcquireGamebookCommandHandlerTest {

    private MockEventEmitter mockEventEmitter;

    @BeforeEach
    private void beforeEach() {
        this.mockEventEmitter = new MockEventEmitter();
    }

    @DisplayName("when executed with valid parameters,")
    @Test
    public void testWithValidParams() {
        AcquireGamebookCommandHandler handler = new AcquireGamebookCommandHandler(new FindUserUARepository(), new FindBookGamebookRepository(), mockEventEmitter);
        AcquireGamebookCommand validParams = new AcquireGamebookCommand("id1", new Alias("user1"));

        Either<List<CommandError>, UserAccount> result = handler.execute(validParams);

        assertTrue(result.isRight(), "should return succesful result");
        assertEquals(1, result.right().get().getOwnedBooks().length(), "should have added the gamebook to the account");
        assertTrue(mockEventEmitter.hasFiredEventOfType(GamebookAcquiredEvent.class), "should fire a GamebookAcquiredEvent event");
        assertTrue(mockEventEmitter.hasFiredEventOfType(UserAccountUpdatedEvent.class), "should fire a UserAccountUpdatedEvent event");
    }

    @DisplayName("when executed with an invalid user account,")
    @Test
    public void testWithInvalidUserParam() {
        AcquireGamebookCommandHandler handler = new AcquireGamebookCommandHandler(new NoopUserAccountRepository(), new FindBookGamebookRepository(), mockEventEmitter);
        AcquireGamebookCommand invalidParams = new AcquireGamebookCommand("id1", new Alias("user1"));

        Either<List<CommandError>, UserAccount> result = handler.execute(invalidParams);

        assertTrue(result.isLeft(), "should return errors");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should have fired a domain exception");

    }

    @DisplayName("when executed with an invalid gamebook,")
    @Test
    public void testWithInvalidGamebookParam() {
        AcquireGamebookCommandHandler handler = new AcquireGamebookCommandHandler(new FindUserUARepository(), new NoopMockBookRepository(), mockEventEmitter);
        AcquireGamebookCommand invalidParams = new AcquireGamebookCommand("id1", new Alias("user1"));

        Either<List<CommandError>, UserAccount> result = handler.execute(invalidParams);

        assertTrue(result.isLeft(), "should return errors");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should have fired a domain exception");
    }

    @DisplayName("when executed with a failing repository,")
    @Test
    public void testWithFailingRepository() {
        AcquireGamebookCommandHandler handler = new AcquireGamebookCommandHandler(new BackendFailingUserAccountRepository(), new BackendFailingMockBookRepository(), mockEventEmitter);
        AcquireGamebookCommand validParams = new AcquireGamebookCommand("id1", new Alias("user1"));

        Either<List<CommandError>, UserAccount> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return errors");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should fire a DomainException event");
    }

    @DisplayName("when executed with a random exception,")
    @Test
    public void testWithRandomException() {
        AcquireGamebookCommandHandler handler = new AcquireGamebookCommandHandler(new RandomFailingUserAccountRepository(), new RandomFailingMockBookRepository(), mockEventEmitter);
        AcquireGamebookCommand validParams = new AcquireGamebookCommand("id1", new Alias("user1"));

        Either<List<CommandError>, UserAccount> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return errors");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should fire a DomainException event");
    }

    private class FindUserUARepository extends NoopUserAccountRepository {
        @Override
        public Option<UserAccount> findUserAccount(Alias userAlias) {
            return Option.of(UserAccount.minimalAccount(new Email("mail"), userAlias));
        }
    }

    private class FindBookGamebookRepository extends NoopMockBookRepository {
        @Override
        public Option<Gamebook> findGamebook(String identifier) {
            return Option.of(Gamebook.minimalBook(identifier, new LocalizedText(Tuple.of(Locale.ENGLISH, "title")), "author"));
        }
    }
}
