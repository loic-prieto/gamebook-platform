package org.sephire.gamebook.core.domain.model.book;

/**
 * A Node holds the content of a gamebook node. A step of the player inside the story.
 */
public class Node {
    private LocalizedText content;

    public Node(LocalizedText content) {
        this.content = content;
    }

    public LocalizedText getContent() {
        return content;
    }
}
