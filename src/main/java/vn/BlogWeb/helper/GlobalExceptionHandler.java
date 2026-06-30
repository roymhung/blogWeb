package vn.BlogWeb.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Tham số '%s' có giá trị '%s' không đúng định dạng.",
                ex.getName(), ex.getValue());
        return ApiResponse.error(HttpStatus.BAD_REQUEST, errorMessage);
    }

}
