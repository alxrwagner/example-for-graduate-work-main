package ru.skypro.homework.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skypro.homework.exception.*;

@ControllerAdvice
public class IncorrectParamException {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> isNotFoundWithThisParam() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NullableParamException.class)
    public ResponseEntity<?> paramIsNull() {
        return ResponseEntity.badRequest().body("The Parameter should not be missing");
    }

    @ExceptionHandler(NullOrEmptyException.class)
    public ResponseEntity<?> nullOrEmptyParam(){
        return ResponseEntity.badRequest().body("Data must not be empty");
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<?> invalidEmail(){
        return ResponseEntity.badRequest().body("Invalid email format");
    }

    @ExceptionHandler(TooShortPasswordException.class)
    public ResponseEntity<?> tooShortPass(){
        return ResponseEntity.badRequest().body("The password is too short");
    }

    @ExceptionHandler(InvalidFormatPhoneNumberException.class)
    public ResponseEntity<?> invalidFormatPhoneNumber(){
        return ResponseEntity.badRequest().body("Invalid format phone number");
    }
}