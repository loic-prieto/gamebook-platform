package org.sephire.gamebook.core.domain.model.account;

import lombok.NonNull;
import lombok.Value;

@Value
public class UserName {
    @NonNull
    private String firstName;
    @NonNull
    private String surname;
}
