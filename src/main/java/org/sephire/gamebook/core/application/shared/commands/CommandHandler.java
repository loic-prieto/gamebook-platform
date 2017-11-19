package org.sephire.gamebook.core.application.shared.commands;

/**
 * The generic interface that all command executors must implement to take care
 * of use cases.
 *
 * @param <C> The command that the handler takes care of
 * @param <R> The type of result it must return
 */
public interface CommandHandler<C, R> {

    /**
     * Perform the command
     *
     * @param command
     * @return
     */
    public CommandResult<R> execute(C command);
}
