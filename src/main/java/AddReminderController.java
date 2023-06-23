import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class AddReminderController{
    private AddReminderView view;
    private Calendar calendar;


    public AddReminderController(AddReminderView view, Calendar calendar){
        this.calendar = calendar;
        this.view = view;
    }


    public void initialize(){
        view.notifySavePress(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String alarm = view.getAlarm().getText();
                Reminder reminder = null;
                if(view.getEventName().getPromptText().equals("Inserte nombre del evento")){

                    reminder = createEvent();
                    if (reminder == null){

                        view.getWarningValidDate().setText("Inserte una fecha y hora válidas");
                    }
                    else {
                        calendar.addReminder(reminder);
                        if(!alarm.equals("Ninguna") && reminder != null) {
                            reminder.addAlarm(createAlarm(alarm));
                            calendar.updateNextAlarm(LocalDateTime.now());
                        }
                        view.closeStage();
                    }
                }
                else{
                    reminder = createTask();
                    calendar.addReminder(reminder);
                    if(!alarm.equals("Ninguna") && reminder != null) {
                        reminder.addAlarm(createAlarm(alarm));
                        calendar.updateNextAlarm(LocalDateTime.now());
                    }
                    view.closeStage();
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

        view.handleAlarmItem(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                view.getAlarm().setText(item.getText());
                if(item.getText().equals("Ninguna")){
                    view.getHboxAlarm().getChildren().remove(view.getTimeBefore());
                    view.getHboxAlarm().getChildren().remove(view.getTimeFormat());
                    view.getHboxAlarm().getChildren().remove(view.getAlarmText());

                }
                else{
                    if(!view.getHboxAlarm().getChildren().contains(view.getTimeBefore())) {
                        view.getHboxAlarm().getChildren().add(view.getTimeBefore());
                        view.getHboxAlarm().getChildren().add(view.getTimeFormat());
                        view.getHboxAlarm().getChildren().add(view.getAlarmText());
                    }

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

        view.handleRepetitionItem1(new EventHandler<>(){
            @Override
            public void handle(ActionEvent actionEvent){
                Label label = view.getRepeatsEveryDayLabel();
                view.getRepetition().setText(label.getText());
            }


        });

        view.handleRepetitionItem2(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HBox hbox = view.getHboxRepetition();
                Label label1 = (Label) hbox.getChildren().get(0);
                TextField textField = (TextField) hbox.getChildren().get(1);
                Label label2 = (Label) hbox.getChildren().get(2);
                view.getRepetition().setText(label1.getText() +" "+ textField.getText() +" "+label2.getText());
            }
        });


        view.handleRepetitionItem3(new EventHandler<>(){
            @Override
            public void handle(ActionEvent actionEvent){
                view.getRepetition().setText("No se repite");
            }


        });
        view.handleTimeFormatItem(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                view.getTimeFormat().setText(item.getText());
            }
        });

    }



    private void allDayCheckBoxEvent(){
        boolean allDay = view.getAllDay();
        HBox dateChooser = view.getDateChooser();
        if(allDay){
            int index1 = dateChooser.getChildren().indexOf(view.getHour1());
            dateChooser.getChildren().remove(index1);
            int index2 = dateChooser.getChildren().indexOf(view.getHour2());
            dateChooser.getChildren().remove(index2);
        }
        else{
            view.initializeHourPickers();
            int index = dateChooser.getChildren().indexOf(view.getDatePicker1());
            dateChooser.getChildren().add(index+1, view.getHour1());
            int index2 = dateChooser.getChildren().indexOf(view.getA());
            dateChooser.getChildren().add(index2+1, view.getHour2());
        }
    }

    private void allDayCheckBoxTask(){
        boolean allDay = view.getAllDay();
        HBox dateChooser = view.getDateChooser();
        if(allDay){
            dateChooser.getChildren().remove(view.getHour1());
        }
        else{
            view.initializeHourPickers();
            dateChooser.getChildren().add(view.getHour1());
        }
    }

    private Boolean correctDate(LocalDateTime date1, LocalDateTime date2){
        return (date1.isBefore(date2) || date1.isEqual(date2));
    }

    private Reminder createEvent(){
        String title = view.getEventName().getText();
        String description = view.getDescription();
        LocalDate startdate = view.getDatePicker1().getValue();
        LocalDate enddate = view.getDatePicker2().getValue();
        Boolean completeDay = view.getAllDay();
        String repetition = view.getRepetition().getText();

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if(!completeDay){
            startDate = startdate.atTime(stringToLocalTime(view.getHour1().getText()));
            endDate =  enddate.atTime(stringToLocalTime(view.getHour2().getText()));
        }
        else {
            startDate = startdate.atStartOfDay();
            endDate =  enddate.atStartOfDay();
        }

        if(!correctDate(startDate, endDate)){
            return null;
        }

        Reminder event = null;
        if(repetition.equals("No se repite")) {
            event = new Event(title, description, startDate, endDate, completeDay);

        }
        else{
            event = createEventWithRepetition(repetition, title, description, startDate, endDate, completeDay);

        }

        return event;
    }

    private Alarm createAlarm(String alarm ){
        Alarm newAlarm = null;
        Effect effect = null;
        int timeBefore = Integer.parseInt(view.getTimeBefore().getText());
        String format = view.getTimeFormat().getText();
        LocalDateTime goOffTime = null;
        LocalTime time = stringToLocalTime(view.getHour1().getText());
        LocalDateTime eventDate = view.getDatePicker1().getValue().atTime(time);
        if(format.equals("Minutos")){
            goOffTime = view.getDatePicker1().getValue().atTime(time.minusMinutes(timeBefore));
        }
        if(format.equals("Horas")){
            goOffTime = view.getDatePicker1().getValue().atTime(time.minusHours(timeBefore));
        }
        if(format.equals("Días")){
            goOffTime = view.getDatePicker1().getValue().atTime(time).minusDays(timeBefore);
        }
        if(format.equals("Semanas")){
            goOffTime = view.getDatePicker1().getValue().atTime(time).minusWeeks(timeBefore);
        }
        if(alarm.equals("Notificación"))
            effect = new Notification();
        else
            effect = new Sound();

        return newAlarm = new Alarm(goOffTime, effect, view.getDescription(), eventDate);

    }

    private Task createTask(){
        String title = view.getEventName().getText();
        String description = view.getDescription();
        Boolean completeDay = view.getAllDay();
        String alarm = view.getAlarm().getText();
        LocalDate expirationdate = view.getDatePicker1().getValue();
        LocalDateTime expirationDate = null;
        if(!completeDay){
            expirationDate = expirationdate.atTime(stringToLocalTime(view.getHour1().getText()));
        }
        else {
            expirationDate = expirationdate.atStartOfDay();
        }
        Task task = new Task(title,description,expirationDate,completeDay,false);

        return task;
    }


    private Event createEventWithRepetition(String repetition, String title, String description, LocalDateTime startDate, LocalDateTime endDate, boolean allDay){
        DailyStrategy frequencyStrategy = null;
        if(repetition.equals("Todos los días")){
            frequencyStrategy = new DailyStrategy(1);
        }
        else{
            TextField textField = (TextField) view.getHboxRepetition().getChildren().get(1);
            int interval =Integer.parseInt(textField.getText());
            frequencyStrategy = new DailyStrategy(interval);
        }
        InfiniteEvent event = new InfiniteEvent(title, description, allDay, startDate, endDate);
        event.addFrequency(frequencyStrategy);
        return  event;
    }


    private LocalTime stringToLocalTime(String time){
        String parsed = null;

        var substring = time.substring(0,2);
        if(time.charAt(5) == 'p'){
            substring = time.substring(0,2);
            Integer a = Integer.parseInt(substring);
            if(!substring.equals("12"))
                 a +=12;

            parsed = a.toString() + time.substring(2,5);
        }
        else{
            if(substring.equals("12")){
                parsed = "00" + time.substring(2,5);
            }
            else
                parsed = time.substring(0,5);
        }

        return LocalTime.parse(parsed);

    }

}
