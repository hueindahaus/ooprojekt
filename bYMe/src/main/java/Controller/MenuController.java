package Controller;


import Model.Ad;
import Model.Byme;
import Model.IObserver;
import Model.Request;
import Services.PictureHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.HashMap;
import java.util.Map;


public class MenuController extends SidePanelController implements IObserver {

    private ThemeSetter themeSetter;

    @FXML
    Button categoryButton;

    @FXML
    ImageView profilePicImageView;

    @FXML
    Label pictureChangeLabel;

    @FXML
    AnchorPane myAdsPanel;

    @FXML
    FlowPane myAdsFlowPane;

    @FXML
    Button myAdsButton;

    @FXML
    AnchorPane myRequestsPanel;

    @FXML
    FlowPane myReceivedRequestsFlowPane;

    @FXML
    FlowPane mySentRequestsFlowPane;

    @FXML
    Button myRequestsButton;

    PictureHandler pictureHandler = new PictureHandler();

    Timeline showMyAdsPanel = new Timeline();

    Timeline hideMyAdsPanel = new Timeline();

    Timeline showMyRequestsPanel = new Timeline();

    Timeline hideMyRequestsPanel = new Timeline();

    private int panelState = 0;

    private Byme byme = Byme.getInstance(null,null);

    ArrayList<Request> requests = new ArrayList<>();

    private DetailViewToggler detailViewToggler;

    MenuController(ThemeSetter themeSetter, DetailViewToggler detailViewToggler) {
        super("../signedIn.fxml");
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

        Circle clip = new Circle(profilePicImageView.getFitWidth()/2,profilePicImageView.getFitHeight()/2,80);
        profilePicImageView.setClip(clip);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        colorAdjust.setInput(new BoxBlur());

        profilePicImageView.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    profilePicImageView.setEffect(colorAdjust);
                    pictureChangeLabel.setVisible(true);
                } else {
                    profilePicImageView.setEffect(null);
                    pictureChangeLabel.setVisible(false);
                }
            }
        });

    }
    @FXML AnchorPane menuPanel;
    @FXML AnchorPane greyZone;

    @FXML
    Label currentUser;



    void setGreyZoneDisable(boolean value){
        greyZone.setDisable(value);
        myAdsButton.setStyle("-fx-background-color: primary");
        myRequestsButton.setStyle("-fx-background-color: primary");
        panelState = 0;
    }

    @FXML void signout(){
        byme.signoutUser();
        toggleOffPanel();
    }

    @FXML
    private void displayAccountName(){
        if(byme.getCurrentUser() != null) {
            currentUser.setText(byme.getCurrentUser().getUsername());
        }
    }

    @FXML
    void changeProfilePic(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpeg", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                pictureHandler.saveProfilePic(image ,byme.getCurrentUser().getUsername());
                updateProfilePicImageView();
            } catch(IOException exception){
                System.out.println("Can't read image: " + selectedFile.getPath());
            }
        }
    }

    void updateProfilePicImageView(){

        if(byme.isLoggedIn()) {
            BufferedImage image = pictureHandler.getProfilePic(byme.getCurrentUser().getUsername());
            if(image != null) {
                profilePicImageView.setImage(pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(image,null)));
            } else {    //default profilbild
                setDefaultprofilePic();
            }
        }
    }


    private void setDefaultprofilePic(){
        try {
            profilePicImageView.setImage(SwingFXUtils.toFXImage(ImageIO.read(new File("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "images" + File.separatorChar + "defaultProfilePic.png")),null));
        } catch(IOException exception){
            System.out.println("Can't find default profile picture");
        }
    }

    public void update(){
        updateProfilePicImageView();
        displayAccountName();
        updateRequests();
    }

    @FXML void changeTheme(){
        themeSetter.changeTheme();
    }

    public void populateMyAds(){
        HashMap<String, Ad> ads = byme.getAds();
        if(byme.isLoggedIn()) {
            myAdsFlowPane.getChildren().clear();
            for (Map.Entry ad : ads.entrySet()) {
                Ad currentAd = (Ad) ad.getValue();
                if (currentAd.getAccount().equals(byme.getCurrentUser().getUsername())) {
                    myAdsFlowPane.getChildren().add(new AdItem(currentAd, detailViewToggler));
                }
            }
        }
    }

    @FXML void toggleMyAdsPanel(){
        hideMyRequestsPanel.play();
        myRequestsButton.setStyle("-fx-background-color: primary");
        if(panelState != 1) {
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

    public void populateMyRequests(){
        if(byme.isLoggedIn()) {
            myReceivedRequestsFlowPane.getChildren().clear();
            mySentRequestsFlowPane.getChildren().clear();
            for (Request request : requests) {
                if(request.getState() == 1) { //Check if accepted requests have been completed (due-date)
                    if (request.getDate().before(Calendar.getInstance().getTime())) {
                        request.setState(3);
                    }
                }
                if (request.getReceiver().equals(byme.getCurrentUser().getUsername())) {
                    myReceivedRequestsFlowPane.getChildren().add(new RequestItem(request, detailViewToggler, true));
                } else if (request.getSender().equals(byme.getCurrentUser().getUsername())) {
                    mySentRequestsFlowPane.getChildren().add(new RequestItem(request, detailViewToggler, false));
                }
            }
        }
    }

    @FXML void toggleMyRequestsPanel(){
        hideMyAdsPanel.play();
        myAdsButton.setStyle("-fx-background-color: primary");
        if(panelState != 2) {
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

    private void updateRequests(){
        if(byme.isLoggedIn()) {
            HashMap<String, Ad> ads = byme.getAds();
            requests.clear();
            for (Map.Entry ad : ads.entrySet()) {
                Ad currentAd = (Ad) ad.getValue();
                requests.addAll(currentAd.getRequests());
            }
        }
    }
}
