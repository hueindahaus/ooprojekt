package Controller;

import Model.AccountHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;

public class LoginController extends AnchorPane{


    @FXML AnchorPane registerBox;
    @FXML AnchorPane registerBoxFrame;




    public LoginController(){


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }

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

        registerBoxFrame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {                         //gör så att inget ska hända när man trycker på registreringsrutan
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });

    }



    private AccountHandler accountHandler = AccountHandler.getInstance();

    @FXML
    TextField signUpUsername;

    @FXML
    TextField signUpPassword;

    @FXML
    TextField signUpPassword2;

    @FXML
    AnchorPane greyZone;

    @FXML void registerUser(){
        String username = signUpUsername.getText();
        String password = signUpPassword.getText();
        String verifyPassword = signUpPassword2.getText();

         if (!verifyPassword.equals(password)){
            signUpPassword.setStyle("-fx-border-color: red;");
            signUpPassword2.setStyle("-fx-border-color: red;");
        } else if(accountHandler.isAlreadyRegistered(username)){
            signUpUsername.setStyle("-fx-border-color: red");
            System.out.println("User already exist: " + username);
        } else if(username.isEmpty() && password.isEmpty() && verifyPassword.isEmpty()){
            System.out.println("Fill all textfields");
        } else {
             accountHandler.registerAccount(username, password);
             toggleRegisterBox();
         }
    }





    @FXML
    private TextField logInUsername;

    @FXML
    private TextField logInPassword;

    @FXML
    private AnchorPane logInPanel;

    private Timeline showLogInPanel;
    private Timeline hideLogInPanel;
    private Timeline showGreyZone;
    private Timeline hideGreyZone;
    private boolean logInPanelIsToggled = false;

    @FXML void toggleLogInPanel(){
        if (logInPanelIsToggled) {
            hideLogInPanel.play();
            hideGreyZone.play();
            greyZone.setDisable(true);
            logInPanelIsToggled = false;
        } else {
            showLogInPanel.play();
            showGreyZone.play();
            greyZone.setDisable(false);
            logInPanelIsToggled = true;
        }
    }


    @FXML public void toggleRegisterBox(){
        if(registerBox.isVisible()){
            registerBox.setVisible(false);
        } else {
            registerBox.setVisible(true);
        }
    }

}
