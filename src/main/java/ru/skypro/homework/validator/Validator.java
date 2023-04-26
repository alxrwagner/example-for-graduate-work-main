package ru.skypro.homework.validator;


import ru.skypro.homework.exception.InvalidEmailException;
import ru.skypro.homework.exception.InvalidFormatPhoneNumberException;
import ru.skypro.homework.exception.NullOrEmptyException;
import ru.skypro.homework.exception.TooShortPasswordException;

public class Validator {

    public static <T> T checkValidateObj(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    public static String checkValidateString(String str) {
        if (str == null || str.isBlank()) {
            throw new NullOrEmptyException();
        }
        return str.trim();
    }

    public static String checkValidateEmail(String email) {
        if (!checkValidateString(email).contains("@")) {
            throw new InvalidEmailException();
        }
        return email.trim();
    }

    public static String checkValidatePassword(String password) {
        if (checkValidateString(password).length() < 8) {
            throw new TooShortPasswordException();
        }
        return password.trim();
    }

    public static String checkValidatePhone(String phone, Integer countryCode){
        if (!checkValidateString(phone).startsWith("+" + countryCode)){
            throw new InvalidFormatPhoneNumberException();
        }
        return phone.trim();
    }
}
