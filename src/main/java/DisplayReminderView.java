import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;



public class DisplayReminderView {

    public Label getReminderName() {
        return reminderName;
    }

    public Label getReminderDate() {
        return reminderDate;
    }

    public CheckBox getCompleted() {
        return completed;
    }

    public void setReminderName(Label reminderName) {
        this.reminderName = reminderName;
    }

    public void setReminderDate(Label reminderDate) {
        this.reminderDate = reminderDate;
    }

    public void setCompleted(CheckBox completed) {
        this.completed = completed;
    }

    @FXML
    private Label reminderName;
    @FXML
    private Label reminderDate;
    @FXML
    private CheckBox completed;

    @FXML
    private VBox displayVbox;

    public VBox getDisplayVbox() {
        return displayVbox;
    }

    public void setDisplayVbox(VBox displayVbox) {
        this.displayVbox = displayVbox;
    }

    public Button getButtonDisplay() {
        return buttonDisplay;
    }

    public void setButtonDisplay(Button buttonDisplay) {
        this.buttonDisplay = buttonDisplay;
    }

    @FXML
    private Button buttonDisplay;

}
