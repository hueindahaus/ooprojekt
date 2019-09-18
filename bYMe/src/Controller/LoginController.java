package Controller;

import Model.AccountHandler;
import javafx.fxml.FXML;

import java.awt.*;

public class LoginController {

    private AccountHandler accountHandler = AccountHandler.getInstance();

    @FXML
    TextField signUpUsername;

    @FXML
    TextField signUpPasssword;

    @FXML
    TextField signUpPassword2;

    @FXML void registerUser(){
        if(signUpPasssword.getText().equals(signUpPassword2.getText())){
            accountHandler.registerAccount(signUpUsername.getText(), signUpPasssword.getText());
        }
    }
}
