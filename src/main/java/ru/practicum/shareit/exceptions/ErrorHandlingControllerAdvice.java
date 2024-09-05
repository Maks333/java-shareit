package ru.practicum.shareit.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    ValidationErrorResponse onMethodArgumentNotValidException(
//            MethodArgumentNotValidException e) {
//        ValidationErrorResponse error = new ValidationErrorResponse();
//        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
//            error.getViolations().add(
//                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
//        }
//        return error;
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorMessage onNotFoundException(NotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(EmailIsNotUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ErrorMessage onEmailIsNotUniqueException(EmailIsNotUniqueException e) {
        return new ErrorMessage(e.getMessage());
    }
}

@Getter
class ValidationErrorResponse {
    private final List<Violation> violations = new ArrayList<>();

}

@Data
@RequiredArgsConstructor
class Violation {
    private final String fieldName;
    private final String message;
}

@Data
class ErrorMessage {
    private final String message;
}