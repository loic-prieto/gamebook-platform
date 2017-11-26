package org.sephire.gamebook.core.domain.shared.events;

import lombok.Getter;

/**
 * This a domain event that represents an error that was produced
 * while running the application. Mainly infrastructure errors, but also
 * unexpected code errors.
 * We need errors to be registered but also not to leak into the consumer.
 * The role of a DomainError is to be fired to interested automated error
 * handlers.
 */
public class DomainError extends DomainEvent {
    @Getter
    private Throwable exception;

    public DomainError(Throwable exception) {
        this.exception = exception;
    }
}
