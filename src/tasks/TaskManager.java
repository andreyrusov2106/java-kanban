package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private int currentId;
    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final Map<Integer, Epic> epicMap = new HashMap<>();
    private final Map<Integer, SubTask> subTaskMap = new HashMap<>();

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
        return taskMap.get(id);
    }

    //methods for Epics
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicMap.values());
    }

    public void clearAllEpics() {
        epicMap.clear();
        subTaskMap.clear();
    }

    public Epic getEpicById(int id) {
        return epicMap.get(id);
    }

    public int createEpic(Epic task) {
        if (task != null) {
            currentId++;
            task.setId(currentId);
            epicMap.put(currentId, task);
            checkEpicStatus(currentId);
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
    public List<SubTask> getAllSubTasksByEpicId(int epicId) {
        List<SubTask> subTasks = new ArrayList<>();
        for (Integer subTaskId : epicMap.get(epicId).getSubTaskIds()) {
            subTasks.add(subTaskMap.get(subTaskId));
        }
        return subTasks;
    }

    public void clearAllSubTasksByEpicId(int epicId) {
        if (epicMap.containsKey(epicId)) {
            for (Integer subTaskId : epicMap.get(epicId).getSubTaskIds()) {
                subTaskMap.remove(subTaskId);
            }
            epicMap.get(epicId).clearAllSubTasks();
            checkEpicStatus(epicId);
        }
    }

    public SubTask getSubTaskById(int subTaskId) {
        return subTaskMap.get(subTaskId);
    }

    public void createSubTask(SubTask task) {
        int epicId = task.getEpicId();
        if (epicMap.containsKey(epicId)) {
            currentId++;
            task.setId(currentId);
            Epic tmpEpic = epicMap.get(epicId);
            tmpEpic.addSubTaskIds(currentId);
            subTaskMap.put(currentId, task);
            checkEpicStatus(epicId);
        }
    }

    public void updateSubTask(SubTask task) {
        if (subTaskMap.containsKey(task.getId())) {
            subTaskMap.put(task.getId(), task);
        }
    }

    public void removeSubTaskById(int subTaskId) {
        if (subTaskMap.containsKey(subTaskId)) {
            int epicId = subTaskMap.get(subTaskId).getEpicId();
            if (epicMap.containsKey(epicId)) {
                Epic tmpEpic = epicMap.get(epicId);
                tmpEpic.removeSubTaskIds(subTaskId);
                subTaskMap.remove(subTaskId);
                checkEpicStatus(tmpEpic.getId());
            }
        }
    }

    private void checkEpicStatus(int epicId) {
        List<SubTask> subTasks = getAllSubTasksByEpicId(epicId);
        Epic epic = epicMap.get(epicId);
        if (subTasks.isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        ArrayList<Status> statuses = new ArrayList<>();
        for (SubTask subTask : subTaskMap.values()) {
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
