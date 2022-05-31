package tasks;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description, Status status) {
        super(name, description, status);
    }

    public SubTask(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public void setEpicId(int epicId) {
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
