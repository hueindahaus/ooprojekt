package Controller;

import Model.Byme;
import Services.AccountHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class AdListController extends AnchorPane {

    @FXML
    private ImageView adImage;
    @FXML
    private Label adTitle;
    @FXML
    private Label adLocation;
    @FXML
    private Label adPrice;
    @FXML
    private TextArea adDescription;
    @FXML
    private ImageView adImageDetail;
    @FXML
    private Label adTitleDetail;
    @FXML
    private Label adLocationDetail;
    @FXML
    private Label adPriceDetail;
    @FXML
    private TextArea adDescriptionDetail;
    @FXML
    private FlowPane adsListFlowPane;


    private Byme bYMe = Byme.getInstance(AccountHandler.getInstance());

    public AdListController(Byme bYMe) {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ads.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.bYMe = bYMe;


    }




}
