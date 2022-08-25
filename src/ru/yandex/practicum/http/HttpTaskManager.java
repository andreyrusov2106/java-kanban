package ru.yandex.practicum.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.adapters.LocalDateTimeAdapter;
import ru.yandex.practicum.client.KVTaskClient;
import ru.yandex.practicum.tasks.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {
    public static final String TASKS = "tasks";
    public static final String EPICS = "epics";
    public static final String SUBTASKS = "subtasks";
    public static final String HISTORY = "history";
    private final  KVTaskClient kvTaskClient;
    private final Gson gson;

    public HttpTaskManager(int port) {
        super(null);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.serializeNulls();
        gson = gsonBuilder.create();
        kvTaskClient = new KVTaskClient(port);
    }

    public void save() {
        if (getAllTasks().size() > 0) {
            String allTasks = gson.toJson(getAllTasks());
            kvTaskClient.put(TASKS, allTasks);
        }
        if (getAllEpics().size() > 0) {
            String allEpics = gson.toJson(getAllEpics());
            kvTaskClient.put(EPICS, allEpics);
        }
        if (getAllSubTasks().size() > 0) {
            String allSubTasks = gson.toJson(getAllSubTasks());
            kvTaskClient.put(SUBTASKS, allSubTasks);
        }

        if (getHistoryManager().getHistory().size() > 0) {
            List<Integer> historyList = getHistory().stream()
                    .map(Task::getId)
                    .collect(Collectors.toList());
            String history = gson.toJson(historyList);
            kvTaskClient.put(HISTORY, history);
        }

    }

    public void load() {
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        try {
            ArrayList<Task> tasks = gson.fromJson(kvTaskClient.load(TASKS), taskType);
            tasks.forEach(this::createTask);
        } catch (Exception ex) {
            System.out.println("Проблемы при загрузке задач");
        }
        try {
            Type epicType = new TypeToken<ArrayList<Epic>>() {
            }.getType();
            ArrayList<Epic> epics = gson.fromJson(kvTaskClient.load(EPICS), epicType);
            epics.forEach(e -> {
                e.clearAllSubTasks();
                createEpic(e);
            });
        } catch (Exception ex) {
            System.out.println("Проблемы при загрузке эпиков");
        }
        try {

            Type subTaskType = new TypeToken<ArrayList<SubTask>>() {
            }.getType();
            ArrayList<SubTask> subTasks = gson.fromJson(kvTaskClient.load(SUBTASKS), subTaskType);
            subTasks.forEach(this::createSubTask);
        } catch (Exception ex) {
            System.out.println("Проблемы при загрузке сабтасков");
        }
        try {
            Type historyType = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            ArrayList<Integer> history = gson.fromJson(kvTaskClient.load(HISTORY), historyType);
            for (Integer taskId : history) {
                getTaskById(taskId);
                getEpicById(taskId);
                getSubTaskById(taskId);

            }
        } catch (Exception ex) {

        }

    }

}
