package ru.yandex.practicum.tasks;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.interfaces.HistoryManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void add() {
        Task task = new Task(1, "Task1", "Task1", Status.NEW);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
        historyManager.add(task);
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void remove() {
        Task task1 = new Task(1, "Task1", "Task1", Status.NEW);
        Task task2 = new Task(2, "Task2", "Task2", Status.NEW);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
        historyManager.remove(2);
        history = historyManager.getHistory();
        assertEquals(0, history.size(), "История пустая.");
    }

    @Test
    void getHistory() {
        List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size(), "История пустая.");
        Task task1 = new Task(1, "Task1", "Task1", Status.NEW);
        historyManager.add(task1);
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
    }
}