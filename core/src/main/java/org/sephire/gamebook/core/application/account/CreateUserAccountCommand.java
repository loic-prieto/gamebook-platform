package org.sephire.gamebook.core.application.account;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateUserAccountCommand {
    @NonNull
    private String email;
    @NonNull
    private String alias;
}
