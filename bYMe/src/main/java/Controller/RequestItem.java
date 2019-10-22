package Controller;

import Model.Ad;
import Model.Byme;
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

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RequestItem extends AnchorPane {

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
    @FXML
    private Button buttonReview;
    @FXML
    private AnchorPane reviewPane;

    PictureHandler pictureHandler = new PictureHandler();

    DetailViewToggler detailViewToggler;

    private Request request;

    private Byme byme = Byme.getInstance(null, null);

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
        reviewPane.setVisible(false);
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
            buttonReview.setVisible(false);
            buttonAccept.setDisable(true);
            buttonDecline.setDisable(true);
            if (request.getState() == 1) {
                requestAnchorPane.setStyle("-fx-background-color: greenyellow");
            } else if (request.getState() == 2) {
                requestAnchorPane.setStyle("-fx-background-color: IndianRed");
            } else {
                requestAnchorPane.setStyle("-fx-background-color: Gold");
                buttonReview.setVisible(true);
            }
        }
    }
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;
    @FXML
    private ImageView star3;
    @FXML
    private ImageView star4;
    @FXML
    private ImageView star5;


    Image yellowStar = new Image("File:"+"src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "images" + File.separatorChar + "star.png");
    Image hollowStar = new Image("File:"+"src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "images" + File.separatorChar + "hollowStar.png");

    @FXML
    private void toggleStar1Enter(){
        star1.setImage(yellowStar);
    }
    @FXML
    private void toggleStar2Enter(){
        star1.setImage(yellowStar);
        star2.setImage(yellowStar);
    }
    @FXML
    private void toggleStar3Enter(){
        star1.setImage(yellowStar);
        star2.setImage(yellowStar);
        star3.setImage(yellowStar);
    }
    @FXML
    private void toggleStar4Enter(){
        star1.setImage(yellowStar);
        star2.setImage(yellowStar);
        star3.setImage(yellowStar);
        star4.setImage(yellowStar);
    }
    @FXML
    private void toggleStar5Enter(){
        star1.setImage(yellowStar);
        star2.setImage(yellowStar);
        star3.setImage(yellowStar);
        star4.setImage(yellowStar);
        star5.setImage(yellowStar);
    }
    @FXML
    private void toggleStarExit(){
        star1.setImage(hollowStar);
        star2.setImage(hollowStar);
        star3.setImage(hollowStar);
        star4.setImage(hollowStar);
        star5.setImage(hollowStar);
    }

    @FXML
    private void reviewPaneToggle(){
        if(reviewPane.isVisible()) {
            reviewPane.setVisible(false);
        }else{
            reviewPane.setVisible(true);
        }
    }
    @FXML
    private void reviewAd(){
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 1);
        removeRequest();
    }

    @FXML
    private void reviewAd2(){
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 2);
        removeRequest();
    }

    @FXML
    private void reviewAd3(){
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 3);
        removeRequest();
    }

    @FXML
    private void reviewAd4(){
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 4);
        removeRequest();
    }

    @FXML
    private void reviewAd5(){
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 5);
        removeRequest();
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
        byme.removeRequest(request);
    }
}
