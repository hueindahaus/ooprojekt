package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class AdController extends AnchorPane {

    @FXML
    AnchorPane createAdBoxFrame;

    @FXML
    AnchorPane greyZone;

    @FXML
    TextField adTitle;

    @FXML
    TextField adPrice;
    @FXML
    TextField adDescription;

    @FXML
    ComboBox adLocation;

    Timeline hideGreyZone;

    Timeline showGreyZone;

    private AdCreator adCreator;

    AdController(AdCreator adCreator){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../createAdWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }
        adLocation.getItems().addAll("Västra Götaland", "Stockholm", "Skåne", "Jönköping", "Bergsjön");
        createAdBoxFrame.setVisible(false);
        this.adCreator = adCreator;

        hideGreyZone = new Timeline(                                                                                        //animation för att gömma gråzonen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(greyZone.opacityProperty(), 0))
        );

        showGreyZone = new Timeline(                                                                                        //animation för att visa gråzonen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(greyZone.opacityProperty(), 1))
        );

    }

    @FXML
    void toggleCreateAdWindow(){
        if(createAdBoxFrame.isVisible()){
            createAdBoxFrame.setVisible(false);
            hideGreyZone.play();
            greyZone.setDisable(true);
        } else {
            createAdBoxFrame.setVisible(true);
            showGreyZone.play();
            greyZone.setDisable(false);
        }
    }

    @FXML
    void createAd(){
        adCreator.createAd(adTitle.getText(), adDescription.getText(), Integer.valueOf(adPrice.getText()), adLocation.getSelectionModel().getSelectedItem().toString());
        adCreator.populateAds();
        toggleCreateAdWindow();
    }
}
