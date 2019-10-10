package Controller;

import Model.Ad;
import Model.Byme;
import Model.IObserver;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Scanner;

public class DetailViewController extends AnchorPane{


    DetailViewToggler detailViewToggler;

    Byme byme = Byme.getInstance(null,null);


    @FXML
    Button deleteButton;
    @FXML
    Button editButton;
    @FXML
    Button saveButton;
    @FXML
    Label adTitle;
    @FXML
    Label adLocation;
    @FXML
    Label adDescription;
    @FXML
    Label adUser;
    @FXML
    Label adPrice;
    @FXML
    TextField adTitleTextField;
    @FXML
    TextField adLocationTextField;
    @FXML
    TextField adDescriptionTextField;
    @FXML
    TextField adUserTextField;
    @FXML
    TextField adPriceTextField;

    @FXML
    AnchorPane greyZone;
    @FXML
    AnchorPane confirmPane;
    @FXML
    AnchorPane greyZone2;

    Timeline showPrompt;

    Timeline closePrompt;


    Ad ad;

    public DetailViewController(DetailViewToggler detailViewToggler){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../detailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }

        showPrompt = new Timeline(
                new KeyFrame(Duration.seconds(0.05), new KeyValue(confirmPane.layoutYProperty(), 350))
        );

        closePrompt = new Timeline(
                new KeyFrame(Duration.seconds(0.05), new KeyValue(confirmPane.layoutYProperty(), -200))
        );

        this.detailViewToggler = detailViewToggler;


    }


    /**
     *
     * @param ad Sends in an ad as parameter so the detailView can show the data of an ad.
     */
    void setAd(Ad ad){
        if (ad != null) {
            this.ad = ad;
            adTitle.setText(ad.getTitle());
            adDescription.setText(ad.getDescription());
            adLocation.setText(ad.getLocation());
            adUser.setText(ad.getAccount());
            adPrice.setText(String.valueOf(ad.getPrice()));
        }
    }


    /**
     * Used for the exitButton which upon a press closes the detailView panel.
     */
    @FXML
    void closeDetailView(){
        detailViewToggler.toggleDetailView(false, ad);
    }


    /**
     * Gives the user the ability to remove a specific ad.
     */

    @FXML
    void removeAdPrompt(){
        showPrompt.play();
        greyZone2.setVisible(true);
    }

    @FXML
    void removeAdClosePrompt(){
        closePrompt.play();
        greyZone2.setVisible(false);
    }

    @FXML
    void removeAd(){
        byme.removeAd(ad.getAdId());
        removeAdClosePrompt();
        closeDetailView();
    }


    /**
     * Used for the button which upon a press enables the "edit mode".
     */
    @FXML
    void editAd(){

        showTextFields();

        editButton.setVisible(false);
        saveButton.setVisible(true);

    }


    /**
     * Saves the changes when a user has edited an ad.
     * It also notifies observers who has registered to listen to these changes via the
     * observerble "ad".
     */
    @FXML
    void saveChanges(){

        byme.editAd(ad.getAdId(), adTitleTextField.getText(),Integer.valueOf(adPriceTextField.getText()),
                adDescriptionTextField.getText(), adLocation.getText() );

        adTitle.setText(ad.getTitle());
        adLocation.setText(ad.getLocation());
        adDescription.setText(ad.getDescription());
        adPrice.setText(String.valueOf(ad.getPrice()));

        showLabels();

        editButton.setVisible(true);
        saveButton.setVisible(false);


    }


    /**
     * Method that makes sure that the delete and edit button only shows on an ad if the logged in user
     * owns that specific ad.
     */
    void showUserButtons(){
        if(isUsersAd()){
            deleteButton.setVisible(true);
            editButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
            editButton.setVisible(false);
        }
    }



    private boolean isUsersAd(){
        if (byme.getCurrentUser() != null){
            if (byme.getCurrentUser().getUsername().equals(ad.getAccount())){
                return true;
            }
        }
        return false;
    }

    /**
     * Method which enables visibility for an ads TextFields and at the same time disables visibility for the Labels.
     * It also sets the text for all TextFields so they get the current data.
     *
     */
    void showTextFields(){

        adTitle.setVisible(false);
        adLocation.setVisible(false);
        adDescription.setVisible(false);
        adPrice.setVisible(false);

        adTitleTextField.setVisible(true);
        adLocationTextField.setVisible(true);
        adDescriptionTextField.setVisible(true);
        adPriceTextField.setVisible(true);

        adTitleTextField.setText(adTitle.getText());
        adLocationTextField.setText(adLocation.getText());
        adDescriptionTextField.setText(adDescription.getText());
        adPriceTextField.setText(adPrice.getText());

    }

    /**
     * Method which enables visibility for an ads Labels and at the same time disables visibility for the TextFields.
     *
     */

    public void showLabels(){

        adTitle.setVisible(true);
        adLocation.setVisible(true);
        adDescription.setVisible(true);
        adPrice.setVisible(true);

        adTitleTextField.setVisible(false);
        adLocationTextField.setVisible(false);
        adDescriptionTextField.setVisible(false);
        adPriceTextField.setVisible(false);

    }



}
