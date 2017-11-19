package org.sephire.gamebook.core.domain.shared.events;

/**
 * The domain event emitter should fire events to allow listeners to react
 * to them
 */
public interface EventEmitter {
    public void fireEvent(DomainEvent event);
}
