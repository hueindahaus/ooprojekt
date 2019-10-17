package Controller;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessageController {

    private static void handleTextFieldEmptyError(TextField textField1, TextField textField2, TextField textField3, Label errorLabel){

        List<TextField> textfields = new ArrayList<>();
        textfields.add(textField1);
        textfields.add(textField2);
        textfields.add(textField3);
        for (TextField textfield : textfields) {
            if (textfield.getText().isEmpty()) {
                textfield.setStyle("-fx-border-color: #e74c3c;");
            } else textfield.setStyle("-fx-border-color: inherit");
        }

        if(!allTextFieldsAreFilled(textField1,textField2,textField3)) {
            errorLabel.setText("Fyll alla fält!");
        }

    }


    private static void handleUserAlreadyExistError(boolean userAlreadyExist,TextField username, Label errorLabel ){
        if (userAlreadyExist){
            username.setStyle("-fx-border-color: #e74c3c;");
            System.out.println("User already exist: " + username.getText());
            errorLabel.setText("Användare: " + username.getText() + " finns redan!");
        }
    }


    private static void handlePasswordDontMatchError(TextField password1, TextField password2, Label errorLabel){

        if(!passwordsAreEqual(password1,password2)){
            password2.setStyle("-fx-border-color: #e74c3c;");
            errorLabel.setText("Lösenorden matchar ej!");
        }
    }

    private static void resetTextFields(TextField textField1, TextField textField2, TextField textField3, Label errorLabel){
        errorLabel.setText("");
        textField1.setStyle("-fx-border-color: inherit;");
        textField2.setStyle("-fx-border-color: inherit;");
        textField3.setStyle("-fx-border-color: inherit;");
    }

    static void resetTextFields(TextField textField1, TextField textField2, Label errorLabel){
        errorLabel.setText("");
        textField1.setStyle("-fx-border-color: inherit;");
        textField2.setStyle("-fx-border-color: inherit;");
    }

    static void handleRegisterErrors(TextField username, TextField password, TextField password2, Label errorLabel, boolean userAlreadyExist){
        resetTextFields(username,password,password2,errorLabel);

        if(!allTextFieldsAreFilled(username,password,password2)){
           handleTextFieldEmptyError(username,password,password2,errorLabel);
        } else if(userAlreadyExist){
            handleUserAlreadyExistError(userAlreadyExist,username,errorLabel);
        } else if (!passwordsAreEqual(password,password2)){
            handlePasswordDontMatchError(password,password2,errorLabel);
        }

    }


    private static boolean passwordsAreEqual(TextField password1, TextField password2){
        return password1.getText().equals(password2.getText());
    }

    private static boolean allTextFieldsAreFilled(TextField t1, TextField t2, TextField t3){
        return !t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty();
    }

    private static boolean allTextFieldsAreFilled(TextField t1, TextField t2){
        return !t1.getText().isEmpty() && !t2.getText().isEmpty();
    }


    static void handleLoginErrors(TextField username, TextField password, Label errorLabel, boolean usernameAndPasswordMatch){
        resetTextFields(username, password, errorLabel);

        if(!allTextFieldsAreFilled(username,password)){
            handleTextFieldEmptyError(username,password,errorLabel);
        } else if(!usernameAndPasswordMatch){
            handleWrongUsernameOrPasswordError(usernameAndPasswordMatch,username,password,errorLabel);
        }
    }

    private static void handleWrongUsernameOrPasswordError(boolean usernameAndPasswordMatch, TextField username, TextField password, Label errorLabel){
        if(!usernameAndPasswordMatch){
            username.setStyle("-fx-border-color: #e74c3c;");
            password.setStyle("-fx-border-color: #e74c3c;");
            errorLabel.setText("Fel användarnamn eller lösenord!");
        }
    }


    private static void handleTextFieldEmptyError(TextField textField1, TextField textField2, Label errorLabel){

        if (textField1.getText().isEmpty()) {
            textField1.setStyle("-fx-border-color: #e74c3c;");
        } else textField1.setStyle("-fx-border-color: inherit");

        if (textField2.getText().isEmpty()) {
            textField2.setStyle("-fx-border-color: #e74c3c;");
        } else textField2.setStyle("-fx-border-color: inherit");

        if(!allTextFieldsAreFilled(textField1,textField2)) {
            errorLabel.setText("Fyll alla fält!");
        }

    }

}
