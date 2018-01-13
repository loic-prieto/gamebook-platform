package org.sephire.gamebook.api.infrastructure;

/**
 * A RequestDTO is a simple request object to be mapped by Spring MVC from the request body.
 * Since we're using command to interact with the model, each request should map to a Command
 * object.
 *
 * @param <COMMAND>
 */
public interface RequestDTO<COMMAND> {

    /**
     * A Request DTO can be converted to its corresponding domain command.
     * The request object must provide the behaviour.
     *
     * @return
     */
    COMMAND toCommand();
}
