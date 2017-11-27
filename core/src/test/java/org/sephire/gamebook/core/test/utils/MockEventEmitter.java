package org.sephire.gamebook.core.test.utils;

import io.vavr.collection.List;
import org.sephire.gamebook.core.domain.shared.events.DomainEvent;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;

/**
 * This allows to register fired events to consult while testing.
 */
public class MockEventEmitter implements EventEmitter {

    private List<DomainEvent> firedEvents = List.empty();

    @Override
    public void fireEvent(DomainEvent event) {
        firedEvents = firedEvents.append(event);
    }

    public int countFiredEvents() {
        return firedEvents.length();
    }

    public boolean hasFiredEventOfType(Class<? extends DomainEvent> eventType) {
        return firedEvents
                .filter((event) -> event.getClass().equals(eventType))
                .length() > 0;
    }
}
