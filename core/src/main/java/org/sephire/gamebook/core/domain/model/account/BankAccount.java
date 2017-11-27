package org.sephire.gamebook.core.domain.model.account;

import lombok.Value;

@Value
public class BankAccount {
    private String identifier;
    private String IBAN;
    private String ownerFullName;
}
