package org.sephire.gamebook.core.application.book;

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
import org.sephire.gamebook.core.domain.model.book.GamebookDeletedEvent;
import org.sephire.gamebook.core.domain.model.book.LocalizedText;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;
import org.sephire.gamebook.core.test.utils.MockEventEmitter;
import org.sephire.gamebook.core.test.utils.NoopMockBookRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A delete sephire.org.gamebook command")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteGamebookCommandHandlerTest {

    private MockEventEmitter mockEventEmitter;

    @BeforeEach
    private void beforeEach() {
        this.mockEventEmitter = new MockEventEmitter();
    }

    @Test
    @DisplayName("when executed with valid params")
    public void testWithValidParams() {
        DeleteGamebookCommandHandler handler = new DeleteGamebookCommandHandler(new ReturnSameAuthorMockBookRepository("test"), mockEventEmitter);
        DeleteGamebookCommand validParams = new DeleteGamebookCommand("test1", "test");

        Either result = handler.execute(validParams);
        assertTrue(result.isRight(), "should return succesful result");
        assertTrue(mockEventEmitter.hasFiredEventOfType(GamebookDeletedEvent.class), "should fire a GamebookDeletedEvent event");
    }

    @Test
    @DisplayName("when executed with an unauthorized author")
    public void testWithInvalidAuthor() {
        DeleteGamebookCommandHandler handler = new DeleteGamebookCommandHandler(new ReturnSameAuthorMockBookRepository("anotherAuthor"), mockEventEmitter);
        DeleteGamebookCommand validParams = new DeleteGamebookCommand("test1", "test");

        Either<List<CommandError>, Void> result = handler.execute(validParams);
        assertTrue(result.isLeft(), "should return failing result");
        boolean correctError = !result.getLeft().find((error) -> error.getClass().equals(NotAuthorizedToDeleteGamebookError.class)).isEmpty();
        assertTrue(correctError, "should generate a not authorized error");
    }

    @Test
    @DisplayName("when executed with a failing repository")
    public void testWithFailingRepository() {
        DeleteGamebookCommandHandler handler = new DeleteGamebookCommandHandler(new FailingRepoMock("test"), mockEventEmitter);
        DeleteGamebookCommand validParams = new DeleteGamebookCommand("test1", "test");

        Either<List<CommandError>, Void> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return an error list");
        boolean repositoryErrorDetected = !result.getLeft().find((error) -> error.getClass().equals(RepositoryError.class))
                .isEmpty();
        assertTrue(repositoryErrorDetected, "should return a repository error");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should have fired error event");
    }

    @Test
    @DisplayName("when executed with an unknown exception,")
    public void testWithRandomException() {
        DeleteGamebookCommandHandler handler = new DeleteGamebookCommandHandler(new RandomFailingRepoMock(), mockEventEmitter);
        DeleteGamebookCommand validParams = new DeleteGamebookCommand("test1", "test");

        Either<List<CommandError>, Void> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return an error list");
        boolean exceptionalErrorDetected = !result.getLeft().find((error) -> error.getClass().equals(ExceptionalError.class))
                .isEmpty();
        assertTrue(exceptionalErrorDetected, "should return an exceptional error");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should have fired error event");
    }

    private class ReturnSameAuthorMockBookRepository extends NoopMockBookRepository {
        private String author;

        public ReturnSameAuthorMockBookRepository(String author) {
            this.author = author;
        }

        @Override
        public Option<Gamebook> findGamebook(String identifier) {
            return Option.of(Gamebook.minimalBook(identifier, new LocalizedText(), author));
        }
    }

    private class FailingRepoMock extends ReturnSameAuthorMockBookRepository {

        public FailingRepoMock(String author) {
            super(author);
        }

        @Override
        public void deleteGamebook(Gamebook gamebook) {
            throw new RepositoryException();
        }
    }

    private class RandomFailingRepoMock extends NoopMockBookRepository {
        @Override
        public Option<Gamebook> findGamebook(String identifier) {
            throw new RuntimeException();
        }
    }
}
