package org.sephire.gamebook.api.account;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.api.infrastructure.RequestDTO;
import org.sephire.gamebook.core.application.account.GetUserAccountCommand;
import org.sephire.gamebook.core.domain.model.account.Alias;

@RequiredArgsConstructor
public class GetUserAccountRequest implements RequestDTO<GetUserAccountCommand> {
    @NonNull
    @Getter
    private String userAlias;

    @Override
    public GetUserAccountCommand toCommand() {
        return new GetUserAccountCommand(new Alias(userAlias));
    }
}
