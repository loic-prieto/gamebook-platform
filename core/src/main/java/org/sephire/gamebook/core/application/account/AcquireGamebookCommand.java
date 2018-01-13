package org.sephire.gamebook.core.application.account;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.domain.model.account.Alias;

@RequiredArgsConstructor
public class AcquireGamebookCommand {
    @NonNull
    @Getter
    private String gamebookIdentifier;
    @NonNull
    @Getter
    private Alias userAlias;
}
