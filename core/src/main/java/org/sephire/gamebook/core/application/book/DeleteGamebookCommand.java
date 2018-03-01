package org.sephire.gamebook.core.application.book;

import lombok.NonNull;
import lombok.Value;
import org.sephire.gamebook.core.application.shared.commands.Command;

@Value
public class DeleteGamebookCommand implements Command {
    @NonNull
    private String identifier;
    @NonNull
    private String author;
}
