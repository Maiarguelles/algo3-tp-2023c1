import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddReminderView implements Initializable {

    @FXML
    private DatePicker datePicker1;

    @FXML
    private DatePicker datePicker2;

    public void setEventName(String string) {
        this.eventName.setText(string);
    }

    @FXML
    private TextField eventName;

    @FXML
    private CheckBox allDay;

    @FXML
    private MenuButton repetition;

    @FXML
    private ColorPicker color;

    @FXML
    private TextArea description;

    @FXML
    private MenuButton hour1;

    @FXML
    private MenuButton hour2;

    @FXML
    private MenuButton alarm;
    @FXML
    private TextField timeBefore;

    @FXML
    private MenuButton timeFormat;

    @FXML
    private Button save;



    public Scene setView(Stage stage, Parent root) {

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        return scene;
    }

    public Button getSave() {
        return save;
    }

    public LocalDate getDatePicker1() {
        return datePicker1.getValue();
    }

    public LocalDate getDatePicker2() {
        return datePicker2.getValue();
    }

    public String getEventName() {
        return eventName.getText();
    }

    public boolean getAllDay() {
        return allDay.isSelected();
    }

    public MenuButton getRepetition() {
        return repetition;
    }

    public ColorPicker getColor() {
        return color;
    }

    public String getDescription() {
        return description.getText();
    }

    public String getHour1() {
        return hour1.getText();
    }

    public String getHour2() {
        return hour2.getText();
    }

    public String getAlarm() {
        return alarm.getText();
    }

    public String getTimeBefore() {
        return timeBefore.getText();
    }

    public String getTimeFormat() {
        return timeFormat.getText();
    }

    public void notifyButtonPress(EventHandler<ActionEvent> eventHandler){
        save.setOnAction(eventHandler);
    }


    public void a(){
        eventName.setText("Hola");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void handleMenuItems1(EventHandler<ActionEvent> eventHandler){
        for(int i=0; i< hour1.getItems().size(); i++){
            hour1.getItems().get(i).setOnAction(eventHandler);
        }
    }

    public void initializeHourPickers(){
        hour1.setText("Elegir hora");
        hour2.setText("Elegir hora");
    }

    public void handleMenuItems2(EventHandler<ActionEvent> eventHandler){
        for(int i=0; i< hour1.getItems().size(); i++){
            hour2.getItems().get(i).setOnAction(eventHandler);
        }
    }

    public void notifyHourSelection(EventHandler<ActionEvent> eventHandler){
        hour1.setOnAction(eventHandler);
    }
    public void notifyHourSelection2(EventHandler<ActionEvent> eventHandler){
        hour2.setOnAction(eventHandler);
    }


    @FXML
    public void handleButtonAction() {
    }

    public void a(){

    }

}



