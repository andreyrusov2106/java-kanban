package ru.yandex.practicum.exceptions;

public class ClientRegisterException extends RuntimeException {
    public ClientRegisterException(final String message) {
        super(message);
    }
}
