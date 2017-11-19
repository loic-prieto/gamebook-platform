package org.sephire.gamebook.core.domain.model.book;

public interface GamebookRepository {
    public Gamebook findGamebook(String identifier);

    public Gamebook storeGamebook(Gamebook gamebook);

    public void deleteGamebook(Gamebook gamebook);
}
