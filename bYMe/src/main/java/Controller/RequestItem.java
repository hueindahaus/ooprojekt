package Controller;

import Model.Ad;
import Model.IObserver;
import Model.Request;
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

public class RequestItem extends AnchorPane{

    @FXML
    private Label requestAd;
    @FXML
    private Label requestMessage;
    @FXML
    private Label requestSender;

    PictureHandler pictureHandler = new PictureHandler();

    DetailViewToggler detailViewToggler;
    private Request request;

    public RequestItem(Request request, DetailViewToggler detailViewToggler)  {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../requests.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.request = request;

        requestAd.setText(request.getAd());
        requestMessage.setText(request.getMessage());
        requestSender.setText(request.getSender());


        this.detailViewToggler = detailViewToggler;
    }
}
