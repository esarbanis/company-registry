package io.github.esarbanis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Handles exceptions on the REST layer and gracefully returns the appropriate http error code.
 *
 * @author Efthymios Sarmpanis
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * When an entity is not found or an empty result set is provided, the service should return
     * a 404 http status.
     *
     * @return Not Found Response
     */
    @ExceptionHandler(value = {EntityNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseBody
    ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    /**
     * When a constraint violation exception is thrown, the application should map the
     * {@link javax.validation.ConstraintViolation}s to a <code>Set</code> of {@link Violation}s
     * and propage them to the client with a 400 http status.
     *
     * @return Bad Request Response with a <code>Set</code> of {@link Violation}s in its body
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    ResponseEntity<Set<Violation>> handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
        Set<Violation> violations = e.getConstraintViolations().stream()
                .map(constraintViolation ->
                        Violation.builder()
                                .path(constraintViolation.getPropertyPath().toString())
                                .message(constraintViolation.getMessage())
                                .build())
                .collect(Collectors.toSet());
        return ResponseEntity.badRequest().body(violations);
    }

    /**
     * Wrapper for {@link javax.validation.ConstraintViolation} objects, for easy serialisation.
     *
     * @author Efthymios Sarmpanis
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Violation {

        /**
         * The Object Graph path
         */
        private String path;

        /**
         * The error message
         */
        private String message;

    }
}
