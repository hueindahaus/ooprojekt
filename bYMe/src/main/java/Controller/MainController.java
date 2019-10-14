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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.*;

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
    private ArrayList<String> tags = new ArrayList<>();
    private HashMap<String,AdItem> adItems = new HashMap<>();

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
        updateAdItems();
        populateAds();
        byme.addObserver(this);
    }

    @FXML
    void searchAds() {
        adsListFlowPane.getChildren().clear();
        for (Ad ad : search.findAds(searchInput.getText(), byme.getAds())) {
            adsListFlowPane.getChildren().add(adItems.get(ad.getAdId()));
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
                "main:"+ theme.main+";"+"\n"+
                "main-dark:" + theme.main_dark+";"+"\n"+
                "primary:"+theme.primary+";" + "\n"+
                "primary-dark:"+theme.primary_dark+";"+"\n"+
                "secondary:"+theme.secondary+";"+"\n"+
                "secondary-dark:"+theme.secondary_dark+";"+"\n"+
                "tertiary:"+theme.tertiary+";"+"\n"+
                "tertiary-dark:"+theme.tertiary_dark+";");

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

    public void updateAdItems(){
        for(Object obj: byme.getAds().values()){
            Ad ad = (Ad) obj;
            if(!adItems.containsKey(ad.getAdId())) {
                adItems.put(ad.getAdId(), new AdItem(ad, this));
            }
        }
        populateAds();
    }

    private void populateAds() {
        adsListFlowPane.getChildren().clear();
        tags.clear();
        HashMap<String, Ad> ads = byme.getAds();
        for (Map.Entry ad : ads.entrySet()) {
        Ad currentAd = (Ad) ad.getValue();
            if (!tags.contains(currentAd.getTitle())) {
                tags.add(currentAd.getTitle());
                tags.add("1");
            } else {
                int valueIndex = tags.indexOf(currentAd.getTitle())+1;
                String oldValue = tags.get(valueIndex);
                String newValue = String.valueOf(Integer.valueOf(oldValue)+1);
                tags.set(valueIndex, newValue);
            }
            AdItem currentAdItem = adItems.get(currentAd.getAdId());
            currentAdItem.update();
            adsListFlowPane.getChildren().add(currentAdItem);
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


    public void  toggleDetailView(boolean openDetailView, Ad ad){
        if (openDetailView){
            detailViewController.setVisible(true);
            detailViewController.setAd(ad);
            detailViewController.showUserButtons();
            detailViewController.showLabels();

            detailViewController.loadPictures();
            detailViewController.updateAdImageViews();
        }else {
            detailViewController.setVisible(false);
        }
    }


    public void update() {

        updateAdItems();
        menuController.populateMyAds();
    }

    public void populateTags(){
        tagsFlowPane.getChildren().clear();
        ArrayList<String> sortedTags = sortTags();
        for (int i = 0; i < sortedTags.size(); i += 2) {
            tagsFlowPane.getChildren().add(new tagItem(sortedTags.get(i), Integer.valueOf(sortedTags.get(i+1))));
        }
    }

    private ArrayList<String> sortTags(){
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        for(int i = 0; i < tags.size(); i++) {
            if(i%2 == 0){
                keys.add(tags.get(i));
            } else {
                values.add(tags.get(i));
            }
        }
        String tempValue;
        String tempKey;
        for(int i = 0; i < values.size(); i++){ //Bubblesort
            for(int j=1; j < values.size()-i; j++){
                if(Integer.valueOf(values.get(j-1)) < Integer.valueOf(values.get(j))){
                   tempValue = values.get(j-1);
                   tempKey = keys.get(j-1);
                   values.set(j-1, values.get(j));
                   keys.set(j-1, keys.get(j));
                   values.set(j, tempValue);
                   keys.set(j, tempKey);
                }
            }
        }
        ArrayList<String> sortedTags = new ArrayList<>();
        for(int i = 0; i < values.size(); i++) {
            sortedTags.add(keys.get(i));
            sortedTags.add(values.get(i));
        }
        return sortedTags;
    }

}


