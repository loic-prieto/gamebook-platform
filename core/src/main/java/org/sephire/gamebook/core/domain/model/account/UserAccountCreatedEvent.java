package org.sephire.gamebook.core.domain.model.account;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.domain.shared.events.DomainEvent;

@RequiredArgsConstructor
public class UserAccountCreatedEvent extends DomainEvent {
    @Getter
    @NonNull
    private UserAccount userAccount;
}
