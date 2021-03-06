package org.sephire.gamebook.core.application.account;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.application.shared.commands.Command;
import org.sephire.gamebook.core.domain.model.account.Alias;

@RequiredArgsConstructor
public class GetUserAccountCommand implements Command {
    @NonNull
    @Getter
    private Alias userAlias;
}
