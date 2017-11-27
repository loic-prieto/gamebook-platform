package org.sephire.gamebook.core.application.shared.commands;

/**
 * This error is to be used when a problem with the underlying repository
 * technology was found. No specific error will be propagated to the caller
 * since it would be unsecure.
 * Detailed errors will be handled by the error event listener anyway.
 */
public class RepositoryError extends CommandError {
}
