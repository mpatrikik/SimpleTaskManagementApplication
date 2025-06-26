import java.util.UUID;

public class Task {
    private String id;
    private String description;
    private boolean completed;

    public Task(String description) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
