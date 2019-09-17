package Controller;

import Model.AccountHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {

  //FX grejer, borde kanske flyttas

  public Controller(){

  }

  @FXML
  private TextField logInUsername;

  @FXML
  private TextField logInPassword;

  @FXML
  private AnchorPane logInPanel;

  private AccountHandler accountHandler = AccountHandler.getInstance();

  @FXML
  void toggleLogInPanel() {
    if (logInPanel.isVisible()) {
      logInPanel.setVisible(false);
    } else {
      logInPanel.setVisible(true);
    }
  }






}


