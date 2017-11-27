package org.sephire.gamebook.core.application.shared.commands;

/**
 * This is an error that is the result of a customer requesting an
 * operation for which it has no authorization.
 * This can be further subclassed to provide more detail.
 */
public class NotAuthorizedOperationError extends CommandError {
}
