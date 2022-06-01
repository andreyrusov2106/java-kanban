package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private int currentId;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();

    //methods for Tasks
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void clearAllTasks() {
        tasks.clear();
    }

    public void createTask(Task task) {
        if (task != null) {
            currentId++;
            task.setId(currentId);
            tasks.put(currentId, task);
        }
    }

    public void updateTask(Task task) {
        if (task != null) {
            tasks.put(task.getId(), task);
        }
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    //methods for Epics
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

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

    public void updateEpic(Epic task) {
        if (task != null) {
            epics.put(task.getId(), task);
        }
    }

    public void removeEpicById(int id) {
        clearAllSubTasksByEpicId(id);
        epics.remove(id);
    }

    //methods for Subtasks
    public List<SubTask> getAllSubTasksByEpicId(int epicId) {
        List<SubTask> subTasks = new ArrayList<>();
        for (Integer subTaskId : epics.get(epicId).getSubTaskIds()) {
            subTasks.add(this.subTasks.get(subTaskId));
        }
        return subTasks;
    }

    public void clearAllSubTasksByEpicId(int epicId) {
        if (epics.containsKey(epicId)) {
            for (Integer subTaskId : epics.get(epicId).getSubTaskIds()) {
                subTasks.remove(subTaskId);
            }
            epics.get(epicId).clearAllSubTasks();
            checkEpicStatus(epicId);
        }
    }

    public SubTask getSubTaskById(int subTaskId) {
        return subTasks.get(subTaskId);
    }

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

    public void updateSubTask(SubTask task) {
        if (subTasks.containsKey(task.getId())) {
            subTasks.put(task.getId(), task);
            checkEpicStatus(task.getEpicId());
        }
    }

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
