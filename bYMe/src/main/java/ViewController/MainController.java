package ViewController;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.*;


/**
 *
 *@author Alexander Huang, Joel Jönsson, Adam Jawad, Johan Gottlander & Milos Bastajic.
 *
 * Used by: TagItem
 * Uses: Byme, Search, LoginController, MenuController, AdCreatorController, DetailViewController, Theme
 */
public class MainController implements Initializable, SidePanelToggler, ThemeSetter, DetailViewToggler, IObserver, AdItemsUpdater, TagSearcher {

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
    @FXML
    private ImageView bymeImage;

    private boolean dark_theme = false;

    private Theme default_theme = new Theme("#ecf0f1", "#bdc3c7", "#3498db", "#2980b9", "#f1c40f", "#f39c12", "#34495e", " #2c3e50", "#FFFFFF");
    private Theme alternative_theme = new Theme("#2C3A47", "#2f3640", "#273c75", "#192a56", "#fbc531", "#e1b12c", "#f5f6fa", "#dcdde1", "#000000");

    private LoginController loginController;
    private MenuController menuController;
    private AdCreatorController adController;
    private DetailViewController detailViewController;


    private Byme byme = Byme.getInstance(AccountHandler.getInstance(), AdHandler.getInstance());
    private List<String> tags = new ArrayList<>();
    private List<Integer> tagsCount = new ArrayList<>();
    private Map<String, AdItem> adItems = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginController = new LoginController(this, this);
        root.getChildren().add(loginController);
        menuController = new MenuController(this, this);
        root.getChildren().add(menuController);
        adController = new AdCreatorController(this);
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
        for (Ad ad : Search.findAds(searchInput.getText(), byme.getAds())) {
            adsListFlowPane.getChildren().add(adItems.get(ad.getAdId()));
        }
    }

    public void searchTags(String tagName) {

        Search.setNewActiveTag(tagName);
        adsListFlowPane.getChildren().clear();

        if (!Search.getActiveTag().equals(Search.getNewActiveTag())) {
            Search.setActiveTag(Search.getNewActiveTag());
            for (Ad ad : Search.findAds(tagName, byme.getAds())) {
                adsListFlowPane.getChildren().add(adItems.get(ad.getAdId()));
                populateTags();
            }
        } else {
            Search.setActiveTag("");
            populateAds();
        }

    }

    /**
     *
     */
    @FXML
    public void toggleSidePanel() {
        if (!byme.isLoggedIn()) {
            loginController.togglePanel();
        } else {
            menuController.togglePanel();
        }
    }

    /**
     *
     * @param login
     */
    @Override
    public void toggleSidePanel(boolean login) {
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
                        "tertiary-dark:" + theme.tertiary_dark + ";" + "\n" +
                        "extreme-color:" + theme.extreme_color + ";");

    }

    /**
     * Changes the theme from default to a dark theme, and the other way around(Used as a method on a toggle button).
     */
    @Override
    public void changeTheme() {
        if (!dark_theme) {
            setTheme(alternative_theme);
            dark_theme = true;
        } else {
            setTheme(default_theme);
            dark_theme = false;
        }
    }

    /**
     * This method updates all existing ads.
     */
    @Override
    public void updateAdItems() {
        for (Object obj : byme.getAds().values()) {
            Ad ad = (Ad) obj;
            if (!adItems.containsKey(ad.getAdId())) {
                adItems.put(ad.getAdId(), new AdItem(ad, this, byme.getAccountRating(ad.getAccount())));
            }
            adItems.get(ad.getAdId()).update();
        }
        populateAds();
    }

    @FXML
    void populateAllAds() {
        searchInput.setText("");
        Search.setActiveTag("");
        adsListFlowPane.getChildren().clear();
        populateAds();
    }

    private void populateAds() {
        adsListFlowPane.getChildren().clear();
        tags.clear();
        tagsCount.clear();
        for (Ad currentAd : byme.getAds().values()) {
            countTags(currentAd);
            AdItem currentAdItem = adItems.get(currentAd.getAdId());
            currentAdItem.update();
            adsListFlowPane.getChildren().add(currentAdItem);
        }
        populateTags();
    }


    private void countTags(Ad currentAd) {
        for (String tag : currentAd.getTagsList()) {
            if (!tag.equals("")) {
                if (!tags.contains(tag)) {
                    tags.add(tag);
                    tagsCount.add(1);
                } else {
                    int valueIndex = tags.indexOf(tag);
                    int newValue = tagsCount.get(valueIndex) + 1;
                    tagsCount.set(valueIndex, newValue);
                }
            }

        }
    }


    /**
     * This method opens the view for creating new ads if a user is logged in, else the right hand side pane for logging in toggles open.
     */
    @FXML
    void openCreateAd() {
        if (byme.isLoggedIn()) {
            adController.toggleCreateAdWindow();
        } else {
            loginController.togglePanel();
        }
    }

    /**
     * A method for toggling the open/close function of the view which display more information about an ad
     * @param openDetailView Is a boolean containing the information whether or not an ad is open or not.
     * @param ad Is the current ad that the user clicked on.
     */
    @Override
    public void toggleDetailView(boolean openDetailView, Ad ad) {
        if (openDetailView) {
            detailViewController.setVisible(true);
            detailViewController.setAd(ad);
            detailViewController.showUserButtons();
            detailViewController.showLabels();
            detailViewController.loadPictures();
            detailViewController.updateImageView();
        } else {
            detailViewController.setVisible(false);
        }
    }

    @Override
    public void update() {
        updateAdItems();
        menuController.populateMyAds();
    }

    private void populateTags() {
        tagsFlowPane.getChildren().clear();
        List<String> sortedTags = sortTags();
        for (int i = 0; i < sortedTags.size(); i += 2) {
            String currentTag = sortedTags.get(i);

            tagsFlowPane.getChildren().add(new TagItem(sortedTags.get(i), Integer.valueOf(sortedTags.get(i + 1)), currentTag.equals(Search.getActiveTag()), this));
        }
    }


    private List<String> sortTags() {
        List<Integer> values = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            keys.add(tags.get(i));
            values.add(tagsCount.get(i));
        }
        int tempValue;
        String tempKey;
        for (int i = 0; i < values.size(); i++) { //Bubblesort
            for (int j = 1; j < values.size() - i; j++) {
                if (values.get(j - 1) < values.get(j)) {
                    tempValue = values.get(j - 1);
                    tempKey = keys.get(j - 1);
                    values.set(j - 1, values.get(j));
                    keys.set(j - 1, keys.get(j));
                    values.set(j, tempValue);
                    keys.set(j, tempKey);
                }
            }
        }
        List<String> sortedTags = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            sortedTags.add(keys.get(i));
            sortedTags.add(String.valueOf(values.get(i)));
        }
        return sortedTags;
    }

}



