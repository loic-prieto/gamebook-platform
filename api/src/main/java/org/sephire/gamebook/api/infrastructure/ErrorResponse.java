package org.sephire.gamebook.api.infrastructure;

import lombok.Getter;
import org.sephire.gamebook.core.application.shared.commands.CommandError;

/**
 * This is a serializer-friendly version of a Command Error.
 * A very naive implementation, as this is client-facing. An exception->object mapper
 * needs to be done here.
 */
public class ErrorResponse {
    @Getter
    private String exceptionName;

    public ErrorResponse(CommandError error) {
        this.exceptionName = error.getClass().getName();
    }
}
