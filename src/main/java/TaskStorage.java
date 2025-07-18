import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    private static String FILE_NAME = "src/main/resources/tasks.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void setFileName(String fileName) {
            FILE_NAME = fileName;
    }

    static {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(FILE_NAME)) {
                gson.toJson(new ArrayList<>(), writer);
                System.out.println("Created new empty tasks file: " + FILE_NAME);
            } catch (IOException e) {
                System.err.println("Error creating tasks file: " + e.getMessage());
            }
        }
    }

    public static List<Task> loadTasks() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type taskListType = new TypeToken<List<Task>>(){}.getType();
            List<Task> tasks = gson.fromJson(reader, taskListType);
            return tasks != null ? tasks : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error loading tasks.json file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.err.println("Error saving tasks.json file: " + e.getMessage());
        }
    }
}
