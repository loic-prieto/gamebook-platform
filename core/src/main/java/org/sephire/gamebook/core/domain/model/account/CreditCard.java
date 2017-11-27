package org.sephire.gamebook.core.domain.model.account;

import lombok.Value;

@Value
public class CreditCard {
    private String identifier;
    private String number;
    private String fullOwnerName;
}
