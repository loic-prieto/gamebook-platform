package org.sephire.gamebook.core.domain.model.account;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.shared.events.DomainEvent;

@RequiredArgsConstructor
public class GamebookAcquiredEvent extends DomainEvent {
    @NonNull
    @Getter
    private Gamebook acquiredGamebook;
    @NonNull
    @Getter
    private User acquiringUser;
}
