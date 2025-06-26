import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
        Button saveButton = new Button("Save \uD83D\uDCBE");
        Button completeButton = new Button("Mark as completed ✓");
        Button incompleteButton = new Button("Mark as incomplete ❓");
        Button deleteButton = new Button("Delete");

        updateTaskList();

        addButton.setOnAction(e -> {
            String description = taskInput.getText().trim();
            if (!description.isEmpty()) {
                tasks.add(new Task(description));
                TaskStorage.saveTasks(tasks);
                updateTaskList();
                taskInput.clear();
            }
        });

        saveButton.setOnAction(e -> {
                    TaskStorage.saveTasks(tasks);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tasks saved successfully!");
                    alert.setTitle("Save Confirmation");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                });

        completeButton.setOnAction(e -> {
            int selectedId = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedId >= 0 && selectedId < tasks.size()) {
                Task task = tasks.get(selectedId);
                if (!task.isCompleted()) {
                    task.setCompleted(true);
                    TaskStorage.saveTasks(tasks);
                    updateTaskList();
                }

            }
        });

        incompleteButton.setOnAction(e -> {
            int selectedId = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedId >= 0 && selectedId < tasks.size()) {
                Task task = tasks.get(selectedId);
                if (task.isCompleted()) {
                    task.setCompleted(false);
                    TaskStorage.saveTasks(tasks);
                    updateTaskList();
                }
            }
        });

        deleteButton.setOnAction(e -> {
            int selectedId = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedId >= 0 && selectedId < tasks.size()) {
                tasks.remove(selectedId);
                TaskStorage.saveTasks(tasks);
                updateTaskList();
            }
        });


        HBox statusButtons = new HBox(10, completeButton, incompleteButton);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(taskInput, addButton, statusButtons, saveButton, deleteButton, taskListView);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateTaskList() {
        taskListView.getItems().clear();
        for (Task task : tasks) {
            String label = (task.isCompleted() ? "[✓] " : "[❓] ") + task.getDescription();
            taskListView.getItems().add(label);
        }
    }
}