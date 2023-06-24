package GUI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FullDisplayReminderView {

    @FXML
    private Label reminderName;
    @FXML
    private CheckBox completed;
    @FXML
    private Label reminderDate;
    @FXML
    private Label reminderRepetition;
    @FXML
    private Label reminderDescription;
    @FXML
    private VBox alarmVbox;
    @FXML
    private VBox mainVbox;



    public Scene setView(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        completed.setDisable(true);
        
        stage.show();
        return scene;
    }

    public Label getReminderName() {
        return reminderName;
    }

    public VBox getMainVbox() {
        return mainVbox;
    }

    public CheckBox getCompleted() {
        return completed;
    }

    public Label getReminderDate() {
        return reminderDate;
    }

    public Label getReminderRepetition() {
        return reminderRepetition;
    }

    public Label getReminderDescription() {
        return reminderDescription;
    }

    public VBox getAlarmVbox() {
        return alarmVbox;
    }

}
