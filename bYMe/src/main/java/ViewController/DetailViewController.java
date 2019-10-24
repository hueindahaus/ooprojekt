package ViewController;

import Model.Ad;
import Model.Byme;
import Services.PictureHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Math.abs;


public class DetailViewController extends AnchorPane implements ImageViewUpdater{



    DetailViewToggler detailViewToggler;

    PictureHandler pictureHandler = PictureHandler.getInstance();

    PictureChangeController pictureChanger;

    Byme byme = Byme.getInstance(null, null);


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
    Label userRating;


    @FXML
    TextField adTitleTextField;
    @FXML
    ComboBox adLocationComboBox;
    ;
    @FXML
    TextField adDescriptionTextField;
    @FXML
    TextField adUserTextField;
    @FXML
    TextField adPriceTextField;
    @FXML
    Label errorLabel;

    @FXML
    Label tag1Label;
    @FXML
    Label tag2Label;
    @FXML
    Label tag3Label;
    @FXML
    Label tag4Label;
    @FXML
    Label tag5Label;

    @FXML
    TextField tag1TextField;
    @FXML
    TextField tag2TextField;
    @FXML
    TextField tag3TextField;
    @FXML
    TextField tag4TextField;
    @FXML
    TextField tag5TextField;


    @FXML
    AnchorPane greyZone;
    @FXML
    AnchorPane confirmPane;
    @FXML
    AnchorPane requestPane;
    @FXML
    AnchorPane greyZone2;
    @FXML
    ImageView image1;
    @FXML
    TextField messageContent;
    @FXML
    DatePicker requestDate;
    @FXML
    TextField requestHour;
    @FXML
    TextField requestMinute;
    @FXML
    Button requestButton;
    @FXML
    Label errorLabelRequest;

    Timeline showPrompt;

    Timeline closePrompt;

    Timeline showRequestPrompt;

    Timeline closeRequestPrompt;

    Ad ad;

    int index;


    public DetailViewController(DetailViewToggler detailViewToggler) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/detailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        showPrompt = new Timeline(
                new KeyFrame(Duration.seconds(0.05), new KeyValue(confirmPane.layoutYProperty(), 350))
        );

        closePrompt = new Timeline(
                new KeyFrame(Duration.seconds(0.05), new KeyValue(confirmPane.layoutYProperty(), -200))
        );

        showRequestPrompt = new Timeline(
                new KeyFrame(Duration.seconds(0.05), new KeyValue(requestPane.layoutYProperty(), 350))
        );

        closeRequestPrompt = new Timeline(
                new KeyFrame(Duration.seconds(0.05), new KeyValue(requestPane.layoutYProperty(), 900))
        );

        this.detailViewToggler = detailViewToggler;

        this.pictureChanger = new PictureChangeController(this);
        Button pictureChangeButton = new Button();
        pictureChangeButton.setText("Spara Ändringar");
        pictureChangeButton.setPrefWidth(200);
        pictureChangeButton.setPrefHeight(50);
        pictureChangeButton.setLayoutX(400);
        pictureChangeButton.setLayoutY(400);
        pictureChangeButton.getStyleClass().add("saveImageButton");
        pictureChangeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closePictureChangePanel();
            }
        });
        pictureChanger.getChildren().add(pictureChangeButton);
        this.getChildren().add(pictureChanger);






        adLocationComboBox.getItems().addAll("Västra Götaland", "Stockholm", "Skåne", "Jönköping", "Bergsjön");

        adPriceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    adPriceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if (newValue.length() > 0 && newValue.charAt(0) == '0') {
                    adPriceTextField.setText(oldValue);
                }
            }
        });
        requestDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }


    /**
     * @param ad Sends in an ad as parameter so the detailView can show the data of an ad.
     */
    void setAd(Ad ad) {
        this.ad = ad;
        if (ad != null) {
            adTitle.setText(ad.getTitle());
            adDescription.setText(ad.getDescription());
            adLocation.setText(ad.getLocation());
            String result = String.format("%.2f", byme.getAccountRating(ad.getAccount()));
            adUser.setText(ad.getAccount()+" ("+result+")");
            adPrice.setText(String.valueOf(ad.getPrice()));


            if (!(ad.getTagsList().size() == 0)) {
                ArrayList<String> tags = ad.getTagsList();
                tag1Label.setText(tags.get(0));
                tag2Label.setText(tags.get(1));
                tag3Label.setText(tags.get(2));
                tag4Label.setText(tags.get(3));
                tag5Label.setText(tags.get(4));
            }

        }
    }


    /**
     * Used for the exitButton which upon a press closes the detailView panel.
     */
    @FXML
    void closeDetailView() {
        switchToNormalViewMode();
        detailViewToggler.toggleDetailView(false, ad);
    }

    /**
     * Gives the user a prompt to make sure the user is sure they want to delete their ad.
     */
    @FXML
    void removeAdPrompt() {
        showPrompt.play();
        greyZone2.setVisible(true);
    }

    @FXML
    void removeAdClosePrompt() {
        closePrompt.play();
        greyZone2.setVisible(false);
    }
    /**
     * Gives the user the ability to remove a specific ad.
     */
    @FXML
    void removeAd() {
        pictureHandler.removePictureFolder(ad.getAdId());
        byme.removeAd(ad.getAdId());
        removeAdClosePrompt();
        closeDetailView();
    }

    @FXML
    void sendRequestPrompt() {
        if (byme.isLoggedIn()) {
            messageContent.setText("");
            showRequestPrompt.play();
            greyZone2.setVisible(true);
        }
    }

    @FXML
    void sendRequestClosePrompt() {
        closeRequestPrompt.play();
        greyZone2.setVisible(false);
    }


    @FXML
    void sendRequest() throws ParseException {

        ErrorMessageController.handleRequestErrors(requestMinute,requestHour,messageContent,requestDate,errorLabelRequest);

        String date = requestDate.getValue() + "/" + requestHour.getText() + ":" + requestMinute.getText();
        byme.sendRequest(byme.getCurrentUser().getUsername(), ad.getAccount(), ad, messageContent.getText(), date);
        sendRequestClosePrompt();
    }


    public void updateImageViews(){
        if(pictureChanger.getImages().size() > 0){
            image1.setImage(pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(pictureChanger.getImages().get(0), null)));
        } else {
            try {
                image1.setImage(SwingFXUtils.toFXImage(ImageIO.read(new File("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "ViewController/images" + File.separatorChar + "insert_photo.png")), null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    void savePictures(){
        if(ad != null) {
            pictureHandler.saveAdPictures(ad.getAdId(), pictureChanger.getImages());
        }
    }

    void loadPictures() {
        if (ad != null) {
            pictureChanger.setImages(pictureHandler.getAdPictures(ad.getAdId()));
        }
    }



    private void setEnablePictureChange(boolean enable) {
        if (enable) {
            image1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    openPictureChangePanel();
                }
            });

            image1.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    image1.setEffect(pictureChanger.pictureEffect);
                }
            });

            image1.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    image1.setEffect(null);
                }
            });

            image1.setCursor(Cursor.HAND);
        } else {
            image1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //do nothing
                }
            });


            image1.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    image1.setEffect(null);
                }
            });


            image1.setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Used for the button which upon a press enables the "edit mode".
     */
    @FXML
    void editAd() {
        showTextFields();
        setEnablePictureChange(true);
        editButton.setVisible(false);
        saveButton.setVisible(true);
    }

    /**
     * Saves the changes when a user has edited an ad.
     * It also notifies observers who has registered to listen to these changes via the
     * observerble "ad".
     */
    @FXML
    void saveChanges() {
        ErrorMessageController.handleAdCreationErrors(adTitleTextField, adPriceTextField, adLocationComboBox, adDescriptionTextField, errorLabel);

        if (allTextFieldsFilled()) {

            byme.editAd(ad.getAdId(), adTitleTextField.getText(), Integer.valueOf(adPriceTextField.getText()),
                    adDescriptionTextField.getText(), adLocation.getText(), getTagsText());
            switchToNormalViewMode();
        }

    }



    private void switchToNormalViewMode(){
        adTitle.setText(ad.getTitle());
        adLocation.setText(ad.getLocation());
        adDescription.setText(ad.getDescription());
        adPrice.setText(String.valueOf(ad.getPrice()));


        //flytta funktionalitet till egen method och kalla på här..
        tag1Label.setText(ad.getTagsList().get(0));
        tag2Label.setText(ad.getTagsList().get(1));
        tag3Label.setText(ad.getTagsList().get(2));
        tag4Label.setText(ad.getTagsList().get(3));
        tag5Label.setText(ad.getTagsList().get(4));


        showLabels();
        setEnablePictureChange(false);
        greyZone.setDisable(false);
        greyZone.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        editButton.setVisible(true);
        saveButton.setVisible(false);
    }

    private boolean allTextFieldsFilled() {
        return (!adTitleTextField.getText().isEmpty() && !adDescriptionTextField.getText().isEmpty() && (adLocationComboBox.getSelectionModel().getSelectedItem() != null) && !adPriceTextField.getText().isEmpty());
    }

    private ArrayList<String> getTagsText() {

        ArrayList<String> tempList = new ArrayList<>();
        tempList.add(tag1TextField.getText());
        tempList.add(tag2TextField.getText());
        tempList.add(tag3TextField.getText());
        tempList.add(tag4TextField.getText());
        tempList.add(tag5TextField.getText());

        return tempList;

    }


    /**
     * Method that makes sure that the delete and edit button only shows on an ad if the logged in user
     * owns that specific ad.
     */
    void showUserButtons() {
        index = 0;
        if (isUsersAd()) {
            deleteButton.setVisible(true);
            editButton.setVisible(true);
            requestButton.setVisible(false);
        } else {
            deleteButton.setVisible(false);
            editButton.setVisible(false);
            requestButton.setVisible(true);
        }
    }


    private boolean isUsersAd() {
        if (byme.getCurrentUser() != null) {
            if (byme.getCurrentUser().getUsername().equals(ad.getAccount())) {
                return true;
            }
        }
        return false;
    }


    private void setVisible(Node node, Node node2, Node node3, Node node4, Node node5, boolean value){
        node.setVisible(value);
        node2.setVisible(value);
        node3.setVisible(value);
        node4.setVisible(value);
        node5.setVisible(value);
    }

    /**
     * Method which enables visibility for an ads TextFields and at the same time disables visibility for the Labels.
     * It also sets the text for all TextFields so they get the current data.
     */
    void showTextFields() {

        adTitle.setVisible(false);
        adLocation.setVisible(false);
        adDescription.setVisible(false);
        adPrice.setVisible(false);


        setVisible(tag1Label,tag2Label,tag3Label,tag4Label,tag5Label, false);



        adTitleTextField.setVisible(true);
        adLocationComboBox.setVisible(true);
        adDescriptionTextField.setVisible(true);
        adPriceTextField.setVisible(true);

        setVisible(tag1TextField,tag2TextField,tag3TextField,tag4TextField,tag5TextField, true);



        adTitleTextField.setText(adTitle.getText());
        adLocationComboBox.getSelectionModel().select(adLocation.getText());
        adDescriptionTextField.setText(adDescription.getText());
        adPriceTextField.setText(adPrice.getText());

        tag1TextField.setText(tag1Label.getText());
        tag2TextField.setText(tag2Label.getText());
        tag3TextField.setText(tag3Label.getText());
        tag4TextField.setText(tag4Label.getText());
        tag5TextField.setText(tag5Label.getText());


    }

    /**
     * Method which enables visibility for an ads Labels and at the same time disables visibility for the TextFields.
     */

    public void showLabels() {

        adTitle.setVisible(true);
        adLocation.setVisible(true);
        adDescription.setVisible(true);
        adPrice.setVisible(true);
        tag1Label.setVisible(true);


        setVisible(tag1Label,tag2Label,tag3Label,tag4Label,tag5Label, true);


        adTitleTextField.setVisible(false);
        adLocationComboBox.setVisible(false);
        adDescriptionTextField.setVisible(false);
        adPriceTextField.setVisible(false);


        setVisible(tag1TextField,tag2TextField,tag3TextField,tag4TextField,tag5TextField, false);


    }



    void openPictureChangePanel(){
            pictureChanger.update();
            greyZone.setDisable(true);
            greyZone.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
            pictureChanger.setVisible(true);
    }


    void closePictureChangePanel(){
        savePictures();
        updateImageViews();
        greyZone.setDisable(false);
        greyZone.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        pictureChanger.setVisible(false);
    }

    @FXML
    void nextImage(){
        ArrayList<BufferedImage> images = pictureChanger.getImages();
        if(images.size() > 0) {
            index++;
            image1.setImage(pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(images.get(abs(index) % images.size()), null)));
        }
    }

    @FXML
    void prevImage(){
        ArrayList<BufferedImage> images = pictureChanger.getImages();
        if(pictureChanger.getImages().size() > 0) {
            index--;
            image1.setImage(pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(images.get(abs(index) % images.size()), null)));
        }
    }


}
