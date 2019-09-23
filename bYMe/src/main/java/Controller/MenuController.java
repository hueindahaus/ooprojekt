package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.rmi.CORBA.Tie;
import java.io.IOException;

public class MenuController extends AnchorPane {

    @FXML
    Button categoryButton;

    @FXML
    ImageView categoryImage;

    public MenuController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../signedIn.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        hideMenuPanel = new Timeline(                                                                                      //animation för att gömma login-panelen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(menuPanel.layoutXProperty(), 1440))

        );

        showMenuPanel = new Timeline(                                                                                      //animation för att visa login-panelen
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
    private boolean menuPanelIsToggled = false;

    @FXML void toggleMenuPanel(){
        if (menuPanelIsToggled) {
            hideMenuPanel.play();
            hideGreyZone.play();
            greyZone.setDisable(true);
            menuPanelIsToggled = false;
        } else {
            showMenuPanel.play();
            showGreyZone.play();
            greyZone.setDisable(false);
            menuPanelIsToggled = true;
        }
    }
    Timeline showMenuPanel;
    Timeline hideMenuPanel;
    Timeline hideGreyZone;
    Timeline showGreyZone;

}
