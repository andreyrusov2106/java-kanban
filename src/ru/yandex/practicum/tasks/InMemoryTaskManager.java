package ru.yandex.practicum.tasks;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int currentId;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();

    //methods for Tasks
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public void createTask(Task task) {
        if (task != null) {
            currentId++;
            task.setId(currentId);
            tasks.put(currentId, task);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    //methods for Epics
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public int createEpic(Epic task) {
        if (task != null) {
            currentId++;
            task.setId(currentId);
            epics.put(currentId, task);
            checkEpicStatus(currentId);
            return currentId;
        } else {
            return -1;
        }
    }

    @Override
    public void updateEpic(Epic task) {
        if (task != null) {
            epics.put(task.getId(), task);
        }
    }

    @Override
    public void removeEpicById(int id) {
        clearAllSubTasksByEpicId(id);
        epics.remove(id);
    }

    //methods for Subtasks
    @Override
    public List<SubTask> getAllSubTasksByEpicId(int epicId) {
        List<SubTask> subTasks = new ArrayList<>();
        for (Integer subTaskId : epics.get(epicId).getSubTaskIds()) {
            subTasks.add(this.subTasks.get(subTaskId));
        }
        return subTasks;
    }

    @Override
    public void clearAllSubTasksByEpicId(int epicId) {
        if (epics.containsKey(epicId)) {
            for (Integer subTaskId : epics.get(epicId).getSubTaskIds()) {
                subTasks.remove(subTaskId);
            }
            epics.get(epicId).clearAllSubTasks();
            checkEpicStatus(epicId);
        }
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public void createSubTask(SubTask task) {
        int epicId = task.getEpicId();
        if (epics.containsKey(epicId)) {
            currentId++;
            task.setId(currentId);
            Epic tmpEpic = epics.get(epicId);
            tmpEpic.addSubTaskIds(currentId);
            subTasks.put(currentId, task);
            checkEpicStatus(epicId);
        }
    }

    @Override
    public void updateSubTask(SubTask task) {
        if (subTasks.containsKey(task.getId())) {
            subTasks.put(task.getId(), task);
            checkEpicStatus(task.getEpicId());
        }
    }

    @Override
    public void removeSubTaskById(int subTaskId) {
        if (subTasks.containsKey(subTaskId)) {
            int epicId = subTasks.get(subTaskId).getEpicId();
            if (epics.containsKey(epicId)) {
                Epic tmpEpic = epics.get(epicId);
                tmpEpic.removeSubTaskIds(subTaskId);
                subTasks.remove(subTaskId);
                checkEpicStatus(tmpEpic.getId());
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void checkEpicStatus(int epicId) {
        List<SubTask> subTasks = getAllSubTasksByEpicId(epicId);
        Epic epic = epics.get(epicId);
        if (subTasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        ArrayList<Status> statuses = new ArrayList<>();
        for (SubTask subTask : this.subTasks.values()) {
            statuses.add(subTask.getStatus());
        }
        if (!statuses.contains(Status.DONE) && !statuses.contains(Status.IN_PROGRESS)) {
            epic.setStatus(Status.NEW);
        } else if (!statuses.contains(Status.NEW) && !statuses.contains(Status.IN_PROGRESS)) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
