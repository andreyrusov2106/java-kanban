package ru.yandex.practicum.tasks;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        history.addFirst(task);
        if (history.size() > 10) {
            history.removeLast();
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
