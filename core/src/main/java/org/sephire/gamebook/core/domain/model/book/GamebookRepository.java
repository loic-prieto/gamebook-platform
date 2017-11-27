package org.sephire.gamebook.core.domain.model.book;

import io.vavr.control.Option;

public interface GamebookRepository {
    public Option<Gamebook> findGamebook(String identifier);

    public Option<Gamebook> findGamebookByTitle(String title);

    public Gamebook storeGamebook(Gamebook gamebook);

    public void deleteGamebook(Gamebook gamebook);
}
