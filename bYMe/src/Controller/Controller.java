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

public class Controller implements Initializable {

  //FX grejer, borde kanske flyttas

  public Controller(){

  }

  @Override
  public void initialize(URL url, ResourceBundle rb){
    hideLogInPanel = new Timeline(                                                                                      //animation för att gömma login-panelen
            new KeyFrame(Duration.seconds(0.2), new KeyValue(logInPanel.layoutXProperty(), 1440))

    );

    showLogInPanel = new Timeline(                                                                                      //animation för att visa login-panelen
            new KeyFrame(Duration.seconds(0.2), new KeyValue(logInPanel.layoutXProperty(), 1220))

    );

    hideGreyZone = new Timeline(                                                                                        //animation för att gömma gråzonen
            new KeyFrame(Duration.seconds(0.2), new KeyValue(greyZone.opacityProperty(), 0))
    );

    showGreyZone = new Timeline(                                                                                        //animation för att visa gråzonen
          new KeyFrame(Duration.seconds(0.6), new KeyValue(greyZone.opacityProperty(), 1))
    );
  }

  @FXML
  private TextField logInUsername;

  @FXML
  private TextField logInPassword;

  @FXML
  private AnchorPane logInPanel;

  @FXML
  private AnchorPane greyZone;

  private AccountHandler accountHandler = AccountHandler.getInstance();


  private Timeline showLogInPanel;
  private Timeline hideLogInPanel;
  private Timeline showGreyZone;
  private Timeline hideGreyZone;
  private boolean logInPanelIsToggled = false;

  @FXML
  void toggleLogInPanel() {
    if (logInPanelIsToggled) {
      hideLogInPanel.play();
      hideGreyZone.play();
      greyZone.setDisable(true);
      logInPanelIsToggled = false;
    } else {
      showLogInPanel.play();
      greyZone.setDisable(false);
      showGreyZone.play();
      logInPanelIsToggled = true;
    }
  }








}


