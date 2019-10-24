package ViewController;


import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessageController {

    private static void handleTextFieldEmptyError(TextField textField1, TextField textField2, TextField textField3, Label errorLabel) {

        List<TextField> textfields = new ArrayList<>();
        textfields.add(textField1);
        textfields.add(textField2);
        textfields.add(textField3);
        for (TextField textfield : textfields) {
            if (textfield.getText().isEmpty()) {
                textfield.setStyle("-fx-border-color: #e74c3c;");
            } else textfield.setStyle("-fx-border-color: inherit");
        }

        if (!allTextFieldsAreFilled(textField1, textField2, textField3)) {
            errorLabel.setText("Fyll alla fält!");
        }

    }


    private static void handleUserAlreadyExistError(boolean userAlreadyExist, TextField username, Label errorLabel) {
        if (userAlreadyExist) {
            username.setStyle("-fx-border-color: #e74c3c;");
            System.out.println("User already exist: " + username.getText());
            errorLabel.setText("Användare: " + username.getText() + " finns redan!");
        }
    }


    private static void handlePasswordDontMatchError(TextField password1, TextField password2, Label errorLabel) {

        if (!passwordsAreEqual(password1, password2)) {
            password2.setStyle("-fx-border-color: #e74c3c;");
            errorLabel.setText("Lösenorden matchar ej!");
        }
    }


    private static void resetTextFields(TextField textField1, TextField textField2, TextField textField3, ComboBox comboBox, Label errorLabel) {
        errorLabel.setText("");
        textField1.setStyle("-fx-border-color: inherit;");
        textField2.setStyle("-fx-border-color: inherit;");
        textField3.setStyle("-fx-border-color: inherit;");
        comboBox.setStyle("-fx-border-color: inherit;");
    }

    private static void resetTextFields(TextField textField1, TextField textField2, TextField textField3, Label errorLabel) {
        errorLabel.setText("");
        textField1.setStyle("-fx-border-color: inherit;");
        textField2.setStyle("-fx-border-color: inherit;");
        textField3.setStyle("-fx-border-color: inherit;");
    }

    private static void resetTextFields(TextField textField1,TextField textField2, TextField textField3,TextField textField4, TextField textField5, TextField textField6, Label errorLabel) {
        errorLabel.setText("");
        textField1.setStyle("-fx-border-color: inherit;");
        textField2.setStyle("-fx-border-color: inherit;");
        textField3.setStyle("-fx-border-color: inherit;");
        textField4.setStyle("-fx-border-color: inherit;");
        textField5.setStyle("-fx-border-color: inherit;");
        textField6.setStyle("-fx-border-color: inherit;");
    }

    private static void resetDatepickerandTextFields(TextField hour, TextField minute, TextField description, DatePicker datePicker, Label errorLabel) {
        errorLabel.setText("");
        hour.setStyle("-fx-border-color: inherit;");
        minute.setStyle("-fx-border-color: inherit;");
        description.setStyle("-fx-border-color: inherit;");
        datePicker.setStyle("-fx-border-color: inherit;");
    }

    static void resetTextFields(TextField textField1, TextField textField2, Label errorLabel) {
        errorLabel.setText("");
        textField1.setStyle("");
        textField2.setStyle("");

    }

    static void handleRegisterErrors(TextField username, TextField password, TextField password2, Label errorLabel, boolean userAlreadyExist) {
        resetTextFields(username, password, password2, errorLabel);

        if (!allTextFieldsAreFilled(username, password, password2)) {
            handleTextFieldEmptyError(username, password, password2, errorLabel);
        } else if (userAlreadyExist) {
            handleUserAlreadyExistError(userAlreadyExist, username, errorLabel);
        } else if (!passwordsAreEqual(password, password2)) {
            handlePasswordDontMatchError(password, password2, errorLabel);
        }

    }


    private static boolean passwordsAreEqual(TextField password1, TextField password2) {
        return password1.getText().equals(password2.getText());
    }

    private static boolean allFieldsAreFilled(TextField t1, TextField t2, TextField t3, ComboBox c1) {
        return !t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty() && !(c1.getSelectionModel().getSelectedItem() == null);
    }

    private static boolean allTextFieldsAreFilled(TextField t1, TextField t2, TextField t3) {
        return !t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty();
    }

    private static boolean allTextFieldsAreFilled(TextField t1, TextField t2) {
        return !t1.getText().isEmpty() && !t2.getText().isEmpty();
    }


    private static boolean allTextFieldsAreFilled(TextField t1, TextField t2, TextField t3, TextField t4, TextField t5, TextField t6) {
        return !t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty() && !t4.getText().isEmpty() && !t5.getText().isEmpty() && !t6.getText().isEmpty();
    }


    static void handleLoginErrors(TextField username, TextField password, Label errorLabel, boolean usernameAndPasswordMatch) {
        resetTextFields(username, password, errorLabel);

        if (!allTextFieldsAreFilled(username, password)) {
            handleTextFieldEmptyError(username, password, errorLabel);
        } else if (!usernameAndPasswordMatch) {
            handleWrongUsernameOrPasswordError(usernameAndPasswordMatch, username, password, errorLabel);
        }
    }

    private static void handleWrongUsernameOrPasswordError(boolean usernameAndPasswordMatch, TextField username, TextField password, Label errorLabel) {
        if (!usernameAndPasswordMatch) {
            username.setStyle("-fx-border-color: #e74c3c;");
            password.setStyle("-fx-border-color: #e74c3c;");
            errorLabel.setText("Fel användarnamn eller lösenord!");
        }
    }

    static void handleAdCreationErrors(TextField title, TextField price, ComboBox location, TextField description, Label errorLabel) {
        resetTextFields(title, price, description, location, errorLabel);
        if (!allFieldsAreFilled(title, price, description, location)) {
            handleTextFieldsEmptyErrorAdCreation(title, price, description, location, errorLabel);
        }
    }

    static void handleRequestErrors(TextField hour, TextField minute,TextField description,DatePicker datePicker,Label errorLabel){
        resetDatepickerandTextFields(hour,minute,description,datePicker,errorLabel);
        if (!allTextFieldsAreFilledandDatepicker(hour,minute,description,datePicker)){
            handleTextFieldsEmptyErrorRequest(hour,minute,description,datePicker, errorLabel);
        }
    }

    private static boolean allTextFieldsAreFilledandDatepicker(TextField hour, TextField minute, TextField description, DatePicker datePicker) {
   return !hour.getText().isEmpty() && !minute.getText().isEmpty() && !description.getText().isEmpty() && !(datePicker.getValue() == null);
    }


    static void handleTextFieldEmpty(TextField textField){
        if (textField.getText().isEmpty()) {
            textField.setStyle("-fx-border-color: #e74c3c;");
        } else textField.setStyle("-fx-border-color: inherit");
    }

    static void handleComboBoxEmpty(ComboBox comboBox){
        if (comboBox.getSelectionModel().getSelectedItem() == null) {
            comboBox.setStyle("-fx-border-color: #e74c3c;");
        } else comboBox.setStyle("-fx-border-color: inherit");
    }

    static void handleDatepickerEmpty(DatePicker datePicker){
        if (datePicker.getValue() == null) {
            datePicker.setStyle("-fx-border-color: #e74c3c;");
        } else datePicker.setStyle("-fx-border-color: inherit");
    }

    private static void handleTextFieldEmptyError(TextField textField1, TextField textField2, Label errorLabel) {

        handleTextFieldEmpty(textField1);
        handleTextFieldEmpty(textField2);

        if (!allTextFieldsAreFilled(textField1, textField2)) {
            errorLabel.setText("Fyll alla fält!");
        }

    }

    private static void handleTextFieldsEmptyErrorAdCreation(TextField textField1, TextField textField2, TextField textField3, ComboBox comboBox, Label errorLabel) {

        handleTextFieldEmpty(textField1);
        handleTextFieldEmpty(textField2);
        handleTextFieldEmpty(textField3);
        handleComboBoxEmpty(comboBox);



        if (!allFieldsAreFilled(textField1, textField2, textField3, comboBox)) {
            errorLabel.setText("Fyll alla fält!");
        }

    }


    private static void handleTextFieldsEmptyErrorRequest(TextField textField1, TextField textField2,TextField textField3,DatePicker datePicker, Label errorLabel) {


        handleTextFieldEmpty(textField1);
        handleTextFieldEmpty(textField2);
        handleTextFieldEmpty(textField3);
        handleDatepickerEmpty(datePicker);
        if (!allTextFieldsAreFilled(textField1,textField2 )) {
            errorLabel.setText("Fyll alla fält!");
        }
    }
}