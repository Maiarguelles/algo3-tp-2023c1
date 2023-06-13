import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controlador {
    FXMLLoader mainLoader;
    Parent mainRoot;
    FXMLLoader addReminderLoader;
    Parent addReminderRoot;
    FXMLLoader replaceThingsLoader;
    Calendar calendar;

    public Controlador(FXMLLoader mainLoader, Parent mainRoot,FXMLLoader addReminderLoader, Parent addReminderRoot,Calendar calendar, FXMLLoader replaceThingsLoader){
        this.mainLoader = mainLoader;
        this.mainRoot = mainRoot;
        this.calendar = calendar;
        this.addReminderLoader = addReminderLoader;
        this.addReminderRoot = addReminderRoot;
        this.replaceThingsLoader = replaceThingsLoader;
    }

    public void initialize(){
        MainView view = mainLoader.getController();



        view.notifySelectNewEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent){
                FXMLLoader addReminderLoader2 = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));

                Stage stage = new Stage();
                try {
                    Parent root = (Parent)addReminderLoader2.load();
                    AddReminderView view2 = addReminderLoader2.getController();
                    view2.setView(stage,root);
                    AddReminderController addReminderController = new AddReminderController(view2, calendar);
                    addReminderController.initialize();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });




    }

}
