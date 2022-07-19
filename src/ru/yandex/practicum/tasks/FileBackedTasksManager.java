package ru.yandex.practicum.tasks;

import java.io.FileWriter;
import java.io.IOException;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private final String fileName;

    public FileBackedTasksManager(String fileName) {
        this.fileName = fileName;
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("id,type,name,status,description,epic\n");
            if (getAllTasks().size() > 0) {
                for (Task task : getAllTasks()) {
                    fileWriter.write(ToStringHelper.toString(task));
                }
            }
            if (getAllEpics().size() > 0) {
                for (Epic epic : getAllEpics()) {
                    fileWriter.write(ToStringHelper.toString(epic));
                }
            }
            if (getAllSubTasks().size() > 0) {
                for (SubTask subTask : getAllSubTasks()) {
                    fileWriter.write(ToStringHelper.toString(subTask));
                }
            }
            if (getHistoryManager().getHistory().size() > 0) {
                fileWriter.write("\n");
                fileWriter.write(ToStringHelper.toString(getHistoryManager()));
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки  файла");
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic task) {
        super.createEpic(task);
        save();
    }

    @Override
    public void createSubTask(SubTask task) {
        super.createSubTask(task);
        save();
    }

    @Override
    public void removeTaskById(int taskId) {
        super.removeTaskById(taskId);
        save();
    }

    @Override
    public void removeEpicById(int epicId) {
        super.removeEpicById(epicId);
        save();
    }

    @Override
    public void removeSubTaskById(int subTaskId) {
        super.removeSubTaskById(subTaskId);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task result = super.getTaskById(id);
        if (result != null) save();
        return result;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask result = super.getSubTaskById(id);
        if (result != null) save();
        return result;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic result = super.getEpicById(id);
        if (result != null) save();
        return result;
    }
}
