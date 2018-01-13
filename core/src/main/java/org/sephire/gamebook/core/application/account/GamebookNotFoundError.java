package org.sephire.gamebook.core.application.account;

import org.sephire.gamebook.core.application.shared.commands.CommandClientError;

/**
 * This exception is thrown when someone tries to operate with a gamebook
 * but the gamebook is not found.
 */
public class GamebookNotFoundError extends CommandClientError {

}
