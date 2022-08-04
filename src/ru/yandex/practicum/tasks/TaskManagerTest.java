package ru.yandex.practicum.tasks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.exceptions.DateTimeValidateException;
import ru.yandex.practicum.interfaces.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest {

    static TaskManager taskManager;
    static Epic epic1;
    static SubTask subTask1;
    static SubTask subTask2;
    static SubTask subTask3;
    static Task task1;

    @BeforeAll
    static void BeforeAll() {
        //Tests
        epic1 = new Epic("Epic1", "Epic description1");
        task1 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        subTask1 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 1);
        subTask2 = new SubTask("SubTask2", "Subtask description2", Status.IN_PROGRESS, 1);
        subTask3 = new SubTask("SubTask3", "Subtask description3", Status.DONE, 1);
    }

    public TaskManagerTest(TaskManager taskManager) {
        TaskManagerTest.taskManager = taskManager;
    }


    @Test
    void clearAllTasks() {
        taskManager.createTask(task1);
        List<Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        taskManager.clearAllTasks();
        tasks = taskManager.getAllTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void createTask() {
        taskManager.createTask(task1);
        final Task savedTask = taskManager.getTaskById(1);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task1, savedTask, "Задачи не совпадают.");
        final List<Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task1, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void updateTask() {
        taskManager.createTask(task1);
        final Task savedTask = taskManager.getTaskById(1);
        savedTask.setDescription("New Description");
        taskManager.updateTask(savedTask);
        assertEquals("New Description", taskManager.getTaskById(1).getDescription(), "Неверное обновление задачи.");
    }

    @Test
    void removeTaskById() {
        taskManager.createTask(task1);
        taskManager.removeTaskById(1);
        final List<Task> tasks = taskManager.getAllTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }


    @Test
    void clearAllEpics() {
        epic1.clearAllSubTasks();
        taskManager.createEpic(epic1);
        Epic epic2 = taskManager.getEpicById(1);
        assertEquals(epic1, epic2, "Возвращается неверный эпик");
        taskManager.clearAllEpics();
        final List<Epic> epics = taskManager.getAllEpics();
        assertEquals(0, epics.size(), "Неверное количество эпиков.");
    }


    @Test
    void createEpic() {
        epic1.clearAllSubTasks();
        taskManager.createEpic(epic1);
        final Task savedEpic = taskManager.getEpicById(1);
        assertNotNull(savedEpic, "Эпик не найдена.");
        assertEquals(epic1, savedEpic, "Эпики не совпадают.");
        final List<Epic> tasks = taskManager.getAllEpics();
        assertNotNull(tasks, "Эпики не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество Эпиков.");
        assertEquals(epic1, tasks.get(0), "Эпики не совпадают.");
    }

    @Test
    void updateEpic() {
        taskManager.createEpic(epic1);
        final Task savedEpic = taskManager.getEpicById(1);
        savedEpic.setDescription("New Description");
        taskManager.updateTask(savedEpic);
        assertEquals("New Description", taskManager.getTaskById(1).getDescription(), "Неверное обновление задачи.");
    }

    @Test
    void removeEpicById() {
        epic1.clearAllSubTasks();
        taskManager.createEpic(epic1);
        taskManager.removeEpicById(1);
        final List<Epic> tasks = taskManager.getAllEpics();
        assertEquals(0, tasks.size(), "Неверное количество эпиков.");
    }

    @Test
    void getAllSubTasksByEpicId() {
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        var tasks = taskManager.getAllSubTasksByEpicId(1);
        assertEquals(3, tasks.size(), "Неверное количество сабтасков.");
        taskManager.clearAllSubTasksByEpicId(1);
        tasks = taskManager.getAllSubTasksByEpicId(1);
        assertEquals(0, tasks.size(), "Неверное количество сабтасков.");
    }


    @Test
    void getSubTaskById() {
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        assertEquals(subTask1, taskManager.getSubTaskById(2), "Сабтаски не совпадают.");
    }

    @Test
    void createSubTask() {
        epic1.clearAllSubTasks();
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        final SubTask savedSubTask = taskManager.getSubTaskById(2);
        assertNotNull(savedSubTask, "Сабтаск не найден.");
        assertEquals(subTask1, savedSubTask, "Сабтаски не совпадают.");
        final List<SubTask> tasks = taskManager.getAllSubTasksByEpicId(1);
        assertNotNull(tasks, "Сабтаски не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество Сабтасков.");
        assertEquals(subTask1, tasks.get(0), "Сабтаски не совпадают.");
    }

    @Test
    void updateSubTask() {
        epic1.clearAllSubTasks();
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        final SubTask savedSubTask = taskManager.getSubTaskById(2);
        savedSubTask.setDescription("New Description");
        taskManager.updateSubTask(savedSubTask);
        assertEquals("New Description", taskManager.getSubTaskById(2).getDescription(), "Неверное обновление задачи.");
    }

    @Test
    void removeSubTaskById() {
        taskManager.createEpic(epic1);
        taskManager.removeEpicById(1);
        final List<Epic> tasks = taskManager.getAllEpics();
        assertEquals(0, tasks.size(), "Неверное количество эпиков.");
    }

    @Test
    void checkDateTimeValidateException() {
        Epic epic2 = new Epic("Epic1", "Epic description1");
        SubTask subTask4 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 18, 33), Duration.ofMinutes(15));
        SubTask subTask5 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 18, 33), Duration.ofMinutes(15));
        taskManager.createEpic(epic2);
        taskManager.createSubTask(subTask4);
        final DateTimeValidateException exception = assertThrows(
                DateTimeValidateException.class,
                () -> taskManager.createSubTask(subTask5)
        );
        assertEquals("Время задачи пересекается с уже добавленными задачами", exception.getMessage());

    }

    @Test
    void getPrioritizedTasks() {
        Epic epic2 = new Epic("Epic1", "Epic description1");
        SubTask subTask4 = new SubTask("SubTask4", "Subtask description4", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 18, 33), Duration.ofMinutes(15));
        SubTask subTask5 = new SubTask("SubTask5", "Subtask description5", Status.NEW, 1,
                LocalDateTime.of(2022, Month.AUGUST, 3, 17, 33), Duration.ofMinutes(15));
        SubTask subTask6 = new SubTask("SubTask6", "Subtask description6", Status.NEW, 1);
        taskManager.createEpic(epic2);
        taskManager.createSubTask(subTask4);
        taskManager.createSubTask(subTask5);
        taskManager.createSubTask(subTask6);
        List<Task> tasks = taskManager.getPrioritizedTasks();
        assertEquals(subTask5, tasks.get(0));
        assertEquals(subTask6, tasks.get(2));
    }

}