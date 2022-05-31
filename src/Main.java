import tasks.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //tests for Tasks
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Task1", "Description1", Status.NEW);
        taskManager.createTask(task1);
        Task task2 = new Task("Task1", "Description2", Status.NEW);
        taskManager.createTask(task2);
        taskManager.clearAllTasks();
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Task task3 = taskManager.getTaskById(3);
        System.out.println(task3);
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
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.NEW);
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.NEW);
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.NEW);
        int epicShoppingId = taskManager.createEpic(epicShopping);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        Epic epicShopping2 = new Epic(5, "New Epic", "New description");
        taskManager.updateEpic(epicShopping2);
        taskManager.createSubTask(epicShoppingId, subTask1);
        taskManager.createSubTask(epicShoppingId, subTask2);
        taskManager.createSubTask(epicShoppingId, subTask3);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        SubTask subTask4 = new SubTask(7, "SubTask4", "Subtask description4", Status.DONE);
        taskManager.updateSubTask(5, subTask4);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        for (Task allSubTask : taskManager.getAllSubTasks(5)) {
            System.out.println(allSubTask);
        }
        taskManager.removeSubTaskById(5, 7);
        taskManager.removeSubTaskById(5, 8);
        SubTask subTask7 = new SubTask(6, "SubTask7", "Subtask description7", Status.DONE);
        taskManager.updateSubTask(5, subTask7);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        SubTask subTask5 = (SubTask) taskManager.getSubTaskById(5, 6);
        SubTask subTask6 = new SubTask("SubTask6", "Subtask description6", Status.IN_PROGRESS);
        taskManager.createSubTask(5, subTask6);
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        System.out.println(subTask5);
    }
}
