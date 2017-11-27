package org.sephire.gamebook.core.application.shared.commands;

import io.vavr.collection.List;
import io.vavr.control.Either;

/**
 * The generic interface that all command executors must implement to take care
 * of use cases.
 *
 * @param <C> The command that the handler takes care of
 * @param <R> The type of result it must return
 */
public interface CommandHandler<C, R> {

    /**
     * Perform a command. It will either execute correctly and
     * optionally return some value, or it will fail and return
     * a list of command errors which can be inspected to treat
     * them.
     *
     * @param command
     * @return
     */
    public Either<List<CommandError>, R> execute(C command);
}
