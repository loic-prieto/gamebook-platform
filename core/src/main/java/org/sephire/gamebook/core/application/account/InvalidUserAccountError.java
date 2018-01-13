package org.sephire.gamebook.core.application.account;


import org.sephire.gamebook.core.application.shared.commands.CommandClientError;

/**
 * This is an exception that is thrown when trying to operate with a user account
 * but it is not found.
 */
public class InvalidUserAccountError extends CommandClientError {
}
