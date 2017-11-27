package org.sephire.gamebook.core.domain.model.account;

import lombok.Value;
import org.sephire.gamebook.core.domain.model.book.Gamebook;

@Value
public class GamebookInfo {
    private Gamebook book;
    private boolean enabled;
    private BankAccount bankAccount;
    private Price price;
}
