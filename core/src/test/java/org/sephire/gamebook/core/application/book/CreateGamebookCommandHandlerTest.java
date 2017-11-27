package org.sephire.gamebook.core.application.book;

import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.application.shared.commands.ExceptionalError;
import org.sephire.gamebook.core.application.shared.commands.RepositoryError;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookCreatedEvent;
import org.sephire.gamebook.core.domain.model.book.LocalizedText;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;
import org.sephire.gamebook.core.test.utils.MockEventEmitter;
import org.sephire.gamebook.core.test.utils.NoopMockBookRepository;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A create sephire.org.gamebook command,")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateGamebookCommandHandlerTest {

    private MockEventEmitter mockEventEmitter;

    @BeforeEach
    private void beforeEach() {
        this.mockEventEmitter = new MockEventEmitter();
    }

    @DisplayName("when executed with valid parameters,")
    @Test
    public void testWithValidParams() {
        CreateGamebookCommandHandler handler = new CreateGamebookCommandHandler(new NoopMockBookRepository(), mockEventEmitter);
        CreateGamebookCommand validParams = new CreateGamebookCommand(
                "id",
                "author",
                new LocalizedText(Tuple.of(Locale.ENGLISH, "test title")));

        Either result = handler.execute(validParams);

        assertTrue(result.isRight(), "should return succesful result");
        assertTrue(mockEventEmitter.hasFiredEventOfType(GamebookCreatedEvent.class), "should fire a GamebookCreatedEvent event");
    }

    @DisplayName("when executed with an already taken title")
    public void testWithAlreadyTakenTitle() {
        CreateGamebookCommandHandler handler = new CreateGamebookCommandHandler(new AlreadyTakenBookRepository(), mockEventEmitter);
        CreateGamebookCommand validParams = new CreateGamebookCommand(
                "id",
                "author",
                new LocalizedText(Tuple.of(Locale.ENGLISH, "test title")));
        Either<List<CommandError>, Gamebook> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return an error list");
        boolean invalidBookErrorDetected = !result.left().get().find((error) -> error.getClass().equals(InvalidGamebookError.class))
                .isEmpty();
        assertTrue(invalidBookErrorDetected, "should return an invalid book error");
    }

    @DisplayName("when executed with a failing storage,")
    @Test
    public void testWithFailingStorage() {
        CreateGamebookCommandHandler handler = new CreateGamebookCommandHandler(new FailingMockBookRepository(), mockEventEmitter);
        CreateGamebookCommand validParams = new CreateGamebookCommand(
                "id",
                "author",
                new LocalizedText(Tuple.of(Locale.ENGLISH, "test title")));
        Either<List<CommandError>, Gamebook> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return an error list");
        boolean repositoryErrorDetected = !result.left().get().find((error) -> error.getClass().equals(RepositoryError.class))
                .isEmpty();
        assertTrue(repositoryErrorDetected, "should return a repository error");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should have fired error event");
    }

    @DisplayName("when executed with an unknown exception,")
    @Test
    public void testWithRandomException() {
        CreateGamebookCommandHandler handler = new CreateGamebookCommandHandler(new RandomExceptionBookRepository(), mockEventEmitter);
        CreateGamebookCommand validParams = new CreateGamebookCommand(
                "id",
                "author",
                new LocalizedText(Tuple.of(Locale.ENGLISH, "test title")));
        Either<List<CommandError>, Gamebook> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return an error list");
        boolean exceptionalErrorDetected = !result.left().get().find((error) -> error.getClass().equals(ExceptionalError.class))
                .isEmpty();
        assertTrue(exceptionalErrorDetected, "should return an exceptional error");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should have fired error event");
    }

    private class FailingMockBookRepository extends NoopMockBookRepository {
        @Override
        public Gamebook storeGamebook(Gamebook gamebook) {
            throw new RepositoryException();
        }
    }

    private class RandomExceptionBookRepository extends NoopMockBookRepository {
        @Override
        public Option<Gamebook> findGamebookByTitle(String title) {
            throw new RuntimeException();
        }
    }

    private class AlreadyTakenBookRepository extends NoopMockBookRepository {
        @Override
        public Option<Gamebook> findGamebookByTitle(String title) {
            return Option.of(Gamebook.minimalBook("id", new LocalizedText(Tuple.of(Locale.ENGLISH, "test")), "author"));
        }
    }

}
