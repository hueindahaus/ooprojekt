package Controller;

import Model.Ad;
import Model.AdList;
import Model.Byme;
import Services.AccountHandler;
import Services.AdHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable, PanelToggler, ThemeSetter, AdCreator {

    //FX grejer, borde kanske flyttas

    @FXML
    AnchorPane root;
    @FXML
    Button primaryButton;
    @FXML
    Button addAdButton;

    @FXML
    ImageView exitButtonImage;

    @FXML
    private FlowPane adsListFlowPane;
    @FXML
    private AnchorPane adsPane;
    @FXML
    private AnchorPane detailPane;


    private boolean dark_theme = false;

    private Theme default_theme = new Theme("#ecf0f1", "#bdc3c7", "#3498db", "#2980b9", "#f1c40f", "#f39c12", "#34495e", " #2c3e50");
    private Theme alternative_theme = new Theme("#2C3A47", "#2f3640", "#273c75", "#192a56", "#fbc531", "#e1b12c", "#f5f6fa", "#dcdde1");

    private LoginController loginController;
    private MenuController menuController;
    private AdController adController;

    private Byme byme = Byme.getInstance(AccountHandler.getInstance(), AdHandler.getInstance());

    AdList adList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginController = new LoginController(this, this);
        root.getChildren().add(loginController);
        menuController = new MenuController(this);
        root.getChildren().add(menuController);
        adController = new AdController(this);
        root.getChildren().add(adController);
        populateAds();
    }


    @FXML
    public void togglePanel() {
        if (byme.getCurrentUser() == null) {
            loginController.togglePanel();
        }
        else{
            menuController.togglePanel();
        }
    }

    public void togglePanel(boolean login){
        if(login) {
            loginController.toggleOffPanel();
            menuController.toggleOnPanel();
        }
    }

    private void setTheme(Theme theme){
        root.setStyle(
                "main:"+ theme.main+";"+"\n"+
                "main-dark:" + theme.main_dark+";"+"\n"+
                "primary:"+theme.primary+";" + "\n"+
                "primary-dark:"+theme.primary_dark+";"+"\n"+
                "secondary:"+theme.secondary+";"+"\n"+
                "secondary-dark:"+theme.secondary_dark+";"+"\n"+
                "tertiary:"+theme.tertiary+";"+"\n"+
                "tertiary-dark:"+theme.tertiary_dark+";");
    }

    public void changeTheme(){
        if(!dark_theme) {
            setTheme(alternative_theme);
            dark_theme = true;
        } else {
            setTheme(default_theme);
            dark_theme = false;
        }
    }

    //Fattade inte riktigt hur det var tänkt med AdList, så la detta här istället.


    public void populateAds(){
        adsListFlowPane.getChildren().clear();
        HashMap<String, Ad> ads = byme.getAds();
        for(Map.Entry ad: ads.entrySet()){
            Ad currentAd = (Ad) ad.getValue();
            adsListFlowPane.getChildren().add(new AdList(currentAd.getTitle(), currentAd.getLocation(), currentAd.getPrice(), currentAd.getDescription()));
        }
    }

    public void createAd(String title, String description, int price, String location){
        byme.createAd(title, description, price, location);
    }

    @FXML
    void openCreateAd(){
        if(byme.getCurrentUser() != null){
            adController.toggleCreateAdWindow();
        } else {
            loginController.togglePanel();
        }
    }

    @FXML
    public void closeDetailView(){
        detailPane.toBack();
        adsPane.toFront();
    }

    public void openDetailView(){
        detailPane.toFront();
        adsPane.toBack();
    }


    @FXML
    public void closeButtonMouseEntered(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "images/icon_close_hover.png")));
    }

    @FXML
    public void closeButtonMousePressed(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "images/icon_close_pressed.png")));
    }

    @FXML
    public void closeButtonMouseExited(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "images/icon_close.png")));
    }
    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }


}


