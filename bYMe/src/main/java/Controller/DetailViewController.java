package Controller;

import Model.Ad;
import Model.Byme;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DetailViewController extends AnchorPane {


    DetailViewToggler detailViewToggler;

    Byme byme = Byme.getInstance();

    @FXML
    Button deleteButton;
    @FXML
    Label adTitle;
    @FXML
    Label adLocation;
    @FXML
    Label adDescription;
    @FXML
    Label adUser;
    @FXML
    Label adPrice;
    @FXML
    AnchorPane greyZone;


    Ad ad;

    public DetailViewController(DetailViewToggler detailViewToggler){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../detailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }


        this.detailViewToggler = detailViewToggler;
    }



    void setAd(Ad ad){
        if (ad != null) {
            this.ad = ad;
            adTitle.setText(ad.getTitle());
            adDescription.setText(ad.getDescription());
            adLocation.setText(ad.getLocation());
            adUser.setText(ad.getAccount());
            adPrice.setText(String.valueOf(ad.getPrice()));
        }
    }

    @FXML
    void closeDetailView(){
        detailViewToggler.toggleDetailView(false, ad);
    }


    @FXML
    void removeAd(){
        byme.removeAd(ad.getAdId());
        closeDetailView();
    }



    void showUserButtons(){
        if(isUsersAd()){
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }
    }


    private boolean isUsersAd(){
        if (byme.getCurrentUser() != null){
            if (byme.getCurrentUser().getUsername().equals(ad.getAccount())){
                return true;
            }
        }
        return false;
    }

}
