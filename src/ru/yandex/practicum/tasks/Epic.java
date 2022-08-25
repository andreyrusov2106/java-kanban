package ru.yandex.practicum.tasks;

import ru.yandex.practicum.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description);
    }

    List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    void addSubTaskIds(int id) {
        subTaskIds.add(id);
    }

    void removeSubTaskIds(Integer id) {
        subTaskIds.remove(id);
    }

    public void clearAllSubTasks() {
        subTaskIds.clear();
    }

    @Override
    public String toString() {
        String strTime = "";
        if (getStartTime() != null) {
            strTime = ", startTime=" + this.getStartTime() +
                    ", duration=" + this.getDuration() +
                    ", endTime=" + this.getEndTime();
        }
        return "Epic{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status=" + this.getStatus() + '\'' +
                ", subTaskIds=" + subTaskIds +
                strTime
                +
                '}';
    }
}
