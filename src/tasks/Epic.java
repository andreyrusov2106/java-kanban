package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Epic extends Task {
    private final HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
        checkStatus();
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
        checkStatus();
    }

    @Override
    void setStatus(Status status) {
        super.setStatus(status);
    }

    void checkStatus() {
        if (subTaskMap.isEmpty()) {
            this.setStatus(Status.NEW);
        }
        ArrayList<Status> statuses = new ArrayList<>();
        for (SubTask subTask : subTaskMap.values()) {
            statuses.add(subTask.getStatus());
        }
        if (!statuses.contains(Status.DONE) && !statuses.contains(Status.IN_PROGRESS)) {
            this.setStatus(Status.NEW);
        } else if (!statuses.contains(Status.NEW) && !statuses.contains(Status.IN_PROGRESS)) {
            this.setStatus(Status.DONE);
        } else {
            this.setStatus(Status.IN_PROGRESS);
        }
    }

    Task getSubTaskById(int id) {
        return subTaskMap.getOrDefault(id, null);
    }

    List<Task> getAllSubTasks() {
        return new ArrayList<>(subTaskMap.values());
    }

    void clearAllSubTasks() {
        subTaskMap.clear();
    }

    void addSubTask(int id, SubTask subTask) {
        subTaskMap.put(id, subTask);
        checkStatus();
    }

    void updateSubTask(SubTask task) {
        if (task != null && subTaskMap.containsKey(task.getId())) {
            subTaskMap.put(task.getId(), task);
            checkStatus();
        }
    }

    void removeSubTask(int taskId) {
        if (subTaskMap.containsKey(taskId)) {
            subTaskMap.remove(taskId);
            checkStatus();
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status='" + this.getStatus() + '\'' +
                ", subTaskMap=" + subTaskMap +
                '}';
    }
}
