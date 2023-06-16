import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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


        mainView.getDateLabel().setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT));
        displayReminderBetweenTwoDates(stateDate1,stateDate2);


        updateDisplays();


        mainView.notifyDeleteReminder(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = (Button) actionEvent.getSource();
                Pane pane = (Pane) button.getParent();
                VBox vbox = (VBox) pane.getParent();
                Button display = (Button) vbox.getParent();

                Label id = (Label) pane.getChildren().get(3);
                System.out.println(id.getText());

                calendar.deleteReminder(Integer.parseInt(id.getText()));
                mainView.getListOfReminders().getChildren().remove(display);
                updateDisplays();

            }
        });

       mainView.notifyCheckBoxDisplayOfReminder(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CheckBox checkbox = (CheckBox) actionEvent.getSource();
                boolean state = checkbox.isSelected();
                VBox vbox = (VBox) checkbox.getParent();
                Pane pane = (Pane) vbox.getChildren().get(0);
                Label id = (Label) pane.getChildren().get(3);
                Task task = (Task) calendar.getReminder(Integer.parseInt(id.getText()));

                task.setCompleteTask(state);

            }
        });

        mainView.notifyCloseAplication(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                closeCalendar();
            }
        });

        mainView.notifyButtonDisplaysOfReminder(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fullDisplayReminderLoader = new FXMLLoader(getClass().getResource("/Fxml/FullDisplayReminder.fxml"));
                Parent root = null;
                try {
                    root = (Parent)fullDisplayReminderLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FullDisplayReminderView fullDisplayReminderView = fullDisplayReminderLoader.getController();
                fullDisplayReminderView.setView(new Stage(), root);
                Button reminderButton = (Button)actionEvent.getSource();
                Pane pane = (Pane)reminderButton.getChildrenUnmodifiable().get(0);
                Label id = (Label) pane.getChildren().get(3);
                Reminder reminder = calendar.getReminder(Integer.parseInt(id.getText()));

                fullDisplayReminderView.getReminderName().setText(reminder.getTitle());
                fullDisplayReminderView.getReminderDescription().setText(reminder.getDescription());


                if(reminder.getClass() == Event.class )
                    if(reminder.isCompleteDay()){
                        fullDisplayReminderView.getReminderDate().setText(reminder.getStartDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT)
                                + "-----" + ((Event) reminder).getEndDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT)
                        );
                    }
                    else{
                        fullDisplayReminderView.getReminderDate().setText(reminder.getStartDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy  hh:mm").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT)
                                + "-----" + ((Event) reminder).getEndDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy  hh:mm").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT)
                        );
                    }
                    ((Pane)fullDisplayReminderView.getMainVbox().getChildren().get(0)).getChildren().remove(fullDisplayReminderView.getCompleted());
                    fullDisplayReminderView.getReminderRepetition().setText("");

                else if(reminder.getClass() == InfiniteEvent.class){
                    ((Pane)fullDisplayReminderView.getMainVbox().getChildren().get(0)).getChildren().remove(fullDisplayReminderView.getCompleted());
                }

                else {
                    fullDisplayReminderView.getCompleted().setSelected(((Task) reminder).isCompleted());
                }

                ArrayList<Alarm> alarms = reminder.getAlarms();

                for(int i = 0; i < alarms.size(); i++){
                    Label alarm = new Label();
                    alarm.setText(alarms.get(i).getMinBefore()");
                    fullDisplayReminderView.getAlarmVbox().getChildren().add(alarm);
                }
            }
        });

        mainView.getStage().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                updateDisplays();
            }
        });


        mainView.notifyDatePickerSelection(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                mainView.getDaily().fire();
            }
        });

        mainView.notifySelectNext(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                next();
                updateDisplays();
            }
        });

        mainView.notifySelectPrevious(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                previous();
                updateDisplays();
            }
        });

        mainView.notifySelectDaily(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                if(day == null)
                    day = LocalDate.now();

                displayReminderBetweenTwoDates(day.atStartOfDay(), day.atTime(23, 59,59));

                stageState = stageState.DAILY;
                stateDate1 = day.atStartOfDay();
                stateDate2 = day.atTime(23, 59,59);

                mainView.getDateLabel().setText(stateDate1.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT));
            }
        });

        mainView.notifySelectMonthly(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                if(day == null)
                    day = LocalDate.now();
                int month = day.getMonthValue();
                int year = day.getYear();
                LocalDateTime start = LocalDateTime.of(year, month, 1,0,0);
                displayReminderBetweenTwoDates(start, start.plusMonths(1).minusDays(1));
                mainView.getDatePicker().setValue(null);
                stageState = StageState.MONTHLY;
                stateDate1 = start;
                stateDate2 = start.plusMonths(1);

                mainView.getDateLabel().setText(stateDate1.format(DateTimeFormatter.ofPattern("MMM-yyyy").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT));
            }
        });

        mainView.notifySelectWeekly(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                if(day == null)
                    day = LocalDate.now();
                int dayOfWeek = day.getDayOfWeek().getValue();
                LocalDateTime start = day.minusDays(dayOfWeek-1).atStartOfDay();
                displayReminderBetweenTwoDates(start, start.plusDays(7));

                mainView.getDatePicker().setValue(null);
                stageState = stageState.WEEKLY;
                stateDate1 = start;
                stateDate2 = start.plusDays(7);

                mainView.getDateLabel().setText(stateDate1.format(DateTimeFormatter.ofPattern("MMM  dd")).toUpperCase(Locale.ROOT)+ " - " + stateDate2.format(DateTimeFormatter.ofPattern("dd")));
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

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }


    public void next(){
        if(stageState == StageState.DAILY){
            stateDate1 = stateDate1.plusDays(1);
            stateDate2 = stateDate2.plusDays(1);
        }
        else if(stageState == stageState.WEEKLY){
            stateDate1 = stateDate1.plusWeeks(1);
            stateDate2 = stateDate2.plusWeeks(1);
            mainView.getDateLabel().setText(stateDate1.format(DateTimeFormatter.ofPattern("MMM  dd")).toUpperCase(Locale.ROOT)+ " - " + stateDate2.format(DateTimeFormatter.ofPattern("dd")));
        }
        else{
            stateDate1 = stateDate1.plusMonths(1);
            stateDate2 = stateDate2.plusMonths(2);
            mainView.getDateLabel().setText(stateDate1.format(DateTimeFormatter.ofPattern("MMM-yyyy").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT));
        }
    }


    public void previous(){
        if(stageState == StageState.DAILY){
            stateDate1 = stateDate1.minusDays(1);
            stateDate2 = stateDate2.minusDays(1);
        }
        else if(stageState == stageState.WEEKLY){
            stateDate1 = stateDate1.minusWeeks(1);
            stateDate2 = stateDate2.minusWeeks(1);
            mainView.getDateLabel().setText(stateDate1.format(DateTimeFormatter.ofPattern("MMM  dd")).toUpperCase(Locale.ROOT)+ " - " + stateDate2.format(DateTimeFormatter.ofPattern("dd")));


        }
        else{
            stateDate1 = stateDate1.minusMonths(1);
            stateDate2 = stateDate2.minusMonths(2);
            mainView.getDateLabel().setText(stateDate1.format(DateTimeFormatter.ofPattern("MMM-yyyy").withLocale(new Locale("es"))).toUpperCase(Locale.ROOT));
        }
    }

    public void updateDisplays(){
        displayReminderBetweenTwoDates(stateDate1, stateDate2);
    }


    public void displayReminderBetweenTwoDates(LocalDateTime date1, LocalDateTime date2) {
        mainView.getListOfReminders().getChildren().clear();
        List<Reminder> listOfReminders = calendar.remindersBetweenTwoDates(date1, date2);

        for(int i = 0; i < listOfReminders.size(); i++){

            mainView.getListOfReminders().getChildren().add(createDisplay(listOfReminders.get(i)));
        }

    }

    private Button createDisplay(Reminder reminder) {
        FXMLLoader displayReminderloader = new FXMLLoader(getClass().getResource("/Fxml/DisplayReminder.fxml"));
        try {
            displayReminderloader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DisplayReminderView displayReminderView = displayReminderloader.getController();
        displayReminderView.getReminderName().setText(reminder.getTitle());
        displayReminderView.getReminderID().setText(Integer.toString(reminder.hashCode()));

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
            else {
                displayReminderView.getReminderDate().setText(reminder.getStartDate().toString());
            }
            System.out.print("CUANDO CREO EL DISPLAY:");
            System.out.println(((Task) reminder).isCompleted());
            System.out.println(((Task) reminder).isCompleted());
            displayReminderView.getCompleted().setSelected(((Task) reminder).isCompleted());
        }
        return displayReminderView.getButtonDisplay();
    }


    enum StageState{
        DAILY,
        WEEKLY,
        MONTHLY
    }


    public void closeCalendar(){
        FileWriter file2 = null;
        try {

            file2 = new FileWriter("Calendar.json");
            System.out.println(calendar.writeCalendar(file2));
            file2.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

