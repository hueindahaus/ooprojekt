package Controller;

import Model.Ad;
import Model.Byme;
import Model.IObserver;
import Model.Search;
import Services.AccountHandler;
import Services.AdHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainController implements Initializable, SidePanelToggler, ThemeSetter, AdCreator, DetailViewToggler, IObserver {

    @FXML
    AnchorPane root;
    @FXML
    Button primaryButton;
    @FXML
    private FlowPane adsListFlowPane;
    @FXML
    private TextField searchInput;
    @FXML
    private FlowPane tagsFlowPane;

    private boolean dark_theme = false;

    private Theme default_theme = new Theme("#ecf0f1", "#bdc3c7", "#3498db", "#2980b9", "#f1c40f", "#f39c12", "#34495e", " #2c3e50");
    private Theme alternative_theme = new Theme("#2C3A47", "#2f3640", "#273c75", "#192a56", "#fbc531", "#e1b12c", "#f5f6fa", "#dcdde1");

    private LoginController loginController;
    private MenuController menuController;
    private AdController adController;
    private DetailViewController detailViewController;

    private Byme byme = Byme.getInstance(AccountHandler.getInstance(), AdHandler.getInstance());
    private Search search = new Search();
    private ArrayList<Ad> searchAds;
    private HashMap<String, Integer> tags = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginController = new LoginController(this, this);
        root.getChildren().add(loginController);
        menuController = new MenuController(this, this);
        root.getChildren().add(menuController);
        adController = new AdController(this);
        root.getChildren().add(adController);
        detailViewController = new DetailViewController(this);
        root.getChildren().add(detailViewController);
        populateAds();
        byme.addObserver(this);
        populateTags();
    }

    @FXML
    void searchAds() {
        adsListFlowPane.getChildren().clear();
        searchAds = search.findAds(searchInput.getText(), byme.getAds());
        for (Ad ad : searchAds) {
            adsListFlowPane.getChildren().add(new AdItem(ad, this));

        }
    }


    @FXML
    public void togglePanel() {
        if (byme.getCurrentUser() == null) {
            loginController.togglePanel();
        } else {
            menuController.togglePanel();
        }
    }

    public void togglePanel(boolean login) {
        if (login) {
            loginController.toggleOffPanel();
            menuController.toggleOnPanel();
        }
    }

    private void setTheme(Theme theme) {
        root.setStyle(
                "main:" + theme.main + ";" + "\n" +
                        "main-dark:" + theme.main_dark + ";" + "\n" +
                        "primary:" + theme.primary + ";" + "\n" +
                        "primary-dark:" + theme.primary_dark + ";" + "\n" +
                        "secondary:" + theme.secondary + ";" + "\n" +
                        "secondary-dark:" + theme.secondary_dark + ";" + "\n" +
                        "tertiary:" + theme.tertiary + ";" + "\n" +
                        "tertiary-dark:" + theme.tertiary_dark + ";");
    }

    public void changeTheme() {
        if (!dark_theme) {
            setTheme(alternative_theme);
            dark_theme = true;
        } else {
            setTheme(default_theme);
            dark_theme = false;
        }
    }

    //Fattade inte riktigt hur det var tänkt med AdItem, så la detta här istället.


    public void populateAds() {
        adsListFlowPane.getChildren().clear();
        tags.clear();
        HashMap<String, Ad> ads = byme.getAds();
        for (Map.Entry ad : ads.entrySet()) {
            Ad currentAd = (Ad) ad.getValue();
            if(tags.get(currentAd.getTitle()) == null){
                tags.put(currentAd.getTitle(), 1);
            } else {
                Integer oldValue = tags.get(currentAd.getTitle());
                Integer newValue = oldValue + 1;
                tags.replace(currentAd.getTitle(), oldValue, newValue);
            }
            adsListFlowPane.getChildren().add(new AdItem(currentAd, this));
        }
        populateTags(); //Won't update when in update() (When you create a new ad, works when you sign-in/out)
    }

    public void createAd(String title, String description, int price, String location) {
        byme.createAd(title, description, price, location, byme.getCurrentUser().getUsername());
    }

    @FXML
    void openCreateAd() {
        if (byme.getCurrentUser() != null) {
            adController.toggleCreateAdWindow();
        } else {
            loginController.togglePanel();
        }
    }

    public void toggleDetailView(boolean value, Ad ad) {
        if (value) {
            detailViewController.setVisible(true);
            detailViewController.setAd(ad);
            detailViewController.showUserButtons();
            detailViewController.showLabels();
        } else {
            detailViewController.setVisible(false);
            //detailViewController.editAd(ad);
        }
    }


    public void update() {
        populateAds();
        menuController.populateMyAds();
    }

    public void populateTags(){
        tagsFlowPane.getChildren().clear();
        if(tags.size() > 0) {
            for (Map.Entry tag : tags.entrySet()) {
                String name = (String) tag.getKey();
                Integer noInstances = (Integer) tag.getValue();
                tagsFlowPane.getChildren().add(new tagItem(name, noInstances));
            }
        }
    }

}


