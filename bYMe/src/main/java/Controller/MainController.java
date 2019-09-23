package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

  //FX grejer, borde kanske flyttas

    @FXML AnchorPane root;

    LoginController loginController;

  public MainController(){

  }

  @Override
  public void initialize(URL url, ResourceBundle rb){
        loginController = new LoginController();
        root.getChildren().add(loginController);
  }





  @FXML
  void toggleLogInPanel() {
    loginController.toggleLogInPanel();
  }








}


