package Controller;

import Model.Byme;
import Model.IObserver;
import Services.AccountHandler;
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

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MenuController extends SidePanelController implements IObserver {

    @FXML
    Button categoryButton;

    @FXML
    ImageView profilePicImageView;

    PictureHandler pictureHandler = new PictureHandler();

    private Byme byme = Byme.getInstance(AccountHandler.getInstance());

    public MenuController() {
        super("../signedIn.fxml");
        byme.addObserver(this);

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

        Circle clip = new Circle(profilePicImageView.getFitWidth()/2,profilePicImageView.getFitHeight()/2,80);
        profilePicImageView.setClip(clip);



        profilePicImageView.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    profilePicImageView.setEffect(new BoxBlur());
                } else {
                    profilePicImageView.setEffect(null);
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
    }

    @FXML void signout(){
        byme.signoutUser();
        togglePanel();
    }

    @FXML
    void displayAccountName(){
            currentUser.setText(byme.getCurrentUser().getUsername());
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

        if(byme.getCurrentUser() != null) {
            BufferedImage image = pictureHandler.getProfilePic(byme.getCurrentUser().getUsername());
            if(image != null) {
                profilePicImageView.setImage(pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(image,null)));
            } else {    //default profilbild
                try {
                    profilePicImageView.setImage(SwingFXUtils.toFXImage(ImageIO.read(new File("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "images" + File.separatorChar + "defaultProfilePic.png")),null));
                } catch(IOException exception){
                    System.out.println("Can't find default profile picture");
                }
            }
        }
    }


    public void update(){
        updateProfilePicImageView();
        displayAccountName();
    }
}
