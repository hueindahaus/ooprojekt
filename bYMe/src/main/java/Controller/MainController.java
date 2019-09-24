package Controller;

import Model.Byme;
import Model.IAccountHandler;
import Services.AccountHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //FX grejer, borde kanske flyttas

    @FXML
    AnchorPane root;

    private LoginController loginController;
    private MenuController menuController;

    public MainController() {

    }

    private Byme byme = Byme.getInstance(AccountHandler.getInstance());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginController = new LoginController();
        root.getChildren().add(loginController);
        menuController = new MenuController();
        root.getChildren().add(menuController);
    }


    @FXML
    void toggleLogInPanel() {
        if (byme.getCurrentUser() == null) {
            loginController.toggleLogInPanel();
        }
        else{
            menuController.toggleMenuPanel();
        }
    }
}


