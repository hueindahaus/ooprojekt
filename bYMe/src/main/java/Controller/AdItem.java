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

import java.io.IOException;

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

    PictureHandler pictureHandler = new PictureHandler();

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

        ad.observers.add(this);

    }

    public void update(){

        adTitle.setText(this.ad.getTitle());
        adDescription.setText(this.ad.getDescription());
        adLocation.setText(this.ad.getLocation());
        adPrice.setText(Integer.toString(this.ad.getPrice()) + " kr");
        if(pictureHandler.getAdPictures(ad.getAdId()).size() > 0) {
            Image image = pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(pictureHandler.getAdPictures(ad.getAdId()).get(0), null));
            adImage.setImage(image);
        }
    }
}
