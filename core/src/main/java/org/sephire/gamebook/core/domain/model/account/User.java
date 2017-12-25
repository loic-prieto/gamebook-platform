package org.sephire.gamebook.core.domain.model.account;

import lombok.NonNull;
import lombok.Value;

@Value
public class User {
    @NonNull
    private Email email;
    private UserName name;
    @NonNull
    private Alias alias;

    public static User minimalUser(Email userEmail, Alias alias) {
        return new User(userEmail, null, alias);
    }

    public User changeEmail(Email email) {
        return new User(email, name, alias);
    }

    public User changeUsername(UserName userName) {
        return new User(email, userName, alias);
    }
}
