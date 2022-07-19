import ru.yandex.practicum.tasks.*;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        //Tests
        Epic epic1 = new Epic("Epic1", "Epic description1");
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 1);
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.NEW, 1);
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.NEW, 1);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Epic2", "Epic description2");
        taskManager.createEpic(epic2);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
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
        taskManager.removeSubTaskById(4);
        printHistory(taskManager.getHistory());
        taskManager.removeEpicById(1);
        printHistory(taskManager.getHistory());
    }

    public static void printHistory(List<Task> history) {
        System.out.println("---------------history-------------------");
        for (Task task : history) {
            System.out.println(task.toString());
        }
        System.out.println("-----------------------------------------");
    }
}
