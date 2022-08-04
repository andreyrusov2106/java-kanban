package ru.yandex.practicum.tasks;

import ru.yandex.practicum.enums.Status;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {


    private int epicId;

    public int getEpicId() {
        return epicId;
    }

    public SubTask(String name, String description, Status status) {
        super(name, description, status);
    }

    public SubTask(int id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public SubTask(int id, String name, String description, Status status, int epicId, LocalDateTime startTime, Duration duration) {
        super(id, name, description, status, startTime, duration);
        this.epicId = epicId;

    }

    public SubTask(String name, String description, Status status, int epicId, LocalDateTime startTime, Duration duration) {
        super(name, description, status, startTime, duration);
        this.epicId = epicId;

    }

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }


    @Override
    public String toString() {
        String strTime = "";
        if (getStartTime() != null) {
            strTime = ", startTime=" + this.getStartTime() +
                    ", duration=" + this.getDuration() +
                    ", endTime=" + this.getEndTime();
        }
        return "tasks.SubTask{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status=" + this.getStatus() + '\'' +
                ", epicId=" + epicId +
                strTime
                +
                '}';
    }
}
