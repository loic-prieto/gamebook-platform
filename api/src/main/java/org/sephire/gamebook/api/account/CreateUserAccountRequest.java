package org.sephire.gamebook.api.account;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.sephire.gamebook.api.infrastructure.RequestDTO;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommand;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.Email;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateUserAccountRequest implements RequestDTO<CreateUserAccountCommand> {

    @NonNull
    private String email;
    @NonNull
    private String alias;

    @Override
    public CreateUserAccountCommand toCommand() {
        return new CreateUserAccountCommand(new Email(email), new Alias(alias));
    }
}
