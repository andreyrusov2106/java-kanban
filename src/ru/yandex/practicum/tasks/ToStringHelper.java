package ru.yandex.practicum.tasks;

import ru.yandex.practicum.enums.TaskType;
import ru.yandex.practicum.interfaces.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class ToStringHelper {
    public static String toString(Task task) {
        String strTime = "";
        if (task.getStartTime() != null) {
            strTime = ", startTime=" + task.getStartTime() +
                    ", duration=" + task.getDuration() +
                    ", endTime=" + task.getEndTime();
        }
        return task.getId() +
                "," + TaskType.TASK +
                "," + task.getName() +
                "," + task.getStatus() +
                "," + task.getDescription() + strTime + "\n";
    }

    public static String toString(Epic epic) {
        String strTime = "";
        if (epic.getStartTime() != null) {
            strTime = ", startTime=" + epic.getStartTime() +
                    ", duration=" + epic.getDuration() +
                    ", endTime=" + epic.getEndTime();
        }
        return epic.getId() +
                "," + TaskType.EPIC +
                "," + epic.getName() +
                "," + epic.getStatus() +
                "," + epic.getDescription() + strTime + "\n";
    }

    public static String toString(SubTask subTask) {
        String strTime = "";
        if (subTask.getStartTime() != null) {
            strTime = ", startTime=" + subTask.getStartTime() +
                    ", duration=" + subTask.getDuration() +
                    ", endTime=" + subTask.getEndTime();
        }
        return subTask.getId() +
                "," + TaskType.SUBTASK +
                "," + subTask.getName() +
                "," + subTask.getStatus() +
                "," + subTask.getDescription() +
                "," + subTask.getEpicId() + strTime + "\n";
    }

    public static String toString(HistoryManager manager) {
        List<String> taskIds = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            taskIds.add(String.valueOf(task.getId()));
        }
        return String.join(",", taskIds);
    }


}
