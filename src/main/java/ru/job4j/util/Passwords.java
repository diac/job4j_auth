package ru.job4j.util;

public class Passwords {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private Passwords() {

    }

    public static boolean isValidPasswordFormat(char[] password) {
        return password.length >= MIN_PASSWORD_LENGTH;
    }
}