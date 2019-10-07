package Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdItem extends AnchorPane {

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

    DetailViewToggler detailViewToggler;

    public AdItem(String title, String location, int price, String description, String account, DetailViewToggler detailViewToggler)  {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ads.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        adTitle.setText(title);
        adDescription.setText(description);
        adLocation.setText(location);
        adPrice.setText(Integer.toString(price));
        adAccount.setText(account);

        this.detailViewToggler = detailViewToggler;



        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                detailViewToggler.toggleDetailView(true);
            }
        });

    }
}
