package ru.skypro.homework.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skypro.homework.exception.NullableParamException;
import ru.skypro.homework.exception.NotFoundException;

@ControllerAdvice
public class IncorrectParamException {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> isNotFoundWithThisParam() {return ResponseEntity.notFound().build();}
    @ExceptionHandler(NullableParamException.class)
    public ResponseEntity<?> paramIsNull() {return ResponseEntity.badRequest().body("The Parameter should not be missing");}
}