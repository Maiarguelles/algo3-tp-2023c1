import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    @FXML
    private Button delete;

    @FXML
    private Button buttonDisplay;


    private Reminder reminder;

    @FXML
    private Label reminderID;

    public Pane getMainPane() {
        return mainPane;
    }

    @FXML
    private Pane mainPane;


    public Label getReminderID(){
        return reminderID;
    }



    public void notifyDelete(EventHandler<ActionEvent> eventHandler){
        delete.setOnAction(eventHandler);
    }

    public void notifyButtonDisplay(EventHandler<ActionEvent> eventHandler){
        buttonDisplay.setOnAction(eventHandler);
    }

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

    public Button getDeleteButton(){
        return delete;
    }

    public void setID(int ID){
        this.reminderID.setText(Integer.toString(ID));
    }

}
