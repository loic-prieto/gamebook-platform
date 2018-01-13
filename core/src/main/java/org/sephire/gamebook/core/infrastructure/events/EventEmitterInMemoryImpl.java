package org.sephire.gamebook.core.infrastructure.events;

import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.shared.events.DomainEvent;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;

public class EventEmitterInMemoryImpl implements EventEmitter {

    Map<Class<? extends DomainEvent>, List<Function1<? extends DomainEvent, ?>>> subscriptions;

    public void registerListener(Class<? extends DomainEvent> eventType, Function1<? extends DomainEvent, ?> listener) {
        List<Function1<? extends DomainEvent, ?>> newListeners = subscriptions
                .get(eventType).orElse(() -> Option.of(List.empty())).get()
                .append(listener);

        subscriptions = subscriptions.put(eventType, newListeners);
    }

    @Override
    public void fireEvent(DomainEvent event) {
        subscriptions.get(event.getClass())
                .peek(consumers -> {
                    for (Function1 f : consumers) {
                        f.apply(event);
                    }
                });
    }
}
