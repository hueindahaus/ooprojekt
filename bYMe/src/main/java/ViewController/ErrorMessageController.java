/**
 * Author: Adam
 * Used by: AdCreatorController, DetailViewController & LoginController
 */
package ViewController;


import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

final class ErrorMessageController {

    private ErrorMessageController() {
    }

    private static void handleTextFieldEmptyError(TextField textField1, TextField textField2, TextField textField3, Label errorLabel) {

        List<TextField> textfields = new ArrayList<>();
        textfields.add(textField1);
        textfields.add(textField2);
        textfields.add(textField3);
        for (TextField textfield : textfields) {
            if (textfield.getText().isEmpty()) {
                textfield.setStyle("-fx-border-color: #e74c3c;");
            } else {
                textfield.setStyle("-fx-border-color: inherit");
            }
        }

        if (allTextFieldsAreNotFilled(textField1, textField2, textField3)) {
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

        if (passwordsAreNotEqual(password1, password2)) {
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

    private static void resetTextFields(TextField textField1, TextField textField2, TextField textField3, TextField textField4, TextField textField5, TextField textField6, Label errorLabel) {
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

    /**
     * This methods handles the Errors at registration.
     * If a textfield is left empty the user will get an
     * errormessage telling them to fill in all fields.
     * If the username is already taken the user will get an
     * errormessage telling them that the username is taken.
     * If the two passwords do not match the user will get an
     * errormessage telling them that the passwords do not match.
     *
     * @param username         The textfield username is the textfield where the user types the username they desire for their account.
     * @param password         The textfield password is the textfield where the user types the password they desire for their account.
     * @param password2        The textfield password2 is the "confirm password" textfield.
     * @param errorLabel       The label errorLabel is the label that the users see at registration when something goes wrong.
     * @param userAlreadyExist Controls if an username is alrady taken.
     */
    static void handleRegisterErrors(TextField username, TextField password, TextField password2, Label errorLabel, boolean userAlreadyExist) {
        resetTextFields(username, password, password2, errorLabel);

        if (allTextFieldsAreNotFilled(username, password, password2)) {
            handleTextFieldEmptyError(username, password, password2, errorLabel);
        } else if (userAlreadyExist) {
            handleUserAlreadyExistError(true, username, errorLabel);
        } else if (passwordsAreNotEqual(password, password2)) {
            handlePasswordDontMatchError(password, password2, errorLabel);
        }

    }


    private static boolean passwordsAreNotEqual(TextField password1, TextField password2) {
        return !password1.getText().equals(password2.getText());
    }

    private static boolean allFieldsAreNotFilled(TextField t1, TextField t2, TextField t3, ComboBox c1) {
        return t1.getText().isEmpty() || t2.getText().isEmpty() || t3.getText().isEmpty() || c1.getSelectionModel().getSelectedItem() == null;
    }

    private static boolean allTextFieldsAreNotFilled(TextField t1, TextField t2, TextField t3) {
        return t1.getText().isEmpty() || t2.getText().isEmpty() || t3.getText().isEmpty();
    }

    private static boolean allTextFieldsAreNotFilled(TextField t1, TextField t2) {
        return t1.getText().isEmpty() || t2.getText().isEmpty();
    }

    /**
     * This methods handles the Errors at login.
     * If a textfield is left empty the user will get an
     * errormessage telling them to fill in all fields.
     * If the username and password does not match
     * the user will get an errormessage telling them that
     * the password or username is wrong.
     *
     * @param username                 The username that the user previously registered. Used for logging in.
     * @param password                 The password that the user previously chosen for his account.
     * @param errorLabel               The label that the users see at login when something goes wrong.
     * @param usernameAndPasswordMatch Controls if the password and username is correct.
     */
    static void handleLoginErrors(TextField username, TextField password, Label errorLabel, boolean usernameAndPasswordMatch) {
        resetTextFields(username, password, errorLabel);

        if (allTextFieldsAreNotFilled(username, password)) {
            handleTextFieldEmptyError(username, password, errorLabel);
        } else if (!usernameAndPasswordMatch) {
            handleWrongUsernameOrPasswordError(false, username, password, errorLabel);
        }
    }


    private static void handleWrongUsernameOrPasswordError(boolean usernameAndPasswordMatch, TextField username, TextField password, Label errorLabel) {
        if (!usernameAndPasswordMatch) {
            username.setStyle("-fx-border-color: #e74c3c;");
            password.setStyle("-fx-border-color: #e74c3c;");
            errorLabel.setText("Fel användarnamn eller lösenord!");
        }
    }

    /**
     * This methods handles the Errors at adcreation.
     * If a textfield or combobox is left empty the user will get an
     * errormessage telling them to fill in all fields.
     *
     * @param title
     * @param price
     * @param location
     * @param description
     * @param errorLabel
     */
    static void handleAdCreationErrors(TextField title, TextField price, ComboBox location, TextField description, Label errorLabel) {
        resetTextFields(title, price, description, location, errorLabel);
        if (allFieldsAreNotFilled(title, price, description, location)) {
            handleTextFieldsEmptyErrorAdCreation(title, price, description, location, errorLabel);
        }
    }

    /**
     * This methods handles the Errors at the page where the user sends a request.
     * If a textfield or the datepicker is left empty the user will get an
     * errormessage telling them to fill in all fields.
     *
     * @param hour
     * @param minute
     * @param description
     * @param datePicker
     * @param errorLabel
     */
    static void handleRequestErrors(TextField hour, TextField minute, TextField description, DatePicker datePicker, Label errorLabel) {
        resetDatepickerandTextFields(hour, minute, description, datePicker, errorLabel);
        if (allTextFieldsAreFilledandDatepicker(hour, minute, description, datePicker)) {
            handleTextFieldsEmptyErrorRequest(hour, minute, description, datePicker, errorLabel);
        }
    }

    private static boolean allTextFieldsAreFilledandDatepicker(TextField hour, TextField minute, TextField description, DatePicker datePicker) {
        return hour.getText().isEmpty() && minute.getText().isEmpty() && description.getText().isEmpty() && (datePicker.getValue() == null);
    }

    /**
     * When the textfield is empty it will change color to red
     * otherwise the border does not change color.
     *
     * @param textField
     */
    private static void handleTextFieldEmpty(TextField textField) {
        if (textField.getText().isEmpty()) {
            textField.setStyle("-fx-border-color: #e74c3c;");
        } else {
            textField.setStyle("-fx-border-color: inherit");
        }
    }

    /**
     * When the combobox is empty it will change color to red
     * otherwise the border does not change color.
     *
     * @param comboBox
     */
    private static void handleComboBoxEmpty(ComboBox comboBox) {
        if (comboBox.getSelectionModel().getSelectedItem() == null) {
            comboBox.setStyle("-fx-border-color: #e74c3c;");
        } else {
            comboBox.setStyle("-fx-border-color: inherit");
        }
    }

    /**
     * When the datepicker is empty it will change color to red
     * otherwise the border does not change color.
     *
     * @param datePicker
     */
    private static void handleDatepickerEmpty(DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            datePicker.setStyle("-fx-border-color: #e74c3c;");
        } else {
            datePicker.setStyle("-fx-border-color: inherit");
        }
    }

    private static void handleTextFieldEmptyError(TextField textField1, TextField textField2, Label errorLabel) {

        handleTextFieldEmpty(textField1);
        handleTextFieldEmpty(textField2);

        if (allTextFieldsAreNotFilled(textField1, textField2)) {
            errorLabel.setText("Fyll alla fält!");
        }

    }

    private static void handleTextFieldsEmptyErrorAdCreation(TextField textField1, TextField textField2, TextField textField3, ComboBox comboBox, Label errorLabel) {

        handleTextFieldEmpty(textField1);
        handleTextFieldEmpty(textField2);
        handleTextFieldEmpty(textField3);
        handleComboBoxEmpty(comboBox);


        if (allFieldsAreNotFilled(textField1, textField2, textField3, comboBox)) {
            errorLabel.setText("Fyll alla fält!");
        }

    }


    private static void handleTextFieldsEmptyErrorRequest(TextField textField1, TextField textField2, TextField textField3, DatePicker datePicker, Label errorLabel) {


        handleTextFieldEmpty(textField1);
        handleTextFieldEmpty(textField2);
        handleTextFieldEmpty(textField3);
        handleDatepickerEmpty(datePicker);
        if (allTextFieldsAreNotFilled(textField1, textField2)) {
            errorLabel.setText("Fyll alla fält!");
        }
    }
}