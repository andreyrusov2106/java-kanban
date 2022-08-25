import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.http.HttpTaskManager;
import ru.yandex.practicum.interfaces.TaskManager;
import ru.yandex.practicum.server.KVServer;
import ru.yandex.practicum.tasks.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        KVServer kvServer =new KVServer();
        kvServer.start();
        TaskManager taskManager = Managers.getDefault();
        //Tests
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
        Task task1 = new Task("Task1", "task description1", Status.NEW,
                LocalDateTime.of(2022, Month.AUGUST, 3, 18, 33), Duration.ofMinutes(15));
        Task task2 = new Task("Task2", "task description2", Status.NEW,
                LocalDateTime.of(2022, Month.AUGUST, 3, 19, 34), Duration.ofMinutes(15));
        Task task3 = new Task("Task3", "Subtask description3", Status.NEW);
        Task task4 = new Task("Task4", "Subtask description4", Status.NEW,
                LocalDateTime.of(2022, Month.AUGUST, 3, 17, 34), Duration.ofMinutes(15));
        HttpTaskManager httpTaskManager= new HttpTaskManager(8078);
        taskManager.getEpicById(1);
        httpTaskManager.load();
        System.out.println("*********!");
        printHistory(httpTaskManager.getHistory());
        System.out.println("*********!");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.createTask(task4);



        Epic epic2 = new Epic("Epic2", "Epic description2");
        taskManager.createEpic(epic2);

        taskManager.getEpicById(2);
        taskManager.getEpicById(1);
        printHistory(taskManager.getHistory());
        taskManager.getSubTaskById(3);
        taskManager.getSubTaskById(5);
        FileBackedTasksManager taskManager2 = Loader.loadFromFile(new File("base.csv"));
        printHistory(taskManager.getHistory());
        System.out.println("*********");
        printHistory(taskManager2.getHistory());
        System.out.println("*********");
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(3);
        printHistory(taskManager.getHistory());
        taskManager.getEpicById(1);
        printHistory(taskManager.getHistory());
        taskManager2.removeSubTaskById(4);
        taskManager.removeSubTaskById(4);
        printHistory(taskManager.getHistory());
        taskManager.removeEpicById(1);
        printHistory(taskManager.getHistory());
        kvServer.stop();
    }

    public static void printHistory(List<Task> history) {
        System.out.println("---------------history-------------------");
        for (Task task : history) {
            System.out.println(task.toString());
        }
        System.out.println("-----------------------------------------");
    }
}
