package org.sephire.gamebook.core.application.book;

import io.vavr.Tuple;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
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
import org.sephire.gamebook.core.domain.model.book.LocalizedText;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.test.utils.BackendFailingMockBookRepository;
import org.sephire.gamebook.core.test.utils.MockEventEmitter;
import org.sephire.gamebook.core.test.utils.NoopMockBookRepository;
import org.sephire.gamebook.core.test.utils.RandomFailingMockBookRepository;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A find gamebooks sephire.org.gamebook command")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FindGamebooksCommandHandlerTest {
    private MockEventEmitter mockEventEmitter;

    @BeforeEach
    private void beforeEach() {
        this.mockEventEmitter = new MockEventEmitter();
    }

    @Test
    @DisplayName("when executed with valid params")
    public void testWithValidParams() {
        FindGamebooksCommandHandler handler = new FindGamebooksCommandHandler(new FindManyBookRepository(), mockEventEmitter);
        FindGamebooksCommand validParams = new FindGamebooksCommand(Option.of("title"));

        Either result = handler.execute(validParams);
        assertTrue(result.isRight(), "should return succesful result");
        assertEquals(0, mockEventEmitter.countFiredEvents(), "shouldn't have fired events");
    }

    @Test
    @DisplayName("when executed with a failing repository")
    public void testWithFailingRepository() {
        FindGamebooksCommandHandler handler = new FindGamebooksCommandHandler(new BackendFailingMockBookRepository(), mockEventEmitter);
        FindGamebooksCommand validParams = new FindGamebooksCommand(Option.of("title"));

        Either<List<CommandError>, Set<Gamebook>> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return an error list");
        boolean repositoryErrorDetected = !result.getLeft().find((error) -> error.getClass().equals(RepositoryError.class))
                .isEmpty();
        assertTrue(repositoryErrorDetected, "should return a repository error");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should have fired error event");
    }

    @Test
    @DisplayName("when executed with an unknown exception,")
    public void testWithRandomException() {
        FindGamebooksCommandHandler handler = new FindGamebooksCommandHandler(new RandomFailingMockBookRepository(), mockEventEmitter);
        FindGamebooksCommand validParams = new FindGamebooksCommand(Option.of("title"));

        Either<List<CommandError>, Set<Gamebook>> result = handler.execute(validParams);

        assertTrue(result.isLeft(), "should return an error list");
        boolean exceptionalErrorDetected = !result.getLeft().find((error) -> error.getClass().equals(ExceptionalError.class))
                .isEmpty();
        assertTrue(exceptionalErrorDetected, "should return an exceptional error");
        assertTrue(mockEventEmitter.hasFiredEventOfType(DomainException.class), "should have fired error event");
    }

    private class FindManyBookRepository extends NoopMockBookRepository {
        @Override
        public Set<Gamebook> findGamebooks(Option<String> titleFilter) {
            return HashSet.of(
                    Gamebook.minimalBook("1", new LocalizedText(Tuple.of(Locale.ENGLISH, "title")), "author1"),
                    Gamebook.minimalBook("2", new LocalizedText(Tuple.of(Locale.ENGLISH, "title 2")), "author2"),
                    Gamebook.minimalBook("3", new LocalizedText(Tuple.of(Locale.ENGLISH, "title 3")), "author3")
            );
        }
    }
}
