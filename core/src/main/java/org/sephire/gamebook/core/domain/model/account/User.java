package org.sephire.gamebook.core.domain.model.account;

import lombok.NonNull;
import lombok.Value;

@Value
public class User {
    @NonNull
    private Email email;
    private UserName name;
    private Alias alias;

}
