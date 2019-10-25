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

/**
 * This class is responsible for the FXML-file:login containing all of the fxml-elements needed for showing the sidepanel when a user is not signed in.
 * A user can sign in, open a panel to create an account and change theme.
 * @author Alexander Huang, Milos Bastajic.
 *
 * Uses: SidePanelToggler, Byme.
 * Used by: MainController.
 */
public class LoginController extends SidePanelController {

    @FXML
    AnchorPane registerBox;
    @FXML
    AnchorPane registerBoxFrame;
    @FXML
    TextField signUpUsername;
    @FXML
    PasswordField signUpPassword;
    @FXML
    PasswordField signUpPassword2;
    @FXML
    Label errorLabelRegister;
    @FXML
    AnchorPane greyZone;
    private ThemeSetter themeSetter;
    @FXML
    private TextField logInUsername;
    @FXML
    private PasswordField logInPassword;
    @FXML
    private AnchorPane logInPanel;
    @FXML
    private Label errorLabelLogin;

    private SidePanelToggler panelToggler;
    private Byme bYMe = Byme.getInstance(null, null);

    LoginController(SidePanelToggler panelToggler, ThemeSetter themeSetter) {
        super("FXML/login.fxml");

        this.themeSetter = themeSetter;

        hidePanel = new Timeline(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(logInPanel.layoutXProperty(), 1440))

        );

        showPanel = new Timeline(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(logInPanel.layoutXProperty(), 1220))

        );

        hideGreyZone = new Timeline(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(greyZone.opacityProperty(), 0))
        );

        showGreyZone = new Timeline(
                new KeyFrame(Duration.seconds(0.6), new KeyValue(greyZone.opacityProperty(), 1))
        );

        registerBoxFrame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });


        this.panelToggler = panelToggler;
    }

    @FXML
    void registerUser() {
        String username = signUpUsername.getText();
        String password = signUpPassword.getText();
        String verifyPassword = signUpPassword2.getText();

        ErrorMessageController.handleRegisterErrors(signUpUsername, signUpPassword, signUpPassword2, errorLabelRegister, bYMe.isAlreadyRegistered(signUpUsername.getText()));

        if (!bYMe.isAlreadyRegistered(username) && isAllTextFieldsFilled() && password.equals(verifyPassword)) {
            bYMe.registerAccount(username, password);
            bYMe.loginUser(username, password);
            toggleRegisterBox();
            panelToggler.toggleSidePanel(true);
        }


    }

    private boolean isAllTextFieldsFilled() {
        return !signUpUsername.getText().isEmpty() && !signUpPassword.getText().isEmpty() && !signUpPassword2.getText().isEmpty();
    }


    @Override
    void setGreyZoneDisable(boolean value) {
        greyZone.setDisable(value);
    }


    @FXML
    private void toggleRegisterBox() {
        if (registerBox.isVisible()) {
            registerBox.setVisible(false);
        } else {
            registerBox.setVisible(true);
        }
    }

    @FXML
    void loginUser() {

        ErrorMessageController.handleLoginErrors(logInUsername, logInPassword, errorLabelLogin, bYMe.userExist(logInUsername.getText(), logInPassword.getText()));

        bYMe.loginUser(logInUsername.getText(), logInPassword.getText());
        if (bYMe.isLoggedIn()) {
            ErrorMessageController.resetTextFields(logInUsername, logInPassword, errorLabelLogin);
            panelToggler.toggleSidePanel(true);
            resetLoginFields();
        }
    }

    private void resetLoginFields(){
        logInUsername.clear();
        logInPassword.clear();
    }

    @FXML
    void changeTheme() {
        themeSetter.changeTheme();
    }

}
