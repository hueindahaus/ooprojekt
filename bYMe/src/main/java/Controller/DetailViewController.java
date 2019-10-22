package Controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Scanner;


public class DetailViewController extends AnchorPane{


    DetailViewToggler detailViewToggler;

    PictureHandler pictureHandler = new PictureHandler();

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
    Label userRating;


    @FXML
    TextField adTitleTextField;
    @FXML
    ComboBox adLocationComboBox;;
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
    AnchorPane pictureChangePanel;
    @FXML
    ImageView image1;
    @FXML
    TextField messageContent;
    @FXML
    TextField requestDay;
    @FXML
    TextField requestMonth;
    @FXML
    TextField requestYear;
    @FXML
    TextField requestHour;
    @FXML
    TextField requestMinute;

    @FXML
    void changeAdPic1(){
        changeAdPic(0);
    }

    @FXML
    void changeAdPic2(){
        changeAdPic(1);
    }

    @FXML
    void changeAdPic3(){
        changeAdPic(2);
    }

    @FXML
    void changeAdPic4(){
        changeAdPic(3);
    }

    @FXML
    void changeAdPic5(){
        changeAdPic(4);
    }

    ColorAdjust pictureEffect = new ColorAdjust();


    Timeline showPrompt;

    Timeline closePrompt;

    Timeline showRequestPrompt;

    Timeline closeRequestPrompt;


    Ad ad;

    ArrayList<BufferedImage> images = new ArrayList<>();

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

        showRequestPrompt = new Timeline(
                new KeyFrame(Duration.seconds(0.05), new KeyValue(requestPane.layoutYProperty(), 350))
        );

        closeRequestPrompt = new Timeline(
                new KeyFrame(Duration.seconds(0.05), new KeyValue(requestPane.layoutYProperty(), 900))
        );

        this.detailViewToggler = detailViewToggler;
        pictureEffect.setBrightness(-0.5);
        pictureEffect.setInput(new BoxBlur());

        setHoverEffectOnImageView(imageChanger1);
        setHoverEffectOnImageView(imageChanger2);
        setHoverEffectOnImageView(imageChanger3);
        setHoverEffectOnImageView(imageChanger4);
        setHoverEffectOnImageView(imageChanger5);

        adLocationComboBox.getItems().addAll("Västra Götaland", "Stockholm", "Skåne", "Jönköping", "Bergsjön");

        adPriceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    adPriceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if(newValue.length() > 0 && newValue.charAt(0) == '0'){
                    adPriceTextField.setText(oldValue);
                }
            }
        });
    }



    private void setHoverEffectOnImageView(ImageView imageView){
        imageView.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    imageView.setEffect(pictureEffect);
                } else {
                    imageView.setEffect(null);
                }
            }
        });
    }

    /**
     *
     * @param ad Sends in an ad as parameter so the detailView can show the data of an ad.
     */
    void setAd(Ad ad){
        this.ad = ad;
        if (ad != null) {
            adTitle.setText(ad.getTitle());
            adDescription.setText(ad.getDescription());
            adLocation.setText(ad.getLocation());
            adUser.setText(ad.getAccount());
            adPrice.setText(String.valueOf(ad.getPrice()));


            if (!(ad.getTagsList().size() == 0)){
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
        pictureHandler.removePictureFolder(ad.getAdId());
        byme.removeAd(ad.getAdId());
        removeAdClosePrompt();
        closeDetailView();
    }

    @FXML
    void sendRequestPrompt(){
        if(byme.isLoggedIn()) {
            messageContent.setText("");
            showRequestPrompt.play();
            greyZone2.setVisible(true);
        }
    }

    @FXML
    void sendRequestClosePrompt(){
        closeRequestPrompt.play();
        greyZone2.setVisible(false);
    }

    @FXML
    void sendRequest() throws ParseException {
        String date = requestDay.getText() + "/" + requestMonth.getText() + "/" + requestYear.getText() + "-" + requestHour.getText() + ":" + requestMinute.getText();
        byme.sendRequest(byme.getCurrentUser().getUsername(), ad.getAccount(), ad, messageContent.getText(), date);
        sendRequestClosePrompt();
    }

    void updateAdImageViews(){
        if(images.size() > 0){
            image1.setImage(SwingFXUtils.toFXImage(images.get(0), null));
        } else {
            try {
                image1.setImage(SwingFXUtils.toFXImage(ImageIO.read(new File("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "images" + File.separatorChar + "insert_photo.png")), null));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    void changeAdPic(int index){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpeg", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);



        if(selectedFile != null){
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                if(images.size() - 1 >= index) {
                    images.add(index, image);
                    images.remove(index+1);
                } else {
                    images.add(image);
                }
                updateAdImageViews();
                updatePictureChangePanel();
            } catch(IOException exception){
                System.out.println("Can't read image: " + selectedFile.getPath());
            }
        }
    }




    void savePictures(){
        if(ad != null) {
            pictureHandler.saveAdPictures(ad.getAdId(), images);
        }
    }

    void loadPictures(){
        if(ad != null) {
            images = pictureHandler.getAdPictures(ad.getAdId());
        }
    }


    private void setEnablePictureChange(boolean enable){
        if(enable) {
            image1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    openPictureChangePanel();
                }
            });

            image1.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    image1.setEffect(pictureEffect);
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
    void editAd(){

        showTextFields();
        setEnablePictureChange(true);
        greyZone.setDisable(true);
        editButton.setVisible(false);
        greyZone.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
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

    }

    private boolean allTextFieldsFilled(){
        return (!adTitleTextField.getText().isEmpty()  && !adDescriptionTextField.getText().isEmpty() && (adLocationComboBox.getSelectionModel().getSelectedItem() != null) && !adPriceTextField.getText().isEmpty());
    }

    private ArrayList<String> getTagsText(){

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

        tag1Label.setVisible(false);
        tag2Label.setVisible(false);
        tag3Label.setVisible(false);
        tag4Label.setVisible(false);
        tag5Label.setVisible(false);


        adTitleTextField.setVisible(true);
        adLocationComboBox.setVisible(true);
        adDescriptionTextField.setVisible(true);
        adPriceTextField.setVisible(true);

        tag1TextField.setVisible(true);
        tag2TextField.setVisible(true);
        tag3TextField.setVisible(true);
        tag4TextField.setVisible(true);
        tag5TextField.setVisible(true);

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
     *
     */

    public void showLabels(){

        adTitle.setVisible(true);
        adLocation.setVisible(true);
        adDescription.setVisible(true);
        adPrice.setVisible(true);
        tag1Label.setVisible(true);

        tag1Label.setVisible(true);
        tag2Label.setVisible(true);
        tag3Label.setVisible(true);
        tag4Label.setVisible(true);
        tag5Label.setVisible(true);

        adTitleTextField.setVisible(false);
        adLocationComboBox.setVisible(false);
        adDescriptionTextField.setVisible(false);
        adPriceTextField.setVisible(false);

        tag1TextField.setVisible(false);
        tag2TextField.setVisible(false);
        tag3TextField.setVisible(false);
        tag4TextField.setVisible(false);
        tag5TextField.setVisible(false);

    }


    void openPictureChangePanel(){
            updatePictureChangePanel();
            pictureChangePanel.setVisible(true);

    }

    @FXML
    void closePictureChangePanel(){
        savePictures();
        updateAdImageViews();
        pictureChangePanel.setVisible(false);
    }



    @FXML
    ImageView imageChanger1;
    @FXML
    ImageView imageChanger2;
    @FXML
    ImageView imageChanger3;
    @FXML
    ImageView imageChanger4;
    @FXML
    ImageView imageChanger5;




    void updatePictureChangePanel(){
        try {
            Image defaultImage = SwingFXUtils.toFXImage(ImageIO.read(new File("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "images" + File.separatorChar + "insert_photo.png")), null);
            imageChanger1.setImage(defaultImage);
            imageChanger2.setImage(defaultImage);
            imageChanger3.setImage(defaultImage);
            imageChanger4.setImage(defaultImage);
            imageChanger5.setImage(defaultImage);
        }catch(IOException e){
            e.printStackTrace();
        }
        setImageIfPossible(imageChanger1,0,images);
        setImageIfPossible(imageChanger2,1,images);
        setImageIfPossible(imageChanger3,2,images);
        setImageIfPossible(imageChanger4,3,images);
        setImageIfPossible(imageChanger5,4,images);
    }

    private void setImageIfPossible(ImageView imageView, int num, ArrayList<BufferedImage> list){
        if(list.size() > num){
            imageView.setImage(pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(list.get(num),null)));
        }
    }



}
