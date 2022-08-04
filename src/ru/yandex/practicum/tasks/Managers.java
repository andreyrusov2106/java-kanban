package ru.yandex.practicum.tasks;

import ru.yandex.practicum.interfaces.HistoryManager;
import ru.yandex.practicum.interfaces.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new FileBackedTasksManager("base.csv");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
