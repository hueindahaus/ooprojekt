package Controller;

import Model.Byme;
import Services.AccountHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private Byme bYMe = Byme.getInstance(AccountHandler.getInstance());

    @FXML
    TextField signUpUsername;

    @FXML
    PasswordField signUpPassword;

    @FXML
    PasswordField signUpPassword2;

    @FXML
    Label errorLabel;

    @FXML
    AnchorPane greyZone;

    @FXML void registerUser(){
        String username = signUpUsername.getText();
        String password = signUpPassword.getText();
        String verifyPassword = signUpPassword2.getText();

        highlightUserAlreadyExistError();
        highlightUnmatchedPasswordError();
        highlightTextFieldEmpty();

        if (!isAllTextFieldsFilled()){       //om alla textrutor är ej fyllda
            highlightTextFieldEmpty();
        } else if (bYMe.isAlreadyRegistered(signUpUsername.getText())){   //om användare redan finns
            highlightUserAlreadyExistError();
        } else if(!verifyPassword.equals(password)){
            highlightUnmatchedPasswordError();
        } else {
            bYMe.registerAccount(username, password);
            toggleRegisterBox();
        }


    }

    private boolean isAllTextFieldsFilled(){
        return !signUpUsername.getText().isEmpty() && !signUpPassword.getText().isEmpty() && !signUpPassword2.getText().isEmpty();
    }

    private void highlightTextFieldEmpty(){

        List<TextField> textfields = new ArrayList<>();
        textfields.add(signUpUsername);
        textfields.add(signUpPassword);
        textfields.add(signUpPassword2);

        for(TextField textfield : textfields){
            if(textfield.getText().isEmpty()){
                textfield.setStyle("-fx-border-color: red;");
            } else {
               textfield.setStyle("-fx-border-color: inherit");
            }
        }

        if(!isAllTextFieldsFilled()){
            errorLabel.setText("Fyll alla fält!");
        } else {
            errorLabel.setText("");
        }

    }

    private void highlightUserAlreadyExistError(){
        if (bYMe.isAlreadyRegistered(signUpUsername.getText())){
            signUpUsername.setStyle("-fx-border-color: red;");
            System.out.println("User already exist: " + signUpUsername.getText());
            errorLabel.setText("Användare: " + signUpUsername.getText() + "finns redan!");
        } else {
            signUpUsername.setStyle("-fx-border-color: inherit;");
            errorLabel.setText("");
        }
    }

    private void highlightUnmatchedPasswordError(){
        if(!signUpPassword.getText().equals(signUpPassword2.getText())){
            signUpPassword2.setStyle("-fx-border-color: red;");
            System.out.println("Password doesn't match");
            errorLabel.setText("Lösenorden matchar ej!");
        } else {
            signUpPassword2.setStyle("-fx-border-color: inherit;");
            errorLabel.setText("");
        }
    }





    @FXML
    private TextField logInUsername;

    @FXML
    private PasswordField logInPassword;

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

    @FXML void loginUser(){
        bYMe.loginUser(logInUsername.getText(), logInPassword.getText());
        toggleLogInPanel();
    }

}
