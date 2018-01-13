package org.sephire.gamebook.api.infrastructure;

import io.vavr.collection.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.application.shared.commands.CommandError;

@RequiredArgsConstructor
public class CommandErrors extends RuntimeException {
    @Getter
    @NonNull
    private List<CommandError> errors;

}
