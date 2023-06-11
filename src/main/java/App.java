import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));

        Controlador controlador = new Controlador()
        Calendar calendar = new Calendar(); //Modelo

        //Scene scene = new Scene(fxmlloader.load());
        Parent root = (Parent) fxmlloader.load();
        AddReminderView a = fxmlloader.getController(); //Vista de la creación de eventos
        a.setView(stage, root);



    }
    public static void main(String[] args){
        launch();
    }
}