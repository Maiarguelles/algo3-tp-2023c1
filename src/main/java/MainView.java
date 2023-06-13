import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    private FXMLLoader view;

    @FXML
    private Button previous;
    @FXML
    private Button next;
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
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label date;
    @FXML
    private VBox listOfReminders;



    public MainView(){

    }


    public Scene setView(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        return scene;
    }

    public void notifySelectNewEvent(EventHandler<ActionEvent> eventHandler){
        addEvent.setOnAction(eventHandler);
    }

    /*
    public void notifySelectedNewTask(EventHandler<ActionEvent> eventHandler){
        addTask.setOnAction(eventHandler);
    }

     */
}
