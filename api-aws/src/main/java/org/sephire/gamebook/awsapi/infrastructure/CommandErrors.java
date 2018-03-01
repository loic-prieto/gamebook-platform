package org.sephire.gamebook.awsapi.infrastructure;

import io.vavr.collection.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.application.shared.commands.CommandError;

/**
 * A CommandError list adapter to transform a list of command errors from the domain
 * to a serializable exception for AWS Lambda error handler.
 */
@RequiredArgsConstructor
public class CommandErrors extends RuntimeException {
    @Getter
    @NonNull
    private List<CommandError> errors;

}
