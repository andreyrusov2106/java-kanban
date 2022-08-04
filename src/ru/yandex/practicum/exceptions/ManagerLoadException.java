package ru.yandex.practicum.exceptions;

public class ManagerLoadException extends  RuntimeException{
    public ManagerLoadException(final String message) {
        super(message);
    }
}
