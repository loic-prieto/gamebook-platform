package org.sephire.gamebook.core.test.utils;

import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;


/**
 * This is a test book repository that fails with a runtime exception in every operation.
 */
public class RandomFailingMockBookRepository implements GamebookRepository {
    @Override
    public Option<Gamebook> findGamebook(String identifier) {
        throw new RuntimeException();
    }

    @Override
    public Option<Gamebook> findGamebookByTitle(String title) {
        throw new RuntimeException();
    }

    @Override
    public Gamebook storeGamebook(Gamebook gamebook) {
        throw new RuntimeException();
    }

    @Override
    public void deleteGamebook(Gamebook gamebook) {
        throw new RuntimeException();
    }

    @Override
    public Set<Gamebook> findGamebooks(Option<String> titleFilter) {
        throw new RuntimeException();
    }
}