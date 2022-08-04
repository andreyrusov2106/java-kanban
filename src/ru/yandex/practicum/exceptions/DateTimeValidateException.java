package ru.yandex.practicum.exceptions;

public class DateTimeValidateException extends RuntimeException {
    public DateTimeValidateException(final String message) {
        super(message);
    }
}
