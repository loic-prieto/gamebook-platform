package org.sephire.gamebook.awsapi.account.dtos;

import lombok.*;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommand;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.Email;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CreateUserAccountRequest {
    @NonNull
    private String email;
    @NonNull
    private String alias;

    public CreateUserAccountCommand toCommand() {
        return new CreateUserAccountCommand(new Email(email), new Alias(alias));
    }
}
