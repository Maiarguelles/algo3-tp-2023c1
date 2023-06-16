import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class App extends Application {

    public static Calendar calendar;
    @Override
    public void start(Stage stage) throws Exception {
        //Abrimos las views
        FXMLLoader eventDetailLoader =  new FXMLLoader(getClass().getResource("/Fxml/EventDetail.fxml"));
        FXMLLoader replaceThingsLoader = new FXMLLoader(getClass().getResource("/Fxml/ReplaceThings.fxml"));
        FXMLLoader addReminderLoader = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));


        //Cargamos las views
        Parent eventDetailRoot = (Parent) eventDetailLoader.load();
        Parent addReminderRoot = (Parent) addReminderLoader.load();
        Parent mainRoot = (Parent) mainLoader.load();





        FileReader file = null;
        try {
            file = new FileReader("Calendar.json");
            var calendarReaded = Calendar.readCalendar(new BufferedReader(file));
            if (calendarReaded != null){
                this.calendar = calendarReaded;

            }
            else{
                calendar = new Calendar();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        file.close();




        //Creamos el controlador principal
        MainView mainView = mainLoader.getController();
        MainController controlador = new MainController(calendar, mainView);

        mainView.setView(stage, mainRoot);
        controlador.initialize();

        Label hour = mainView.getHour();

        var timer = new AnimationTimer(){

            @Override
            public void handle(long tiempo){
                var time = LocalDateTime.now();
                hour.setText(time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                Alarm alarm = calendar.getNextAlarm(LocalDateTime.now());
                FXMLLoader notificationLoader = new FXMLLoader(getClass().getResource("/Fxml/Notification.fxml"));

                Parent notificationRoot = null;
                try {
                    notificationRoot = (Parent) notificationLoader.load();
                    NotificationView notificationView = notificationLoader.getController();
                    if(alarm!= null &&  alarm.shouldTrigger(time)){
                        notificationView.setView(new Stage(), notificationRoot);
                        Reminder reminder = calendar.getReminder(alarm.getID());
                        notificationView.getReminderName().setText(reminder.getTitle());
                        notificationView.getDateReminder().setText(reminder.getStartDate().toLocalDate().toString());
                        notificationView.getHourReminder().setText(reminder.getStartDate().toLocalTime().toString());

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.start();



        System.out.println("");


    }
    public static void main(String[] args){
        launch();
    }
}