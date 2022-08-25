package ru.yandex.practicum.exceptions;

public class ClientSaveException extends RuntimeException {
    public ClientSaveException(final String message) {
        super(message);
    }
}