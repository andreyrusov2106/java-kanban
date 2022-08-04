package ru.yandex.practicum.tasks;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.interfaces.TaskManager;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest {


    public FileBackedTasksManagerTest() {
        super(new FileBackedTasksManager("base.csv"));
    }

    @Test
    void EmptyTaskList() {
        taskManager.clearAllTasks();
        taskManager.clearAllEpics();
        TaskManager taskManager2 = Loader.loadFromFile(new File("base.csv"));
        List<Task> tasks = taskManager2.getAllTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач.");

    }

    @Test
    void EmptySubtaskListInEpic() {
        TaskManager taskManager2 = Loader.loadFromFile(new File("base.csv"));
        taskManager2.createEpic(epic1);
        taskManager2.createSubTask(subTask1);
        taskManager2.clearAllSubTasksByEpicId(1);
        List<SubTask> tasks = taskManager2.getAllSubTasksByEpicId(1);
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void emptyHistoryList() {
        TaskManager taskManager2 = Loader.loadFromFile(new File("base.csv"));
        taskManager2.clearAllTasks();
        taskManager2.clearAllEpics();
        assertEquals(0, taskManager2.getHistory().size(), "Неверное количество задач.");
    }
}