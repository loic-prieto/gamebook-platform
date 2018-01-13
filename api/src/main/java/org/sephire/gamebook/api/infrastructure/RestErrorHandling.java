package org.sephire.gamebook.api.infrastructure;

import io.vavr.collection.List;
import org.sephire.gamebook.core.application.shared.commands.CommandClientError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global error handling for Command Errors, which are errors provoked when
 * using a core domain command to interact with the model.
 * A CommandError can either be a client side error, or a server error.
 */
@ControllerAdvice
public class RestErrorHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommandErrors.class})
    protected ResponseEntity<List<ErrorResponse>> handleConflict(CommandErrors errors, WebRequest request) {

        // This binary logic will not work if we want to treat here authorization errors.
        // This will have to be revised in future iterations
        HttpStatus statusCode = errors.getErrors()
                .find(error -> error.getClass().isAssignableFrom(CommandClientError.class))
                .map((error) -> HttpStatus.BAD_REQUEST)
                .getOrElse(() -> HttpStatus.INTERNAL_SERVER_ERROR);

        List<ErrorResponse> errorsBody = errors.getErrors()
                .map(ErrorResponse::new);

        return ResponseEntity
                .status(statusCode)
                .body(errorsBody);

    }
}
