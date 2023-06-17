import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FullDisplayReminderView {
    public Label getReminderName() {
        return reminderName;
    }

    @FXML
    private Label reminderName;

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

    public VBox getMainVbox() {
        return mainVbox;
    }

    @FXML
    private VBox mainVbox;

    public Scene setView(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        return scene;
    }
}
