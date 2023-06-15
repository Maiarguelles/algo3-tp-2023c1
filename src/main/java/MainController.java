import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDateTime.*;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainController{

    private Calendar calendar;

    private MainView mainView;

    private StageState stageState;

    private LocalDateTime stateDate1;

    private LocalDateTime stateDate2;


    public MainController(FXMLLoader mainLoader, Parent mainRoot, FXMLLoader addReminderLoader, Parent addReminderRoot, Calendar calendar, FXMLLoader replaceThingsLoader, MainView mainview) {
        this.calendar = calendar;
        this.mainView = mainview;
        this.stageState = StageState.DAILY;
        this.stateDate1 = LocalDate.now().atStartOfDay();
        this.stateDate2 = LocalDate.now().atTime(23, 59,59);
    }


    public void initialize() {

        mainView.getLabel().setText(LocalDate.now().toString());
        displayReminderBetweenTwoDates(stateDate1,stateDate2);
        mainView.getDatePicker().setValue(LocalDate.now());

        mainView.notifySelectDaily(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                try {
                    displayReminderBetweenTwoDates(day.atStartOfDay(), day.atTime(23, 59,59));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stageState = stageState.DAILY;
                stateDate1 = day.atStartOfDay();
                stateDate2 = day.atTime(23, 59,59);

            }
        });

        mainView.notifySelectMonthly(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                int month = day.getMonthValue();
                int year = day.getYear();
                LocalDateTime start = LocalDateTime.of(year, month, 1,0,0);
                displayReminderBetweenTwoDates(start, start.plusMonths(1).minusDays(1));
                mainView.getDatePicker().setValue(null);
                stageState = StageState.MONTHLY;
                stateDate1 = start;
                stateDate2 = start.plusMonths(1).minusDays(1);
            }
        });

        mainView.notifySelectWeekly(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                int dayOfWeek = day.getDayOfWeek().getValue();
                LocalDateTime start = day.minusDays(dayOfWeek-1).atStartOfDay();
                try {
                    displayReminderBetweenTwoDates(start, start.plusDays(7));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stageState = stageState.WEEKLY;
                stateDate1 = start;
                stateDate2 = start.plusDays(7);
            }
        });

        mainView.notifySelectNewEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader addReminderLoader2 = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));

                Stage stage = new Stage();
                try {
                    Parent root = (Parent) addReminderLoader2.load();
                    AddReminderView view2 = addReminderLoader2.getController();
                    view2.setView(stage, root);
                    AddReminderController addReminderController = new AddReminderController(view2, calendar);
                    addReminderController.initialize();

                    displayReminderBetweenTwoDates(stateDate1, stateDate2);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        mainView.notifySelectNewTask(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader addReminderLoader2 = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));
                Stage stage = new Stage();
                try {
                    Parent root = (Parent) addReminderLoader2.load();
                    AddReminderView view2 = addReminderLoader2.getController();
                    view2.setView(stage, root);

                    view2.getEventName().setPromptText("Inserte el nombre de la tarea");

                    view2.getAllDayHbox().getChildren().remove(view2.getRepetition());

                    HBox dateChooser = view2.getDateChooser();
                    dateChooser.getChildren().remove(view2.getA());
                    dateChooser.getChildren().remove(view2.getHour2());
                    dateChooser.getChildren().remove(view2.getDatePicker2());
                    Text text = new Text("Ingrese la fecha de inicio de la tarea");
                    dateChooser.getChildren().add(0, text);
                    AddReminderController addReminderController = new AddReminderController(view2, calendar);
                    addReminderController.initialize();
                    displayReminderBetweenTwoDates(stateDate1, stateDate2);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }


    public void displayReminderBetweenTwoDates(LocalDateTime date1, LocalDateTime date2) {
        mainView.getListOfReminders().getChildren().clear();
        List<Reminder> listOfReminders = calendar.remindersBetweenTwoDates(date1, date2);

        for(int i = 0; i < listOfReminders.size(); i++){
            mainView.getListOfReminders().getChildren().add(createDisplay(listOfReminders.get(i)));
        }

    }

    private Button createDisplay(Reminder reminder) throws IOException {
        FXMLLoader displayReminderloader = new FXMLLoader(getClass().getResource("/Fxml/DisplayReminder.fxml"));
        try {
            displayReminderloader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DisplayReminderView displayReminderView = displayReminderloader.getController();
        displayReminderView.getReminderName().setText(reminder.getTitle());


        if(reminder.getClass() == Event.class || reminder.getClass() == InfiniteEvent.class){
            displayReminderView.getDisplayVbox().getChildren().remove(displayReminderView.getCompleted());
            if(reminder.isCompleteDay())
                displayReminderView.getReminderDate().setText(reminder.getStartDate().toLocalDate().toString() + " - " +
                        ((Event) reminder).getEndDate().toLocalDate().toString());
            else{
                displayReminderView.getReminderDate().setText(reminder.getStartDate().toString() + " - " +
                        ((Event) reminder).getEndDate().toString());
            }
        } else if (reminder.getClass() == Task.class) {
            if(reminder.isCompleteDay())
                displayReminderView.getReminderDate().setText(reminder.getStartDate().toLocalDate().toString());
            else
                displayReminderView.getReminderDate().setText(reminder.getStartDate().toString());


        }
        return displayReminderView.getButtonDisplay();
    }



    enum StageState{
        DAILY,
        WEEKLY,
        MONTHLY
    }
}

