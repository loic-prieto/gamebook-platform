package org.sephire.gamebook.core.test.utils;

import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;


/**
 * This is a test book repository that fails with a repository exception in every operation.
 */
public class BackendFailingMockBookRepository implements GamebookRepository {
    @Override
    public Option<Gamebook> findGamebook(String identifier) {
        throw new RepositoryException();
    }

    @Override
    public Option<Gamebook> findGamebookByTitle(String title) {
        throw new RepositoryException();
    }

    @Override
    public Gamebook storeGamebook(Gamebook gamebook) {
        throw new RepositoryException();
    }

    @Override
    public void deleteGamebook(Gamebook gamebook) {
        throw new RepositoryException();
    }

    @Override
    public Set<Gamebook> findGamebooks(Option<String> titleFilter) {
        throw new RepositoryException();
    }
}