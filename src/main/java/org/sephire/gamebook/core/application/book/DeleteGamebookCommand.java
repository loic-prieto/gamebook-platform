package org.sephire.gamebook.core.application.book;

import lombok.NonNull;
import lombok.Value;

@Value
public class DeleteGamebookCommand {
    @NonNull
    private String identifier;
}
