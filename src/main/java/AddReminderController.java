import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddReminderController {
    private AddReminderView view;
    private Calendar calendar;
    private DisplayReminderView displayView;

    private MainView mainView;


    public AddReminderController(AddReminderView view, Calendar calendar){
        this.calendar = calendar;
        this.view = view;
    }




    public void initialize(){

        view.notifySavePress(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(view.getHour1().getItems().get(0).getText());
                String title = view.getEventName();
                String description = view.getDescription();
                LocalDate startdate = view.getDatePicker1();
                LocalDate enddate = view.getDatePicker2();
                Boolean completeDay = view.getAllDay();
                LocalDateTime startDate = null;
                LocalDateTime endDate =null;
                if(!completeDay){
                    startDate = startdate.atTime(stringToLocalTime(view.getHour1().getText()));
                    endDate =  enddate.atTime(stringToLocalTime(view.getHour2().getText()));
                }
                else {
                    startDate = startdate.atStartOfDay();
                    endDate =  enddate.atStartOfDay();
                }
            }
        });


        view.notifyAllDayCheckBox(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(view.getEventName().getPromptText().equals("Inserte nombre del evento")){
                    allDayCheckBoxEvent();
                }
                else{
                    allDayCheckBoxTask();
                }

            }
        });


        view.handleMenuItems1(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                view.getHour1().setText(item.getText());


            }
        });

        view.handleMenuItems2(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                view.getHour2().setText(item.getText());

            }
        });

    }

    private LocalTime stringToLocalTime(String time){
        String parsed = null;
        if(time.charAt(5) == 'p'){
            var substring = time.substring(0,2);
            Integer a = Integer.parseInt(substring)+12;
            parsed = a.toString() + time.substring(2,5);
        }
        else{
            parsed = time.substring(0,5);
        }
        return LocalTime.parse(parsed);
    }

}
