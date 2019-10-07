package Controller;

import Model.Ad;
import Model.Byme;
import Services.AccountHandler;
<<<<<<< Updated upstream
=======
import javafx.event.Event;

>>>>>>> Stashed changes
import Services.AdHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
<<<<<<< Updated upstream
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
=======
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


import javafx.scene.layout.FlowPane;

>>>>>>> Stashed changes

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable, SIdePanelToggler, ThemeSetter, AdCreator, DetailViewToggler {

    @FXML
    AnchorPane root;
    @FXML
    Button primaryButton;
<<<<<<< Updated upstream
=======


    @FXML
    ImageView exitButtonImage;
>>>>>>> Stashed changes

    @FXML
    private FlowPane adsListFlowPane;

    private boolean dark_theme = false;

    private Theme default_theme = new Theme("#ecf0f1", "#bdc3c7", "#3498db", "#2980b9", "#f1c40f", "#f39c12", "#34495e", " #2c3e50");
    private Theme alternative_theme = new Theme("#2C3A47", "#2f3640", "#273c75", "#192a56", "#fbc531", "#e1b12c", "#f5f6fa", "#dcdde1");

    private LoginController loginController;
    private MenuController menuController;
    private AdController adController;
    private DetailViewController detailViewController;

    private Byme byme = Byme.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginController = new LoginController(this, this);
        root.getChildren().add(loginController);
        menuController = new MenuController(this,this);
        root.getChildren().add(menuController);
        adController = new AdController(this);
        root.getChildren().add(adController);
        detailViewController = new DetailViewController(this);
        root.getChildren().add(detailViewController);
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

<<<<<<< Updated upstream
=======
    @FXML
     public void openDetailView(MouseEvent event){
        detailPane.toFront();
        adsPane.toBack();
    }


    @FXML
    private void closeDetailView(){
        detailPane.toBack();
        adsPane.toFront();
    }

    @FXML
    public void closeButtonMouseEntered(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "main/images/icon_close_hover.png")));
    }

    @FXML
    public void closeButtonMousePressed(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "main/images/icon_close_pressed.png")));
    }

    @FXML
    public void closeButtonMouseExited(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "main/images/icon_close.png")));
    }

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }


>>>>>>> Stashed changes
    public void changeTheme(){
        if(!dark_theme) {
            setTheme(alternative_theme);
            dark_theme = true;
        } else {
            setTheme(default_theme);
            dark_theme = false;
        }
    }

    //Fattade inte riktigt hur det var tänkt med AdItem, så la detta här istället.


    public void populateAds(){
        adsListFlowPane.getChildren().clear();
        HashMap<String, Ad> ads = byme.getAds();
        for(Map.Entry ad: ads.entrySet()){
            Ad currentAd = (Ad) ad.getValue();
            adsListFlowPane.getChildren().add(new AdItem(currentAd, this));
        }
    }

    public void createAd(String title, String description, int price, String location){
        byme.createAd(title, description, price, location, byme.getCurrentUser().getUsername());
    }

    @FXML
    void openCreateAd(){
        if(byme.getCurrentUser() != null){
            adController.toggleCreateAdWindow();
        } else {
            loginController.togglePanel();
        }
    }

<<<<<<< Updated upstream
    public void  toggleDetailView(boolean value, Ad ad){
        if (value){
            detailViewController.setVisible(true);
            detailViewController.setAd(ad);
            if(byme.getCurrentUser() != null){
                if(byme.getCurrentUser().getUsername().equals(ad.getAccount())){
                    detailViewController.deleteButton.setVisible(true);
                }
            }
        }else {
            detailViewController.setVisible(false);
        }
    }
=======

>>>>>>> Stashed changes

}


