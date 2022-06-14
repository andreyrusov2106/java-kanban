import ru.yandex.practicum.tasks.*;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //tests for Tasks
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Task1", "Description1", Status.NEW);
        taskManager.createTask(task1);
        Task task2 = new Task("Task1", "Description2", Status.NEW);
        taskManager.createTask(task2);
        taskManager.clearAllTasks();
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Task task3 = taskManager.getTaskById(3);
        System.out.println(task3);
        System.out.println("---------------history-------------------");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }
        System.out.println("-----------------------------------------");
        ArrayList<Task> tasks = taskManager.getAllTasks();
        for (Task task : tasks) {
            System.out.println(task);
        }
        task3.setDescription("new description");
        task3.setName("new name");

        taskManager.updateTask(task3);
        taskManager.removeTaskById(3);
        for (Task task : tasks) {
            System.out.println(task);
        }

        //Tests for Epics and SubTasks
        Epic epicShopping = new Epic("Epic1", "Epic description1");
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 5);
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.NEW, 5);
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.NEW, 5);
        taskManager.createEpic(epicShopping);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        Epic epicShopping2 = new Epic(5, "New Epic", "New description");
        taskManager.updateEpic(epicShopping2);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        SubTask subTask4 = new SubTask(6, "SubTask4", "Subtask description4", Status.DONE, 5);
        taskManager.updateSubTask(subTask4);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        for (Task allSubTask : taskManager.getAllSubTasksByEpicId(5)) {
            System.out.println(allSubTask);
        }
        taskManager.removeSubTaskById(7);
        taskManager.removeSubTaskById(8);
        SubTask subTask7 = new SubTask(6, "SubTask7", "Subtask description7", Status.DONE, 5);
        taskManager.updateSubTask(subTask7);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        SubTask subTask5 = taskManager.getSubTaskById(6);
        System.out.println(subTask5);
        System.out.println("---------------history-------------------");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }
        System.out.println("-----------------------------------------");
        SubTask subTask6 = new SubTask("SubTask6", "Subtask description6", Status.IN_PROGRESS, 5);
        taskManager.createSubTask(subTask6);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        Epic epic3 = new Epic("Epic3", "Epic description3");
        taskManager.createEpic(epic3);
        SubTask subTask10 = new SubTask("SubTask10", "Subtask description10", Status.IN_PROGRESS, 10);
        taskManager.createSubTask(subTask10);
        System.out.println(taskManager.getEpicById(10));        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        taskManager.removeEpicById(10);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        taskManager.removeSubTaskById(6);
        taskManager.clearAllSubTasksByEpicId(5);
        taskManager.clearAllEpics();
        System.out.println("---------------history-------------------");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }
        System.out.println("-----------------------------------------");
    }
}
