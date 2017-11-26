package org.serphire.gamebook.core.test.utils;

import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;

/**
 * This is a test book repository that mimicks a repository with no
 * registered books whatsoever and that won't store anything.
 */
public class NoopMockBookRepository implements GamebookRepository {
    @Override
    public Option<Gamebook> findGamebook(String identifier) {
        return Option.none();
    }

    @Override
    public Option<Gamebook> findGamebookByTitle(String title) {
        return Option.none();
    }

    @Override
    public Gamebook storeGamebook(Gamebook gamebook) {
        return gamebook;
    }

    @Override
    public void deleteGamebook(Gamebook gamebook) {
    }
}