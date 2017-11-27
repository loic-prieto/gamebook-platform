package org.sephire.gamebook.core.domain.model.book;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.NonNull;
import lombok.Value;

/**
 * A book is the root entity that holds all of the content of
 * a sephire.org.gamebook:
 * - each node
 * - general config of the book
 */
@Value
public class Gamebook {
    @NonNull
    private String identifier;
    @NonNull
    private LocalizedText title;
    @NonNull
    private String author;
    private Set<Node> nodes;
    private Set<Genre> genres;

    private Gamebook(String identifier, LocalizedText title, String author) {
        this.identifier = identifier;
        this.title = title;
        this.author = author;
        this.nodes = HashSet.empty();
        this.genres = HashSet.empty();
    }

    public static Gamebook minimalBook(String identifier, LocalizedText title, String author) {
        return new Gamebook(identifier, title, author);
    }
}
