package org.sephire.gamebook.core.domain.model.account;

import io.vavr.collection.Set;
import lombok.Value;
import org.sephire.gamebook.core.domain.model.book.Gamebook;

@Value
public class UserAccount {
    private User user;
    private AccountType accountType;
    private PaymentInfo paymentInfo;
    private Set<GamebookInfo> writtenBooks;
    private Set<Gamebook> ownedBooks;

}
