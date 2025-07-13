import java.util.UUID;

public class Task {
    private String id;
    private String description;
    private boolean completed;
    private transient boolean selected;

    public Task(String description) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.completed = false;
    }

    public String getId() {
        return id;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
