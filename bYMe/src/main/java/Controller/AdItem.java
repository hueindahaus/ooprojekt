package Controller;

import Model.Ad;
import Model.Byme;
import Model.IObserver;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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



    }

    public void update(){

        adTitle.setText(this.ad.getTitle());
        adDescription.setText(this.ad.getDescription());
        adLocation.setText(this.ad.getLocation());
        adPrice.setText(Integer.toString(this.ad.getPrice()) + " kr");
        //setTagLabels();
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
}
