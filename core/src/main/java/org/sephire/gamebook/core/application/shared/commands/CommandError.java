package org.sephire.gamebook.core.application.shared.commands;

/**
 * A command error is an error that has been produced while
 * processing a command. It will translate domain errors to
 * application errors, and will be returned to the command's
 * invoker.
 * It should never contain localized strings. Rather, the class
 * of the CommandError itself will serve as an identifier of the
 * error. The client should recognize the class of the error and
 * act accordingly.
 */
public abstract class CommandError {
}
