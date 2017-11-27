package org.sephire.gamebook.core.domain.shared.events;

import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * A domain event
 */
public abstract class DomainEvent {
    @Getter
    private ZonedDateTime dateTime = ZonedDateTime.now();
}
