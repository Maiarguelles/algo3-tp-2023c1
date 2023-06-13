import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Abrimos las views
        FXMLLoader eventDetailLoader =  new FXMLLoader(getClass().getResource("/Fxml/EventDetail.fxml"));
        FXMLLoader replaceThingsLoader = new FXMLLoader(getClass().getResource("/Fxml/ReplaceThings.fxml"));
        FXMLLoader addReminderLoader = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        System.out.println(replaceThingsLoader);


        //Cargamos las views
        Parent eventDetailRoot = (Parent) eventDetailLoader.load();
        Parent addReminderRoot = (Parent) addReminderLoader.load();
        Parent mainRoot = (Parent) mainLoader.load();



        //Parent replaceThingsRoot = (Parent) replaceThingsLoader.load();
        System.out.println("chau");


        Calendar calendar = new Calendar(); //Modelo

        //Creamos el controlador principal
        Controlador controlador = new Controlador(mainLoader, mainRoot, addReminderLoader, addReminderRoot, calendar, replaceThingsLoader);
        MainView mainView = mainLoader.getController();
        mainView.setView(stage, mainRoot);
        controlador.initialize();

        /*Scene scene = new Scene(eventDetailRoot);
        stage.setScene(scene);
        stage.show();
        replaceThingsLoader.load();
        AnchorPane rootPane = (AnchorPane) scene.getRoot();
        VBox vbox = (VBox) eventDetailLoader.getNamespace().get("mainVbox");
        HBox hboxviejo = (HBox) eventDetailLoader.getNamespace().get("hboxTitleEvent");
        int indexviejo = vbox.getChildren().indexOf(hboxviejo);

        Pane hboxnuevo = (Pane) replaceThingsLoader.getNamespace().get("paneTitleTask");
        System.out.println( hboxnuevo);
        vbox.getChildren().remove(indexviejo);
        vbox.getChildren().add(indexviejo,hboxnuevo);*/


    }
    public static void main(String[] args){
        launch();
    }
}