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

        FXMLLoader eventDetailLoader =  new FXMLLoader(getClass().getResource("/Fxml/EventDetail.fxml"));
        FXMLLoader replaceThingsLoader = new FXMLLoader(getClass().getResource("/Fxml/ReplaceThings.fxml"));

        Parent sceneRoot = (Parent) eventDetailLoader.load(); //cargamos el archivo AddReminder.fxml

        Calendar calendar = new Calendar();

        Scene scene = new Scene(sceneRoot);
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
        vbox.getChildren().add(indexviejo,hboxnuevo);


    }
    public static void main(String[] args){
        launch();
    }
}