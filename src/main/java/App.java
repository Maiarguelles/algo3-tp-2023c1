import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Abrimos las views
        FXMLLoader eventDetailLoader =  new FXMLLoader(getClass().getResource("/Fxml/EventDetail.fxml"));
        FXMLLoader replaceThingsLoader = new FXMLLoader(getClass().getResource("/Fxml/ReplaceThings.fxml"));
        FXMLLoader addReminderLoader = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        System.out.println(replaceThingsLoader);


        //Cargamos las views
        Parent eventDetailRoot = (Parent) eventDetailLoader.load();
        Parent addReminderRoot = (Parent) addReminderLoader.load();
        Parent mainRoot = (Parent) mainLoader.load();



        //Parent replaceThingsRoot = (Parent) replaceThingsLoader.load();


        Calendar calendar = new Calendar(); //Modelo

        //Creamos el controlador principal
        MainView mainView = mainLoader.getController();
        MainController controlador = new MainController(mainLoader, mainRoot, addReminderLoader, addReminderRoot, calendar, replaceThingsLoader, mainView);

        mainView.setView(stage, mainRoot);

        Label hour = mainView.getHour();

        var timer = new AnimationTimer(){
            @Override
            public void handle(long tiempo){
                hour.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();
        //hour.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        controlador.initialize();


    }
    public static void main(String[] args){
        launch();
    }
}