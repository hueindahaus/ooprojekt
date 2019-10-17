package Controller;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ErrorController {

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

        if(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty()) {
            errorLabel.setText("Fyll alla fält!");
        } else {
            errorLabel.setText("");
        }

    }


    private static void handleUserAlreadyExistError(boolean userAlreadyExist,TextField username, Label errorLabel ){
        if (userAlreadyExist){
            username.setStyle("-fx-border-color: #e74c3c;");
            System.out.println("User already exist: " + username.getText());
            errorLabel.setText("Användare: " + username.getText() + " finns redan!");
        } else {
            username.setStyle("-fx-border-color: inherit;");
            errorLabel.setText("");
        }
    }


    private static void handlePasswordDontMatchError(TextField password1, TextField password2, Label errorLabel){

        if(!password1.getText().equals(password2.getText())){
            password2.setStyle("-fx-border-color: #e74c3c;");
            errorLabel.setText("Lösenorden matchar ej!");
        } else {
            password2.setStyle("-fx-border-color: inherit;");
            errorLabel.setText("");
        }
    }

    private static void resetTextFields(TextField textField1, TextField textField2, TextField textField3, Label errorLabel){

    }

    static void handleRegisterErrors(TextField username, TextField password, TextField password2, Label errorLabel, boolean userAlreadyExist){
        


    }


    private static boolean passwordsAreEqual(String password1, String password2){
        return password1.equals(password2);
    }

    private boolean allTextFieldsAreFilled(TextField t1, TextField t2, TextField t3){
        return !t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty();
    }




}
