package Controller;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public abstract class SidePanelController extends AnchorPane {


    Timeline showPanel;
    Timeline hidePanel;
    Timeline hideGreyZone;
    Timeline showGreyZone;

    private boolean panelIsToggled= false;

    public SidePanelController(String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }
    }

    @FXML void togglePanel(){
        if (panelIsToggled) {
            hidePanel.play();
            hideGreyZone.play();
            setGreyZoneDisable(true);
            panelIsToggled = false;
        } else {
            showPanel.play();
            showGreyZone.play();
            setGreyZoneDisable(false);
            panelIsToggled = true;
        }
    }


    abstract void setGreyZoneDisable(boolean value);


}
