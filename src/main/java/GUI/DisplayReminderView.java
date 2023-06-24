package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.CheckBox;



public class DisplayReminderView {

    @FXML
    private Pane mainPane;
    @FXML
    private Label reminderName;
    @FXML
    private Label reminderDate;
    @FXML
    private CheckBox completed;
    @FXML
    private Button delete;
    @FXML
    private Button buttonDisplay;
    @FXML
    private Label reminderID;


    public Pane getMainPane() {
        return mainPane;
    }
    public Label getReminderID(){
        return reminderID;
    }
    public Button getButtonDisplay() {
        return buttonDisplay;
    }
    public Button getDeleteButton(){
        return delete;
    }
    public void setID(int ID){
        this.reminderID.setText(Integer.toString(ID));
    }
    public Label getReminderName() {
        return reminderName;
    }
    public Label getReminderDate() {
        return reminderDate;
    }
    public CheckBox getCompleted() {
        return completed;
    }
}
