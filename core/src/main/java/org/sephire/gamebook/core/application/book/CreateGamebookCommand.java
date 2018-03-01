package org.sephire.gamebook.core.application.book;

import lombok.NonNull;
import lombok.Value;
import org.sephire.gamebook.core.application.shared.commands.Command;
import org.sephire.gamebook.core.domain.model.book.LocalizedText;

@Value
public class CreateGamebookCommand implements Command {
    @NonNull
    private String identifier;
    @NonNull
    private String author;
    @NonNull
    private LocalizedText title;
}
