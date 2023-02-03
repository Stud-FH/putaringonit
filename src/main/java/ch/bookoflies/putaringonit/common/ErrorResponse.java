package ch.bookoflies.putaringonit.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

public abstract class ErrorResponse {

    public static Supplier<ResponseStatusException> NotFound(String message) {
        return () -> new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }

    public static Supplier<ResponseStatusException> Unauthorized(String message) {
        return () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
    }

    public static Supplier<ResponseStatusException> Forbidden() {
        return () -> new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public static Supplier<ResponseStatusException> Forbidden(String message) {
        return () -> new ResponseStatusException(HttpStatus.FORBIDDEN, message);
    }

    public static Supplier<ResponseStatusException> Conflict(String message) {
        return () -> new ResponseStatusException(HttpStatus.CONFLICT, message);
    }

    public static Supplier<ResponseStatusException> Unprocessable(String message) {
        return () -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
