import Model.DataHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("application.fxml"));
        primaryStage.setTitle("bYMe");
        primaryStage.setScene(new Scene(root, 1440, 900));
        primaryStage.show();

        //DataHandler.getInstance().registerAccount("alex98huang","password"); //test för att se om detta skrivs till logins.txt i mappen .data

    }


    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> DataHandler.getInstance().shutDown(), "Shutdown-thread"));
        launch(args);
    }
}
