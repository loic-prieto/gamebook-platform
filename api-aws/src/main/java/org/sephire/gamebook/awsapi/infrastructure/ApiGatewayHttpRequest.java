package org.sephire.gamebook.awsapi.infrastructure;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import lombok.Getter;

/**
 * Represents an API Gateway HTTP Request
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

    public ApiGatewayHttpRequest() {

    }
}
