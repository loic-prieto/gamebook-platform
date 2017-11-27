package org.sephire.gamebook.core.domain.model.book;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.domain.shared.events.DomainEvent;

@RequiredArgsConstructor
public class GamebookDeletedEvent extends DomainEvent {
    @NonNull
    @Getter
    private Gamebook gamebook;
}
