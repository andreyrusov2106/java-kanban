package ru.yandex.practicum.http;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.interfaces.TaskManager;
import ru.yandex.practicum.server.KVServer;
import ru.yandex.practicum.tasks.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends TaskManagerTest {
    public HttpTaskManagerTest() {
        super(new HttpTaskManager(KVServer.PORT));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        Epic epic1 = new Epic("Epic1", "Epic description1");
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 18, 33), Duration.ofMinutes(15));
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 18, 50), Duration.ofMinutes(15));
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 19, 50), Duration.ofMinutes(15));
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        final List<Epic> epics = taskManager.getAllEpics();
        assertEquals(1, epics.size(), "Неверное количество эпиков.");

    }

    @Test
    void load() {
        Epic epic1 = new Epic("Epic1", "Epic description1");
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 18, 33), Duration.ofMinutes(15));
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 18, 50), Duration.ofMinutes(15));
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 19, 50), Duration.ofMinutes(15));
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        taskManager.getEpicById(1);
        HttpTaskManager httpTaskManager = new HttpTaskManager(8078);
        httpTaskManager.load();
        final List<SubTask> subTasks = httpTaskManager.getAllSubTasksByEpicId(1);
        assertEquals(3, subTasks.size(), "Неверное количество эпиков.");
    }

    @Test
    void emptyHistoryList() {

    }
}