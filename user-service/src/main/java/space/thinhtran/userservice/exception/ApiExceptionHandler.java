package space.thinhtran.userservice.exception;

import jakarta.ws.rs.NotAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.authorization.client.util.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import space.thinhtran.commonmodule.dto.response.ErrorResp;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    private static final String ERROR_LOG_FORMAT = "Error: URI: {}, Message: {}, Method: {}";
    private static final String INVALID_REQUEST_INFORMATION_MESSAGE = "Request information is not valid";
    private static final String METHOD_NOT_ALLOWED_MESSAGE_FORMAT =
            "Method %s is not allowed for this endpoint. Supported methods are %s";


    private static final String DEFAULT_ERROR_MESSAGE = "An unexpected error occurred. Please try again later.";


    @ExceptionHandler(Exception.class) // for all other exceptions debug
    public ResponseEntity<ErrorResp> handleGeneralException(Exception ex, WebRequest request) {
        ex.getStackTrace();
        ex.printStackTrace();

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                DEFAULT_ERROR_MESSAGE, null, ex, request);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorResp> handleNotFoundException(NotAuthorizedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return buildErrorResponse(status, ex.getMessage(), null, ex, request);
    }

    private ResponseEntity<ErrorResp> buildErrorResponse(HttpStatus httpStatus, String message, List<String> fieldErrors,
                                                         Exception ex, WebRequest request) {
        ErrorResp errorResp = new ErrorResp(httpStatus.toString(), httpStatus.getReasonPhrase(), message, fieldErrors);

        if (request instanceof ServletWebRequest servletWebRequest) {
            String path = servletWebRequest.getRequest().getServletPath();
            String method = servletWebRequest.getRequest().getMethod();
            log.error(ERROR_LOG_FORMAT, path, message, method);
        } else {
            log.error(message, ex);
        }
        return ResponseEntity.status(httpStatus).body(errorResp);
    }
}
