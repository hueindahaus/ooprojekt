package Controller;

import Model.Byme;
import Services.AccountHandler;
import Services.AdHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

    //SKA EJ BERO PÅ MAINCONTROLLER. GÖR EN INTERFACE?
    private MainController mainController;

    private Byme byme = Byme.getInstance(AccountHandler.getInstance(), AdHandler.getInstance());

    AdController(MainController mainController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../createAdWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }
        createAdBoxFrame.setVisible(false);
        //FULT!!!
        this.mainController = mainController;
    }

    @FXML
    void toggleCreateAdWindow(){
        if(createAdBoxFrame.isVisible()){
            createAdBoxFrame.setVisible(false);
            greyZone.setDisable(true);
            greyZone.setVisible(false);
        } else {
            createAdBoxFrame.setVisible(true);
            greyZone.setDisable(false);
            greyZone.setVisible(true);
        }
    }

    @FXML
    void createAd(){
        byme.createAd(adTitle.getText(), adDescription.getText(), Integer.valueOf(adPrice.getText()), "");
        mainController.populateAds();
        toggleCreateAdWindow();
    }
}
