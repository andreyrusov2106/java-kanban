package ru.yandex.practicum.tasks;

public class Managers {
    public static TaskManager getDefault() {
        return new FileBackedTasksManager("base.csv");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
