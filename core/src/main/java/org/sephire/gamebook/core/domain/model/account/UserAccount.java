package org.sephire.gamebook.core.domain.model.account;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.NonNull;
import lombok.Value;
import org.sephire.gamebook.core.domain.model.book.Gamebook;

@Value
public class UserAccount {
    @NonNull
    private User user;
    @NonNull
    private AccountType accountType;
    private PaymentInfo paymentInfo;
    private Set<GamebookInfo> writtenBooks;
    private Set<Gamebook> ownedBooks;

    public static UserAccount minimalAccount(String userEmail, String userAlias) {
        return new UserAccount(
                User.minimalUser(userEmail, userAlias),
                AccountType.Basic,
                null,
                HashSet.empty(),
                HashSet.empty()
        );
    }

    public UserAccount updateUserData(User user) {
        return new UserAccount(user, accountType, paymentInfo, writtenBooks, ownedBooks);
    }

    public UserAccount addGamebook(Gamebook gamebook) {
        return new UserAccount(user, accountType, paymentInfo, writtenBooks, ownedBooks.add(gamebook));
    }
}
