package ru.skypro.homework.service;


import ru.skypro.homework.exception.NullOrEmptyException;

public class Validator {

    public static <T> T checkValidateObj(T obj){
        if (obj == null){
            throw new NullPointerException();
        }
        return obj;
    }

    public static String checkValidateString(String str){
        if(str == null || str.isBlank()){
            throw new NullOrEmptyException();
        }
        return str;
    }
}
