import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private final TaskService taskService = new TaskService();
    private ListView<Task> taskListView = new ListView<>();
    private ComboBox<String> filterBox = new ComboBox<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Task Manager");

        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter new task");

        filterBox.getItems().addAll("All", "Completed", "Incomplete");
        filterBox.setValue("All");
        filterBox.setOnAction(e -> updateTaskList());

        Button addButton = new Button("Add");
        Button completeButton = new Button("Mark as completed ✓");
        Button incompleteButton = new Button("Mark as incomplete ❓");
        Button deleteButton = new Button("Delete");

        updateTaskList();

        addButton.setOnAction(e -> {
            String description = taskInput.getText().trim();
            try {
                taskService.addTask(new Task(description));
                updateTaskList();
                taskInput.clear();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Required");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in the task description before adding.");
                alert.showAndWait();
            }
        });

        completeButton.setOnAction(e -> {
            for (Task task : taskService.getAllTasks()) {
                if (task.isSelected() && !task.isCompleted()) {
                    task.setCompleted(true);
                }
            }
            taskService.saveTasks();
            updateTaskList();
        });

        incompleteButton.setOnAction(e -> {
            updateTaskList();
            for (Task task : taskService.getAllTasks()) {
                if (task.isSelected() && task.isCompleted()) {
                    task.setCompleted(false);
                }
            }
            taskService.saveTasks();
            updateTaskList();
        });


        deleteButton.setOnAction(e -> {
            List<Task> toDelete = new ArrayList<>();
            for (Task task : taskService.getAllTasks()) {
                if (task.isSelected()) {
                    toDelete.add(task);
                }
            }
            for (Task task : toDelete) {
                taskService.deleteTask(task.getId());
            }
            taskService.saveTasks();
            updateTaskList();
        });


        HBox ListingAndFilter = new HBox(10, addButton, filterBox);
        HBox statusButtons = new HBox(10, completeButton, incompleteButton);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(taskInput, ListingAndFilter, statusButtons, deleteButton, taskListView);

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        taskListView.setCellFactory(lv -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();
            {
                checkBox.setOnAction(e -> {
                    Task task = getItem();
                    if (task != null) {
                        task.setSelected(checkBox.isSelected());
                    }
                });
            }

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    checkBox.setSelected(task.isSelected());
                    checkBox.setText(task.getDescription() + (task.isCompleted() ? " ✓" : " ❓"));
                    setGraphic(checkBox);
                }
            }
        });
    }

    private void updateTaskList() {
        taskListView.getItems().clear();
        String filter = filterBox.getValue();

        for (Task task : taskService.getAllTasks()) {
            boolean shouldShow = switch (filter) {
                case "Completed" -> task.isCompleted();
                case "Incomplete" -> !task.isCompleted();
                default -> true;
            };

            if (shouldShow) {
                String displayText = task.getDescription() + (task.isCompleted() ? " ✓" : " ❓");
                taskListView.getItems().add(task);
            }
        }
    }



}