import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController extends Controller {
    private FXMLLoader mainLoader;
    private Parent mainRoot;
    private FXMLLoader addReminderLoader;
    private Parent addReminderRoot;
    private FXMLLoader replaceThingsLoader;
    private Calendar calendar;

    private MainView mainView;

    public MainController(FXMLLoader mainLoader, Parent mainRoot, FXMLLoader addReminderLoader, Parent addReminderRoot, Calendar calendar, FXMLLoader replaceThingsLoader, MainView mainview) {
        this.mainLoader = mainLoader;
        this.mainRoot = mainRoot;
        this.calendar = calendar;
        this.addReminderLoader = addReminderLoader;
        this.addReminderRoot = addReminderRoot;
        this.replaceThingsLoader = replaceThingsLoader;
        this.mainView = mainview;
    }


    public void initialize() throws IOException {

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
                try {
                    displayReminderBetweenTwoDates(start, start.plusMonths(1).minusDays(1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
                    AddReminderController addReminderController = new AddReminderController(view2, calendar, mainView);
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
                    AddReminderController addReminderController = new AddReminderController(view2, calendar, mainView);
                    addReminderController.initialize();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
}
