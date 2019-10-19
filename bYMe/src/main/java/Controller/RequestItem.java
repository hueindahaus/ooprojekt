package Controller;

import Model.Ad;
import Model.IObserver;
import Model.Request;
import Services.PictureHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RequestItem extends AnchorPane{

    @FXML
    private AnchorPane requestAnchorPane;
    @FXML
    private Label requestAd;
    @FXML
    private Label requestMessage;
    @FXML
    private Label requestSender;
    @FXML
    private Label requestReceiver;
    @FXML
    private Label requestDate;
    @FXML
    private Button buttonAccept ;
    @FXML
    private Button buttonDecline;
    @FXML
    private Button buttonRemove;

    PictureHandler pictureHandler = new PictureHandler();

    DetailViewToggler detailViewToggler;

    private Request request;

    public RequestItem(Request request, DetailViewToggler detailViewToggler, boolean userIsRecipient)  {
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
        requestSender.setText("From: " + request.getSender());
        requestDate.setText(request.getDateString());
        requestReceiver.setText("To: " + request.getReceiver());

        this.detailViewToggler = detailViewToggler;

        buttonAccept.setVisible(userIsRecipient);
        buttonDecline.setVisible(userIsRecipient);
        if(!userIsRecipient && request.getState() != 1){ // Can't remove accepted requests
            buttonRemove.setVisible(true);
        } else {
            buttonRemove.setVisible(false);
        }

        if(request.getState() != 0) {
            buttonAccept.setDisable(true);
            buttonDecline.setDisable(true);
            if (request.getState() == 1) {
                requestAnchorPane.setStyle("-fx-background-color: greenyellow");
            } else if (request.getState() == 2) {
                requestAnchorPane.setStyle("-fx-background-color: IndianRed");
            } else {
                requestAnchorPane.setStyle("-fx-background-color: Gold");
            }
        }
    }

    @FXML
    private void acceptRequest(){
        request.setState(1);
        requestAnchorPane.setStyle("-fx-background-color: greenyellow");

    }

    @FXML
    private void declineRequest(){
        request.setState(2);
        requestAnchorPane.setStyle("-fx-background-color: IndianRed");
    }

    @FXML
    private void removeRequest(){
        request.remove();
    }
}
