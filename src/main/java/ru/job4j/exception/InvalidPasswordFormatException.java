package ru.job4j.exception;

import java.security.InvalidParameterException;

public class InvalidPasswordFormatException extends InvalidParameterException {

    public InvalidPasswordFormatException(String msg) {
        super(msg);
    }
}