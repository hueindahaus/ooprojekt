package ViewController;


import Model.*;
import Services.PictureHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * This class is responsible for the FXML-file:signedin containing all of the fxml-elements needed for displaying the sidepanel when a user is signed in.
 * When a user is signed in they can view and manage their ads, requests and profile picture. They can also sign out and change theme.
 * @author Alexander Huang, Milos Bastajic, Joel Jönsson.
 *
 * Uses: Byme, Ad, DetailViewToggler, Request, Ad.
 * Used by: MainController.
 */
public class MenuController extends SidePanelController implements IObserver {

    private ThemeSetter themeSetter;

    @FXML
    private
    Button categoryButton;

    @FXML
    private
    ImageView profilePicImageView;

    @FXML
    private
    Label pictureChangeLabel;

    @FXML
    private
    AnchorPane myAdsPanel;

    @FXML
    private
    FlowPane myAdsFlowPane;

    @FXML
    private
    Button myAdsButton;

    @FXML
    private
    AnchorPane myRequestsPanel;

    @FXML
    private
    FlowPane myReceivedRequestsFlowPane;

    @FXML
    private
    FlowPane mySentRequestsFlowPane;

    @FXML
    private
    Button myRequestsButton;

    private PictureHandler pictureHandler = PictureHandler.getInstance();

    @FXML
    private
    AnchorPane mySentRequestsAnchorPane;

    @FXML
    private
    AnchorPane myReceivedRequestsAnchorPane;

    @FXML
    private
    Button toggleReceviedButton;

    @FXML
    private
    Button toggleSentButton;

    @FXML
    private
    Label userRating;

    private Timeline showMyAdsPanel;

    private Timeline hideMyAdsPanel;

    private Timeline showMyRequestsPanel;

    private Timeline hideMyRequestsPanel;

    private int panelState = 0;

    private Byme byme = Byme.getInstance(null, null);

    private List<Request> requests = new ArrayList<>();

    private DetailViewToggler detailViewToggler;
    @FXML
    private AnchorPane menuPanel;
    @FXML
    private AnchorPane greyZone;
    @FXML
    private
    Label currentUser;

    MenuController(ThemeSetter themeSetter, DetailViewToggler detailViewToggler) {
        super("FXML/signedIn.fxml");
        byme.addObserver(this);
        this.themeSetter = themeSetter;
        this.detailViewToggler = detailViewToggler;

        hidePanel = new Timeline(                                                                                      //animation för att gömma login-panelen
                new KeyFrame(Duration.seconds(0.2), new KeyValue(menuPanel.layoutXProperty(), 1440)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(myAdsPanel.layoutXProperty(), 1660)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(myRequestsPanel.layoutXProperty(), 1660))

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

        showMyAdsPanel = new Timeline(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(myAdsPanel.layoutXProperty(), 520))
        );

        hideMyAdsPanel = new Timeline(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(myAdsPanel.layoutXProperty(), 1660))
        );

        showMyRequestsPanel = new Timeline(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(myRequestsPanel.layoutXProperty(), 520))
        );

        hideMyRequestsPanel = new Timeline(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(myRequestsPanel.layoutXProperty(), 1660))
        );

        Circle clip = new Circle(profilePicImageView.getFitWidth() / 2, profilePicImageView.getFitHeight() / 2, 80);
        profilePicImageView.setClip(clip);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        colorAdjust.setInput(new BoxBlur());

        profilePicImageView.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                profilePicImageView.setEffect(colorAdjust);
                pictureChangeLabel.setVisible(true);
            } else {
                profilePicImageView.setEffect(null);
                pictureChangeLabel.setVisible(false);
            }
        });

    }

    @Override
    void setGreyZoneDisable(boolean value) {
        greyZone.setDisable(value);
        myAdsButton.setStyle("-fx-background-color: primary");
        myRequestsButton.setStyle("-fx-background-color: primary");
        panelState = 0;
    }

    @FXML
    void signout() {
        byme.signoutUser();
        toggleOffPanel();
    }

    @FXML
    private void displayAccountName() {
        if (byme.isLoggedIn()) {
            currentUser.setText(byme.getCurrentUsersUsername());
        }
    }

    @FXML
    void changeProfilePic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpeg", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                pictureHandler.saveProfilePic(image, byme.getCurrentUsersUsername());
                updateProfilePicImageView();
            } catch (IOException exception) {
                System.out.println("Can't read image: " + selectedFile.getPath());
            }
        }
    }

    void updateProfilePicImageView() {

        if (byme.isLoggedIn()) {
            BufferedImage image = pictureHandler.getProfilePic(byme.getCurrentUsersUsername());
            if (image != null) {
                profilePicImageView.setImage(pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(image, null)));
            } else {    //default profilbild
                setDefaultprofilePic();
            }
        }
    }


    private void setDefaultprofilePic() {
        try {
            profilePicImageView.setImage(SwingFXUtils.toFXImage(ImageIO.read(new File("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "ViewController/images" + File.separatorChar + "defaultProfilePic.png")), null));
        } catch (IOException exception) {
            System.out.println("Can't find default profile picture");
        }
    }

    private void userRatingDisplay() {
        if (byme.isLoggedIn()) {
            String result = String.format("%.2f", byme.getAccountRating(byme.getCurrentUsersUsername()));
            userRating.setText("Omdöme: " + result + "(" + (int) byme.getAccountRatingCount(byme.getCurrentUsersUsername()) + ")");
        }
    }

    /**
     * Used when observers are notified. Updates the content in sidepanel.
     */
    @Override
    public void update() {
        updateProfilePicImageView();
        displayAccountName();
        updateRequests();
        populateMyRequests();
        userRatingDisplay();
    }

    @FXML
    void changeTheme() {
        themeSetter.changeTheme();
    }

    void populateMyAds() {
        if (byme.isLoggedIn()) {
            myAdsFlowPane.getChildren().clear();
            for (Ad currentAd : byme.getAds().values()) {
                if (currentAd.getAccount().equals(byme.getCurrentUsersUsername())) {
                    myAdsFlowPane.getChildren().add(new AdItem(currentAd, detailViewToggler, byme.getAccountRating(currentAd.getAccount())));
                }
            }
        }
    }

    @FXML
    void toggleMyAdsPanel() {
        hideMyRequestsPanel.play();
        myRequestsButton.setStyle("-fx-background-color: primary");
        if (panelState != 1) {
            panelState = 1;
            showMyAdsPanel.play();
            populateMyAds();
            myAdsButton.setStyle("-fx-background-color: primary-dark");
        } else {
            panelState = 0;
            hideMyAdsPanel.play();
            myAdsButton.setStyle("-fx-background-color: primary");
        }
    }

    private void populateMyRequests() {
        if (byme.isLoggedIn()) {
            myReceivedRequestsFlowPane.getChildren().clear();
            mySentRequestsFlowPane.getChildren().clear();
            for (Request request : requests) {
                if (request.getDate().before(Calendar.getInstance().getTime())) { //Check if accepted requests have been completed (due-date)
                    if (request.isAccepted()) {
                        request.setState(RequestState.ACCEPTEDANDDONE);
                    } else if (request.isPending()) {
                        request.setState(RequestState.DECLINED);
                    }
                }
                if (request.getReceiver().equals(byme.getCurrentUsersUsername())) {
                    if (!request.isDeclined()) {
                        myReceivedRequestsFlowPane.getChildren().add(new RequestItem(request, detailViewToggler, true));
                    }
                } else if (request.getSender().equals(byme.getCurrentUsersUsername())) {
                    mySentRequestsFlowPane.getChildren().add(new RequestItem(request, detailViewToggler, false));
                }
            }
        }
    }

    @FXML
    void toggleMyRequestsPanel() {
        hideMyAdsPanel.play();
        myAdsButton.setStyle("-fx-background-color: primary");
        if (panelState != 2) {
            panelState = 2;
            showMyRequestsPanel.play();
            populateMyRequests();
            myRequestsButton.setStyle("-fx-background-color: primary-dark");
        } else {
            panelState = 0;
            hideMyRequestsPanel.play();
            myRequestsButton.setStyle("-fx-background-color: primary");
        }
    }

    private void updateRequests() {
        if (byme.isLoggedIn()) {
            requests.clear();
            for (Ad currentAd : byme.getAds().values()) {
                requests.addAll(currentAd.getRequests());
            }
        }
    }

    @FXML
    void toggleSentRequests() {
        mySentRequestsAnchorPane.setVisible(true);
        myReceivedRequestsAnchorPane.setVisible(false);
        toggleReceviedButton.setStyle("-fx-background-color: primary");
        toggleSentButton.setStyle("-fx-background-color: primary-dark");
    }

    @FXML
    void toggleReceivedRequests() {
        mySentRequestsAnchorPane.setVisible(false);
        myReceivedRequestsAnchorPane.setVisible(true);
        toggleReceviedButton.setStyle("-fx-background-color: primary-dark");
        toggleSentButton.setStyle("-fx-background-color: primary");

    }
}
