package ru.yandex.practicum.tasks;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();
}
