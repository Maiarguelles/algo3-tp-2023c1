import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

public class Controlador {
    AddReminderView view;
    Calendar calendar;

    public Controlador(AddReminderView view, Calendar calendar){
        this.view = view;
        this.calendar = calendar;
    }

    public void initialize(){
        view.notifyButtonPress(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                view.a();
            }
        });
    }

}
