package org.sephire.gamebook.core.application.shared.commands;

import lombok.Getter;

/**
 * This is the error to be used when handling unexpected errors that cannot be known
 * in advance. This should only really be used while prototipying an application
 * since all methods should have documented what kind of exception they can generate.
 * <p>
 * In general commands should never leak implementation details to the outside.
 */
public class ExceptionalError extends CommandError {
    @Getter
    private Throwable exception;

    public ExceptionalError(Throwable exception) {
        this.exception = exception;
    }

}
