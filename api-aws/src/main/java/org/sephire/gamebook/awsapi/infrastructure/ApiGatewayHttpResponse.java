package org.sephire.gamebook.awsapi.infrastructure;

import com.jsoniter.output.JsonStream;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.stream.Stream;

public class ApiGatewayHttpResponse<ENTITY> {
    private HttpStatusCode statusCode;
    private Option<ENTITY> entity;
    private Map<String, String> headers;

    public ApiGatewayHttpResponse(HttpStatusCode statusCode, Map<String, String> headers, ENTITY entity) {
        this.statusCode = statusCode;
        this.entity = Option.of(entity);
        this.headers = headers;
    }

    /**
     * Convenience method to build headers for this response.
     * To be used like so:
     * <pre>
     * ApiGatewayHttpResponse&lt;Void&gt; response = new ApiGatewayHttpResponse(
     *     HttpStatusCode.OK,
     *     headers(
     *         header("Content-Type","application/json"),
     *         header("Location","lol"),
     *         header("Etag","123456ABC")
     *     ));
     * </pre>
     *
     * @param headers
     * @return
     */
    public static Map<String, String> headers(Tuple2<String, String>... headers) {
        Stream<Tuple2<String, String>> headerStream = List.of(headers).toJavaStream();
        return HashMap.ofAll(headerStream, entry -> Map.entry(entry._1, entry._2));
    }

    /**
     * Convenience method to build headers for a response.
     * See headers static method of this class.
     *
     * @param key
     * @param value
     * @return
     */
    public static Tuple2<String, String> header(String key, String value) {
        return new Tuple2<>(key, value);
    }

    /**
     * Given an output stream, writes the response to it,
     * formatted as an AWS API Gateway output response, with
     * the following format:
     * <pre>
     * {
     *     "headers":{
     *         "header1":"value",
     *         "header2":"value"
     *     },
     *     "statusCode": intvalue,
     *     "body":"body as string",
     *     "exception":"some serialized string of an error if status code is a semantic error"
     * }
     * </pre>
     *
     * @param outputStream
     */
    public void sendTo(OutputStream outputStream) throws IOException {

        String responseString = JsonStream.serialize(new HTTPResponse(this));

        new OutputStreamWriter(outputStream)
                .write(responseString);
    }

    public static <ENTITY> Builder builder() {
        return new Builder<ENTITY>();
    }

    public static class Builder<ENTITY> {
        private HttpStatusCode statusCode;
        private Map<String, String> headers;
        private ENTITY body;

        public Builder<ENTITY> setStatusCode(HttpStatusCode code) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<ENTITY> setheaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder<ENTITY> setBody(ENTITY body) {
            this.body = body;
            return this;
        }

        public ApiGatewayHttpResponse<ENTITY> build() {
            if (statusCode == null || headers == null) {
                throw new IllegalArgumentException("Status code and headers are mandatory parameters");
            }
            return new ApiGatewayHttpResponse<ENTITY>(statusCode, headers, body);
        }
    }

    /**
     * Models an HTTP Response in JSON-digestible format.
     */
    @Getter
    private class HTTPResponse {
        private int statusCode;
        private java.util.Map<String, String> headers;
        private ENTITY body;

        public HTTPResponse(ApiGatewayHttpResponse<ENTITY> response) {
            this.statusCode = response.statusCode.getCode();
            this.headers = response.headers.toJavaMap();
            if (response.entity.isDefined()) {
                body = response.entity.get();
            }
        }
    }
}
