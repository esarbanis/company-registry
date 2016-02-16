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
 * @author Efthymios Sarmpanis
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseBody
    ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Violation {

        private String path;
        private String message;

    }
}
