package org.sephire.gamebook.core.application.account;

import lombok.NonNull;
import lombok.Value;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.Email;

@Value
public class CreateUserAccountCommand {
    @NonNull
    private Email email;
    @NonNull
    private Alias alias;
}
