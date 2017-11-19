package org.sephire.gamebook.core.domain.model.book;

import io.vavr.collection.Set;
import lombok.Value;

/**
 * A book is the root entity that holds all of the content of
 * a gamebook:
 * - each node
 * - general config of the book
 */
@Value
public class Gamebook {
    private LocalizedText title;
    private Set<Node> nodes;
    private String author;
    private Set<Genre> genre;

}
