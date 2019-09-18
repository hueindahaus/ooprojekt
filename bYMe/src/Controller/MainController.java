package Controller;

import Model.AccountHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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


