import javafx.fxml.FXMLLoader;

public class Controlador {
    FXMLLoader vista1;
    Calendar calendar;

    public Controlador(){
        vista1 = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));
    }

}
