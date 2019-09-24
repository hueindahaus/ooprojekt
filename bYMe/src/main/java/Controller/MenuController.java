package Controller;

import Model.Byme;
import Services.AccountHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class MenuController extends SidePanelController {

    @FXML
    Button categoryButton;

    @FXML
    ImageView categoryImage;

    private Byme byme = Byme.getInstance(AccountHandler.getInstance());

    public MenuController() {
        super("../signedIn.fxml");

        hidePanel = new Timeline(                                                                                      //animation för att gömma login-panelen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(menuPanel.layoutXProperty(), 1440))

        );

        showPanel = new Timeline(                                                                                      //animation för att visa login-panelen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(menuPanel.layoutXProperty(), 1220))

        );

        hideGreyZone = new Timeline(                                                                                        //animation för att gömma gråzonen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(greyZone.opacityProperty(), 0))
        );

        showGreyZone = new Timeline(                                                                                        //animation för att visa gråzonen
                new KeyFrame(Duration.seconds(0.6), new KeyValue(greyZone.opacityProperty(), 1))
        );
    }
    @FXML AnchorPane menuPanel;
    @FXML AnchorPane greyZone;



    void setGreyZoneDisable(boolean value){
        greyZone.setDisable(value);
    }

    @FXML void signout(){
        byme.signoutUser();
        toggleMenuPanel();
    }

}
