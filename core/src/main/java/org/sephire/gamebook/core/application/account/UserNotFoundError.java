package org.sephire.gamebook.core.application.account;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.application.shared.commands.CommandClientError;
import org.sephire.gamebook.core.domain.model.account.Alias;

/**
 * This is an exception that is thrown when an operation tries to operate
 * with a user (identified by its alias) but it isn't found.
 */
@RequiredArgsConstructor
public class UserNotFoundError extends CommandClientError {
    @NonNull
    @Getter
    private Alias notFoundUserAlias;
}
