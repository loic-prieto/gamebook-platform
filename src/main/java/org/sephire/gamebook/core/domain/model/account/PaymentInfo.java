package org.sephire.gamebook.core.domain.model.account;

import io.vavr.collection.Set;
import lombok.Value;

@Value
public class PaymentInfo {
    private Set<BankAccount> bankAccounts;
    private Set<CreditCard> creditCards;
}
