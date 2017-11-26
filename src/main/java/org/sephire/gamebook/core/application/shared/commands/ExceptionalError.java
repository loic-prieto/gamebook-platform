package org.sephire.gamebook.core.application.shared.commands;

/**
 * This is the error to be used when handling unexpected errors that cannot be known
 * in advance. This should only really be used while prototipying an application
 * since all methods should have documented what kind of exception they can generate.
 * <p>
 * In general commands should never leak implementation details to the outside.
 */
public class ExceptionalError extends CommandError {
    private Exception exception;

    public ExceptionalError(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
