package org.sephire.gamebook.awsapi.infrastructure;

/**
 * Archetypical http status code enum.
 * I'm rewriting it to avoid importing the whole
 * apache http library into the lambda function.
 */
public enum HttpStatusCode {
    OK(200),
    SERVER_ERROR(500),
    BAD_REQUEST(400),
    CREATED(201),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    FORBIDDEN(403);

    private int code;


    HttpStatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
