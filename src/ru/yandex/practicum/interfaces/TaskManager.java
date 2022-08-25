package ru.yandex.practicum.interfaces;

import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.SubTask;
import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();

    void clearAllTasks();

    void createTask(Task task);

    void updateTask(Task task);

    void removeTaskById(int id);

    Task getTaskById(int id);

    ArrayList<Epic> getAllEpics();

    void clearAllEpics();

    Epic getEpicById(int id);

    void createEpic(Epic task);

    void updateEpic(Epic task);

    void removeEpicById(int id);

    //methods for Subtasks
    List<SubTask> getAllSubTasksByEpicId(int epicId);
    List<SubTask> getAllSubTasks();

    void clearAllSubTasksByEpicId(int epicId);

    SubTask getSubTaskById(int subTaskId);

    void createSubTask(SubTask task);

    void updateSubTask(SubTask task);

    void removeSubTaskById(int subTaskId);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();
}
