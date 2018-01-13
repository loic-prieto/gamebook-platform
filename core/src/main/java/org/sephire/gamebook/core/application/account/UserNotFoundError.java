package org.sephire.gamebook.core.application.account;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.Alias;

@RequiredArgsConstructor
public class UserNotFoundError extends CommandError {
    @NonNull
    @Getter
    private Alias notFoundUserAlias;
}
