package org.sephire.gamebook.core.domain.model.book;

import io.vavr.collection.List;
import lombok.Value;

/**
 * A Node holds the content of a sephire.org.gamebook node. A step of the player inside the story.
 */
@Value
public class Node {
    private String identifier;
    private LocalizedText content;
    private List<Option> options;

}
