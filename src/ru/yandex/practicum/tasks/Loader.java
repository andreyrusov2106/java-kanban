package ru.yandex.practicum.tasks;

import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.enums.TaskType;
import ru.yandex.practicum.exceptions.ManagerLoadException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("base2.csv");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                if (line.startsWith("id")) continue;
                if (!line.isBlank()) {
                    Task task = taskFromString(line);
                    if (task instanceof Epic) {
                        fileBackedTasksManager.createEpic((Epic) task);
                    } else if (task instanceof SubTask) {
                        fileBackedTasksManager.createSubTask((SubTask) task);
                    } else {
                        fileBackedTasksManager.createTask(task);
                    }
                } else {
                    line = bufferedReader.readLine();
                    for (Integer taskId : historyFromString(line)) {
                        fileBackedTasksManager.getTaskById(taskId);
                        fileBackedTasksManager.getEpicById(taskId);
                        fileBackedTasksManager.getSubTaskById(taskId);

                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка загрузки  файла");
        }
        return fileBackedTasksManager;
    }

    static List<Integer> historyFromString(String value) {
        String[] values = value.split(",");
        List<Integer> result = new ArrayList<>();
        for (String val : values) {
            result.add(Integer.valueOf(val));
        }
        return result;
    }

    static public Task taskFromString(String value) {
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        TaskType taskType = TaskType.valueOf(values[1]);
        String name = values[2];
        Status status = Status.valueOf(values[3]);
        String description = values[4];
        switch (taskType) {
            case EPIC:
                return new Epic(id, name, description, status);
            case TASK:
                return new Task(id, name, description, status);
            case SUBTASK:
                int epicId = Integer.parseInt(values[5]);
                return new SubTask(id, name, description, status, epicId);
            default:
                return null;
        }
    }
}
