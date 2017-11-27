package org.sephire.gamebook.core.application.shared.commands;

import io.vavr.control.Option;

/**
 * The result from the execution of a command.
 *
 * @param <R>
 */
public interface CommandResult<R> {
    /**
     * To get the optional value
     *
     * @return
     */
    public Option<R> resolve();
}
