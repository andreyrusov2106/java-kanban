package ru.yandex.practicum.exceptions;

public class ClientLoadException extends RuntimeException {
    public ClientLoadException(final String message) {
        super(message);
    }
}

