package Controller;

import Model.Ad;
import Model.IObserver;
import Services.PictureHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

public class AdItem extends AnchorPane implements IObserver{

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

    PictureHandler pictureHandler = PictureHandler.getInstance();

    DetailViewToggler detailViewToggler;
    private Ad ad;

    public AdItem(Ad ad, DetailViewToggler detailViewToggler)  {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ads.fxml"));
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
        adAccount.setText(ad.getAccount());


        this.detailViewToggler = detailViewToggler;

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                detailViewToggler.toggleDetailView(true, ad);
            }
        });

        Rectangle clip = new Rectangle(adImage.getFitWidth(), adImage.getFitHeight());
        clip.setArcHeight(20);
        clip.setArcWidth(20);
        adImage.setClip(clip);


        updatePicture();
    }

    public void update(){
        adTitle.setText(this.ad.getTitle());
        adDescription.setText(this.ad.getDescription());
        adLocation.setText(this.ad.getLocation());
        adPrice.setText(Integer.toString(this.ad.getPrice()) + " kr");
       // setTagLabels();
        updatePicture();
    }

    /*
    void setTagLabels(){

        if (!(ad.getTagsList().size() == 0)){
            ArrayList<String> tags = ad.getTagsList();
            tag1Label.setText(tags.get(0));
            tag2Label.setText(tags.get(1));
            tag3Label.setText(tags.get(2));
            tag4Label.setText(tags.get(3));
            tag5Label.setText(tags.get(4));
        }

    }
*/

    private void updatePicture(){
        if(pictureHandler.getAdPictures(ad.getAdId()).size() > 0) {
            Image image = pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(pictureHandler.getAdPictures(ad.getAdId()).get(0), null));
            adImage.setImage(image);
        }
    }
}
