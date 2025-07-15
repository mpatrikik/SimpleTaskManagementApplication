import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class TaskServiceTest {


    @BeforeEach
    void setUp() {
        // Reset the task storage before each test
        TaskStorage.setFileName("src/test/resources/tasks_test.json");
        TaskStorage.saveTasks(new ArrayList<>());
    }

    @Test
    public void testAddTask() {
        TaskService taskService = new TaskService();
        Task task = new Task("Test Task");
        taskService.addTask(task);

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getDescription());
    }
}
