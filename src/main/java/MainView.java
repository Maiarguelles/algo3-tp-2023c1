import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.StringReader;

public class MainView {

    private FXMLLoader view;

    @FXML
    private Button previous;
    @FXML
    private Button next;

    public Button getDaily() {
        return daily;
    }

    @FXML
    private Button daily;
    @FXML
    private Button weekly;
    @FXML
    private Button monthly;
    @FXML
    private MenuItem addEvent;
    @FXML
    private MenuItem addTask;

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    @FXML
    private DatePicker datePicker;
    @FXML
    private Label date;

    @FXML
    private Label hour;

    @FXML
    private VBox listOfReminders;

    private Stage stage;

    public MainView(){

    }


    public ArrayList<DisplayReminderView> getDisplayReminderList() {
        return displayReminderList;
    }

    public Stage getStage(){
        return stage;
    }

    public Label getLabel(){
        return date;
    }

    public Label getHour(){
        return hour;
    }

    public VBox getListOfReminders(){
        return listOfReminders;
    }

    public Scene setView(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
        return scene;
    }



    public void adjustVboxHeight(){
        if(listOfReminders.getChildren().size()>4)
            listOfReminders.setMinHeight((listOfReminders.getHeight() + 100));
    }

    public void notifySelectNewEvent(EventHandler<ActionEvent> eventHandler){
        addEvent.setOnAction(eventHandler);
    }

    public void notifyButtonDisplaysOfReminder(EventHandler<ActionEvent> eventHandler){
       if (listOfReminders == null)
           return;
        for(int i=0; i< listOfReminders.getChildren().size(); i++){
            Button button = (Button) listOfReminders.getChildren().get(i);
            button.setOnAction(eventHandler);
        }
    }

    public void notifyDeleteReminder(EventHandler<ActionEvent> eventHandler){
        if (displayReminderList == null)
            return;
        for(int i=0; i< displayReminderList.size(); i++){
            VBox buttonVBox = displayReminderList.get(i).getDisplayVbox();
            Pane pane = (Pane) buttonVBox.getChildren().get(0);
            Button trash = (Button) pane.getChildren().get(2);
            trash.setOnAction(eventHandler);
        }
    }

    public void notifyCheckBoxDisplayOfReminder(EventHandler<ActionEvent> eventHandler){
        if (displayReminderList == null)
            return;

        for(int i=0; i< displayReminderList.size(); i++){
            VBox buttonVbox = displayReminderList.get(i).getDisplayVbox();

            if(buttonVbox.getChildren().size() > 1) {
                CheckBox checkBox = (CheckBox) buttonVbox.getChildren().get(1);
                checkBox.setOnAction(eventHandler);
            }
        }
    }
    
    public void notifyCloseAplication(EventHandler<WindowEvent> eventHandler){
        stage.setOnCloseRequest(eventHandler);
    }

    public void notifySelectNewTask(EventHandler<ActionEvent> eventHandler){
        addTask.setOnAction(eventHandler);
    }

    public void notifySelectDaily(EventHandler<ActionEvent> eventHandler){
        daily.setOnAction(eventHandler);
    }

    public void notifySelectMonthly(EventHandler<ActionEvent> eventHandler){
        monthly.setOnAction(eventHandler);
    }

    public void notifySelectWeekly(EventHandler<ActionEvent> eventHandler){
        weekly.setOnAction(eventHandler);
    }

    public void notifySelectNext(EventHandler<ActionEvent> eventHandler){
        next.setOnAction(eventHandler);
    }
    public void notifySelectPrevious(EventHandler<ActionEvent> eventHandler){
        previous.setOnAction(eventHandler);
    }

    public void notifyDatePickerSelection(EventHandler<ActionEvent> eventHandler){
        datePicker.setOnAction(eventHandler);
    }

}
