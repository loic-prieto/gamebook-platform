package org.sephire.gamebook.awsapi.infrastructure;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.joining;

/**
 * Represents an API Gateway HTTP Request
 * Will parse the json API Gateway event given from the input stream
 * of the Function handler into an HTTP Request object.
 * If the entity class is defined it will also convert the body into
 * an initialized entity object.
 *
 * @param <ENTITY>
 */
public class ApiGatewayHttpRequest<ENTITY> {
    @Getter
    private Map<String, String> headers;
    @Getter
    private Option<ENTITY> entity;
    @Getter
    private Map<String, String> queryParams;

    public ApiGatewayHttpRequest(InputStream eventStream, Option<Class<ENTITY>> inputEntityClass) {
        String rawEvent = new BufferedReader(new InputStreamReader(eventStream))
                .lines()
                .collect(joining("\n"));

        // Process http metadata
        Any event = JsonIterator.deserialize(rawEvent);
        queryParams = HashMap.ofAll(event.get("queryStringParameters").asMap())
                .mapValues(Any::toString);
        headers = HashMap.ofAll(event.get("headers").asMap())
                .mapValues(Any::toString);

        // Process the entity if needed
        entity = inputEntityClass.map(clazz -> event.get("body")
                .bindTo(emptyDTOFrom(clazz)));
    }

    public ApiGatewayHttpRequest(InputStream eventStream) {
        this(eventStream, Option.none());
    }

    /**
     * The Jsoniter API binding of any to object requires an
     * instance of an object instead of its class.
     *
     * @param entityClass
     * @return
     */
    private ENTITY emptyDTOFrom(Class<ENTITY> entityClass) {
        ENTITY entity = null;
        try {
            entity = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("The entity class must have an empty constructor");
        }
        return entity;
    }
}
