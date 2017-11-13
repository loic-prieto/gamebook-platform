package org.sephire.gamebook.core.domain.model.book;

import io.vavr.collection.List;

/**
 * A book is the root entity that holds all of the content of
 * a gamebook:
 * - each node
 * - general config of the book
 */
public class Gamebook {
    private LocalizedText title;
    private List<Node> nodes;
}
