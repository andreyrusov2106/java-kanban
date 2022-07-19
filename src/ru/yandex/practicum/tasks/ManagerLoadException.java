package ru.yandex.practicum.tasks;

public class ManagerLoadException extends  RuntimeException{
    public ManagerLoadException(final String message) {
        super(message);
    }
}
