package ViewController;

import Model.Byme;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LoginController extends SidePanelController{

    private ThemeSetter themeSetter;

    @FXML AnchorPane registerBox;
    @FXML AnchorPane registerBoxFrame;
    @FXML TextField signUpUsername;
    @FXML PasswordField signUpPassword;
    @FXML PasswordField signUpPassword2;
    @FXML Label errorLabelRegister;
    @FXML AnchorPane greyZone;
    @FXML private TextField logInUsername;
    @FXML private PasswordField logInPassword;
    @FXML private AnchorPane logInPanel;
    @FXML private Label errorLabelLogin;

    private SidePanelToggler panelToggler;



    LoginController(SidePanelToggler panelToggler, ThemeSetter themeSetter){
        super("FXML/login.fxml");

        this.themeSetter = themeSetter;

        hidePanel = new Timeline(                                                                                      //animation för att gömma login-panelen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(logInPanel.layoutXProperty(), 1440))

        );

        showPanel = new Timeline(                                                                                      //animation för att visa login-panelen
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


        this.panelToggler = panelToggler;
    }

    private Byme bYMe = Byme.getInstance(null,null);


    @FXML void registerUser(){
        String username = signUpUsername.getText();
        String password = signUpPassword.getText();
        String verifyPassword = signUpPassword2.getText();

        ErrorMessageController.handleRegisterErrors(signUpUsername,signUpPassword,signUpPassword2,errorLabelRegister,bYMe.isAlreadyRegistered(signUpUsername.getText()));

        if(!bYMe.isAlreadyRegistered(signUpUsername.getText()) && isAllTextFieldsFilled() && signUpPassword.getText().equals(signUpPassword2.getText())) {
            bYMe.registerAccount(username, password);
            bYMe.loginUser(username, password);
            toggleRegisterBox();
            panelToggler.togglePanel(true);
        }


    }

    private boolean isAllTextFieldsFilled(){
        return !signUpUsername.getText().isEmpty() && !signUpPassword.getText().isEmpty() && !signUpPassword2.getText().isEmpty();
    }


    void setGreyZoneDisable(boolean value){
        greyZone.setDisable(value);
    }


    @FXML public void toggleRegisterBox(){
        if(registerBox.isVisible()){
            registerBox.setVisible(false);
        } else {
            registerBox.setVisible(true);
        }
    }

    @FXML void loginUser(){

        ErrorMessageController.handleLoginErrors(logInUsername,logInPassword,errorLabelLogin, bYMe.userExist(logInUsername.getText(),logInPassword.getText()));

        bYMe.loginUser(logInUsername.getText(), logInPassword.getText());
        if(bYMe.isLoggedIn()) {
            ErrorMessageController.resetTextFields(logInUsername,logInPassword,errorLabelLogin);
            panelToggler.togglePanel(true);
        }
    }

    @FXML void changeTheme(){
        themeSetter.changeTheme();
    }

}
