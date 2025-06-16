import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    private List<Task> tasks = TaskStorage.loadTasks();
    private ListView<String> taskListView = new ListView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Task Manager");

        TextField taskInput = new TextField();
        taskInput.setText("Enter new task");

        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button completeButton = new Button("Mark as complete");

        updateTaskList();

        addButton.setOnAction(e -> {
            String description = taskInput.getText().trim();
            if (!description.isEmpty()) {
                tasks.add(new Task(description));
                TaskStorage.saveTasks(tasks);
                updateTaskList();
                taskInput.setText("");
            }
        });

        deleteButton.setOnAction(e -> {
            int selectedIdx = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIdx >= 0) {
                tasks.remove(selectedIdx);
                TaskStorage.saveTasks(tasks);
                updateTaskList();
            }
        });

        completeButton.setOnAction(e -> {
            int selectedIdx = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIdx >= 0) {
                tasks.get(selectedIdx).setCompleted(true);
                TaskStorage.saveTasks(tasks);
                updateTaskList();
            }
        });

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(taskInput, addButton, completeButton, deleteButton, taskListView);
        layout.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateTaskList() {
        taskListView.getItems().clear();
        for (Task task : tasks) {
            String label = (task.isCompleted() ? "[X] " : "[ ] ") + task.getDescription();
            taskListView.getItems().add(label);
        }
    }
}