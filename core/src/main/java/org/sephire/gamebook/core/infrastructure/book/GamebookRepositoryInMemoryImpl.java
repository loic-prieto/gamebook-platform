package org.sephire.gamebook.core.infrastructure.book;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;

public class GamebookRepositoryInMemoryImpl implements GamebookRepository {
    private Map<String, Gamebook> gamebooks;

    public GamebookRepositoryInMemoryImpl() {
        gamebooks = HashMap.empty();
    }

    @Override
    public Option<Gamebook> findGamebook(String identifier) {
        return gamebooks.get(identifier);
    }

    @Override
    public Option<Gamebook> findGamebookByTitle(String title) {
        return gamebooks.values()
                .find(gamebook -> gamebook
                        .getTitle()
                        .getAllTitles()
                        .find(bookTitle -> bookTitle.equals(title))
                        .isDefined());
    }

    @Override
    public Set<Gamebook> findGamebooks(Option<String> titleFilter) {
        return titleFilter.isDefined() ?
                HashSet.ofAll(gamebooks.values()
                        .filter(gamebook ->
                                gamebook.getTitle().getAllTitles()
                                        .find(title -> title._2().contains(titleFilter.get()))
                                        .isDefined()
                        ))
                : HashSet.empty();
    }

    @Override
    public Gamebook storeGamebook(Gamebook gamebook) {
        gamebooks = gamebooks.put(gamebook.getIdentifier(), gamebook);
        return gamebook;
    }

    @Override
    public void deleteGamebook(Gamebook gamebook) {
        gamebooks = gamebooks.remove(gamebook.getIdentifier());
    }
}
