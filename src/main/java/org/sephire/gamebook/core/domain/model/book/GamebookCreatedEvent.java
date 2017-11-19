package org.sephire.gamebook.core.domain.model.book;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.domain.shared.events.DomainEvent;

/**
 * A gamebook was created. Holds information about the gamebook.
 */
@RequiredArgsConstructor
public class GamebookCreatedEvent extends DomainEvent {
    @Getter
    @NonNull
    private Gamebook gamebook;
}
