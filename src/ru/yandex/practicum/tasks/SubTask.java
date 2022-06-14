package ru.yandex.practicum.tasks;

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

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }


    @Override
    public String toString() {
        return "tasks.SubTask{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status=" + this.getStatus() + '\'' +
                ", epicId=" + epicId +
                '}';
    }
}
