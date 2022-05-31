package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subTaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    void addSubTaskIds(int id) {
        subTaskIds.add(id);
    }

    void removeSubTaskIds(Integer id) {
        subTaskIds.remove(id);
    }

    void clearAllSubTasks() {
        subTaskIds.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status=" + this.getStatus() + '\'' +
                ", subTaskIds=" + subTaskIds +
                '}';
    }
}
