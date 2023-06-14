import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class Controller {


    protected Button createDisplay(Reminder reminder) throws IOException {
        FXMLLoader displayReminderloader = new FXMLLoader(getClass().getResource("/Fxml/DisplayReminder.fxml"));
        displayReminderloader.load();
        DisplayReminderView displayReminderView = displayReminderloader.getController();
        displayReminderView.getReminderName().setText(reminder.getTitle());

        if(reminder.getClass() == Event.class){
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


}
