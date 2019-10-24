package ViewController;

import Model.Byme;
import Services.AccountHandler;
import Services.AdHandler;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the FXML-file:createAdWindow containing all of the fxml-elements needed for creating a new ad item.
 * @author Joel Jönsson, Alexander Huang.
 *
 * Uses: AdItemsUpdater, PictureHandler, PictureChangeController, Byme.
 * Used by: MainController.
 */

public class AdCreatorController extends AnchorPane {

    @FXML
    private
    AnchorPane createAdBoxFrame;

    @FXML
    private
    AnchorPane greyZone;
    @FXML
    private
    AnchorPane mouseBlockerPane;

    @FXML
    private
    TextField adTitle;

    @FXML
    private
    TextField adPrice;
    @FXML
    private
    TextField adDescription;
    @FXML
    private
    TextField tag1TextField;
    @FXML
    private
    TextField tag2TextField;
    @FXML
    private
    TextField tag3TextField;
    @FXML
    private
    TextField tag4TextField;
    @FXML
    private
    TextField tag5TextField;

    @FXML
    private
    ComboBox adLocation;

    @FXML
    private
    Label errormessage;

    private Timeline hideGreyZone;

    private Timeline showGreyZone;

    @FXML
    private
    ImageView imageView;

    private PictureHandler pictureHandler = PictureHandler.getInstance();

    private PictureChangeController pictureChanger = new PictureChangeController(null);

    private AdItemsUpdater adItemsUpdater;


    private Byme byme = Byme.getInstance(AccountHandler.getInstance(), AdHandler.getInstance());

    AdCreatorController(AdItemsUpdater adItemsUpdater) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/createAdWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        adLocation.getItems().addAll("Västra Götaland", "Stockholm", "Skåne", "Jönköping", "Bergsjön");
        createAdBoxFrame.setVisible(false);

        hideGreyZone = new Timeline(                                                                                        //animation för att gömma gråzonen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(greyZone.opacityProperty(), 0))
        );

        showGreyZone = new Timeline(                                                                                        //animation för att visa gråzonen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(greyZone.opacityProperty(), 1))
        );


        adPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    adPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if (newValue.length() > 0 && newValue.charAt(0) == '0') {
                    adPrice.setText(oldValue);
                }
            }
        });

        this.getChildren().add(pictureChanger);


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

        updateImageViews();

        this.adItemsUpdater = adItemsUpdater;


    }

    @FXML
    void toggleCreateAdWindow() {
        if (createAdBoxFrame.isVisible()) {
            createAdBoxFrame.setVisible(false);
            hideGreyZone.play();
            greyZone.setDisable(true);
            pictureChanger.resetImageList();
        } else {
            createAdBoxFrame.setVisible(true);
            showGreyZone.play();
            greyZone.setDisable(false);
        }
    }

    @FXML
    void createAd() {

        ErrorMessageController.handleAdCreationErrors(adTitle, adPrice, adLocation, adDescription, errormessage);

        if (allTextFieldsFilled()) {
            byme.createAd(adTitle.getText(), adDescription.getText(), Integer.valueOf(adPrice.getText()), adLocation.getSelectionModel().getSelectedItem().toString(), byme.getCurrentUsersUsername(), getTagsTextField());
            saveRecentlyAddedPictures();
            toggleCreateAdWindow();
            adItemsUpdater.updateAdItems();
            resetAllFields();
        }

    }


    void saveRecentlyAddedPictures() {
        pictureHandler.saveAdPictures(byme.getLastAddedAdId(), pictureChanger.getImages());
    }

    private boolean allTextFieldsFilled() {
        return (!adTitle.getText().isEmpty() && !adPrice.getText().isEmpty() && adLocation.getSelectionModel().getSelectedItem() != null && !adDescription.getText().isEmpty());
    }


    List<String> getTagsTextField() {
        List<String> tagsTemp = new ArrayList<>();
        tagsTemp.add(tag1TextField.getText());
        tagsTemp.add(tag2TextField.getText());
        tagsTemp.add(tag3TextField.getText());
        tagsTemp.add(tag4TextField.getText());
        tagsTemp.add(tag5TextField.getText());

        return tagsTemp;
    }


    private void updateImageViews() {
        if (pictureChanger.getImages().size() > 0) {
            imageView.setImage(SwingFXUtils.toFXImage(pictureChanger.getImages().get(0), null));
        } else {
            try {
                imageView.setImage(SwingFXUtils.toFXImage(ImageIO.read(new File("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "ViewController/images" + File.separatorChar + "insert_photo.png")), null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void openPictureChangePanel() {
        pictureChanger.setVisible(true);
        greyZone.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
        mouseBlockerPane.setVisible(true);
        pictureChanger.update();
    }


    private void closePictureChangePanel() {
        pictureChanger.setVisible(false);
        greyZone.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        mouseBlockerPane.setVisible(false);
        updateImageViews();
    }

    private void resetAllFields() {
        pictureChanger.resetImageList();
        updateImageViews();
        adPrice.setText("");
        adLocation.getSelectionModel().select(null);
        adTitle.setText("");
        adDescription.setText("");
    }


}
