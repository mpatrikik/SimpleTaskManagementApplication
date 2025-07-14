import java.util.ArrayList;
import java.util.List;


public class TaskService {
    private final List<Task> tasks;

    public TaskService() {
        this.tasks = TaskStorage.loadTasks();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        if (task == null || task.getDescription() == null || task.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }
        tasks.add(task);
        TaskStorage.saveTasks(tasks);
    }

    public void saveTasks() {
        TaskStorage.saveTasks(tasks);
    }

    public void deleteTask(String taskId) {
        tasks.removeIf(t -> t.getId().equals(taskId));
        TaskStorage.saveTasks(tasks);
    }
}
