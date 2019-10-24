package ViewController;

import Model.Ad;
import Model.IObserver;
import Services.PictureHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class AdItem extends AnchorPane implements IObserver {

    @FXML
    private ImageView adImage;
    @FXML
    private Label adTitle;
    @FXML
    private Label adLocation;
    @FXML
    private Label adPrice;
    @FXML
    private Label adDescription;
    @FXML
    private Label adAccount;

    @FXML
    private Label tag1Label;
    @FXML
    private Label tag2Label;
    @FXML
    private Label tag3Label;
    @FXML
    private Label tag4Label;
    @FXML
    private Label tag5Label;

    private PictureHandler pictureHandler = PictureHandler.getInstance();

    private Ad ad;

    AdItem(Ad ad, DetailViewToggler detailViewToggler, double rating) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/ads.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.ad = ad;

        adTitle.setText(ad.getTitle());
        adDescription.setText(ad.getDescription());
        adLocation.setText(ad.getLocation());
        adPrice.setText(Integer.toString(ad.getPrice()));
        String result = String.format("%.2f", rating);
        adAccount.setText(ad.getAccount() + " (" + result + ")");


        this.setOnMouseClicked(event -> detailViewToggler.toggleDetailView(true, ad));

        Rectangle clip = new Rectangle(adImage.getFitWidth(), adImage.getFitHeight());
        clip.setArcHeight(20);
        clip.setArcWidth(20);
        adImage.setClip(clip);


        updatePicture();
    }

    @Override
    public void update() {
        adTitle.setText(this.ad.getTitle());
        adDescription.setText(this.ad.getDescription());
        adLocation.setText(this.ad.getLocation());
        adPrice.setText(this.ad.getPrice() + " kr");
        updatePicture();
    }


    /**
     * Updates the ImageView in an ad and displays only the primary picture(first one in the ArrayList).
     **/
    private void updatePicture() {
        if (pictureHandler.getAdPictures(ad.getAdId()).size() > 0) {
            Image image = pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(pictureHandler.getAdPictures(ad.getAdId()).get(0), null));
            adImage.setImage(image);
        }
    }
}
