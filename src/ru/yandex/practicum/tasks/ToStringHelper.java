package ru.yandex.practicum.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToStringHelper {
    public static String toString(Task task) {
        return task.getId() +
                "," + TaskType.TASK +
                "," + task.getName() +
                "," + task.getStatus() +
                "," + task.getDescription() + "\n";
    }

    public static String toString(Epic epic) {
        return epic.getId() +
                "," + TaskType.EPIC +
                "," + epic.getName() +
                "," + epic.getStatus() +
                "," + epic.getDescription() + "\n";
    }

    public static String toString(SubTask subTask) {
        return subTask.getId() +
                "," + TaskType.SUBTASK +
                "," + subTask.getName() +
                "," + subTask.getStatus() +
                "," + subTask.getDescription() +
                "," + subTask.getEpicId() + "\n";
    }

    public static String toString(HistoryManager manager) {
        List<String> taskIds = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            taskIds.add(String.valueOf(task.getId()));
        }
        return String.join(",", taskIds);
    }


}
