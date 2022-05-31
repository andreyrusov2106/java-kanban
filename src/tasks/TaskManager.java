package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private int currentId;
    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final Map<Integer, Epic> epicMap = new HashMap<>();

    //methods for Tasks
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    public void clearAllTasks() {
        taskMap.clear();
    }

    public void createTask(Task task) {
        if (task != null) {
            currentId++;
            task.setId(currentId);
            taskMap.put(currentId, task);
        }
    }

    public void updateTask(Task task) {
        if (task != null) {
            taskMap.put(task.getId(), task);
        }
    }

    public void removeTaskById(int id) {
        taskMap.remove(id);
    }

    public Task getTaskById(int id) {
        return taskMap.getOrDefault(id, null);
    }

    //methods for Epics
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicMap.values());
    }

    public void clearAllEpics() {
        epicMap.clear();
    }

    public Task getEpicById(int id) {
        return epicMap.getOrDefault(id, null);
    }

    public int createEpic(Epic task) {
        if (task != null) {
            currentId++;
            task.setId(currentId);
            epicMap.put(currentId, task);
            return currentId;
        } else {
            return -1;
        }
    }

    public void updateEpic(Epic task) {
        if (task != null) {
            epicMap.put(task.getId(), task);
        }
    }

    public void removeEpicById(int id) {
        epicMap.remove(id);
    }

    //methods for Subtasks
    public List<Task> getAllSubTasks(int epicId) {
        if (epicMap.containsKey(epicId)) {
            return epicMap.get(epicId).getAllSubTasks();
        } else {
            return null;
        }
    }

    public void clearAllSubTasks(int epicId) {
        if (epicMap.containsKey(epicId)) {
            epicMap.get(epicId).clearAllSubTasks();
        }
    }

    public void createSubTask(int epicId, SubTask task) {
        if (task != null && epicMap.containsKey(epicId)) {
            currentId++;
            task.setId(currentId);
            task.setEpicId(epicId);
            Epic tmpEpic = epicMap.get(epicId);
            tmpEpic.addSubTask(currentId, task);
        }
    }

    public void updateSubTask(int epicId, SubTask task) {
        if (epicMap.containsKey(epicId)) {
            Epic tmpEpic = epicMap.get(epicId);
            task.setEpicId(epicId);
            tmpEpic.updateSubTask(task);
        }
    }

    public Task getSubTaskById(int epicId, int subTaskId) {
        if (epicMap.containsKey(epicId)) {
            Epic tmpEpic = epicMap.get(epicId);
            return tmpEpic.getSubTaskById(subTaskId);
        } else {
            return null;
        }
    }

    public void removeSubTaskById(int epicId, int id) {
        if (epicMap.containsKey(epicId)) {
            Epic tmpEpic = epicMap.get(epicId);
            tmpEpic.removeSubTask(id);
        }
    }
}
