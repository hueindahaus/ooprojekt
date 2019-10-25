package ViewController;

import Model.Byme;
import Model.Request;
import Model.RequestState;
import Services.PictureHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for displaying and setting ratings on the sender(the buyer in this case) of the request.
 * The requests also have a specific color to match their current state, as an example does the "request item" in the
 * users request list turn green when they "Accept" a request from a buyer.
 * @author Milos Bastajic, Joel Jönsson
 *
 * Uses: Request, Byme.
 * Used by: MenuController.
 */
public class RequestItem extends AnchorPane {

    PictureHandler pictureHandler = PictureHandler.getInstance();

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
    private Button buttonAccept;
    @FXML
    private Button buttonDecline;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonReview;
    @FXML
    private AnchorPane reviewPane;
    private Request request;

    private Byme byme = Byme.getInstance(null, null);
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
    private Image yellowStar = new Image("File:" + "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "ViewController/images" + File.separatorChar + "star.png");
    private Image hollowStar = new Image("File:" + "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "ViewController/images" + File.separatorChar + "hollowStar.png");

    public RequestItem(Request request, DetailViewToggler detailViewToggler, boolean userIsRecipient) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/requests.fxml"));
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
        requestSender.setText("From: " + request.getSender() + " (" + byme.getAccountRating(byme.getCurrentUsersUsername()) + ")");
        requestDate.setText(request.getDateString());
        requestReceiver.setText("To: " + request.getReceiver());
        
        buttonAccept.setVisible(userIsRecipient);
        buttonDecline.setVisible(userIsRecipient);
        if (!userIsRecipient && !request.isAccepted()) { // Can't remove accepted requests
            buttonRemove.setVisible(true);
        } else {
            buttonRemove.setVisible(false);
        }

        if (!request.isPending()) {
            buttonReview.setVisible(false);
            buttonAccept.setDisable(true);
            buttonDecline.setDisable(true);
            if (request.isAccepted()) {
                requestAnchorPane.setStyle("-fx-background-color: greenyellow");
            } else if (request.isDeclined()) {
                requestAnchorPane.setStyle("-fx-background-color: IndianRed");
            } else {
                requestAnchorPane.setStyle("-fx-background-color: Gold");
                buttonReview.setVisible(!userIsRecipient);
            }
        }
    }

    /**
     * Sets the right amount yellowStar in response to the current star the user is hovering.
     **/
    @FXML
    private void toggleStar1Enter() {
        star1.setImage(yellowStar);
    }

    @FXML
    private void toggleStar2Enter() {
        star1.setImage(yellowStar);
        star2.setImage(yellowStar);
    }

    @FXML
    private void toggleStar3Enter() {
        star1.setImage(yellowStar);
        star2.setImage(yellowStar);
        star3.setImage(yellowStar);
    }

    @FXML
    private void toggleStar4Enter() {
        star1.setImage(yellowStar);
        star2.setImage(yellowStar);
        star3.setImage(yellowStar);
        star4.setImage(yellowStar);
    }

    @FXML
    private void toggleStar5Enter() {
        star1.setImage(yellowStar);
        star2.setImage(yellowStar);
        star3.setImage(yellowStar);
        star4.setImage(yellowStar);
        star5.setImage(yellowStar);
    }

    /**
     * Sets all rating stars to hollowStar image when the user stops hovering a rating star.
     **/
    @FXML
    private void toggleStarExit() {
        star1.setImage(hollowStar);
        star2.setImage(hollowStar);
        star3.setImage(hollowStar);
        star4.setImage(hollowStar);
        star5.setImage(hollowStar);
    }

    @FXML
    private void reviewPaneToggle() {
        if (reviewPane.isVisible()) {
            reviewPane.setVisible(false);
        } else {
            reviewPane.setVisible(true);
        }
    }

    /**
     * Sends a rating from 1-5 to the accounts rating system
     **/
    @FXML
    private void reviewAd() {
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 1);
        removeRequest();
    }

    @FXML
    private void reviewAd2() {
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 2);
        removeRequest();
    }

    @FXML
    private void reviewAd3() {
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 3);
        removeRequest();
    }

    @FXML
    private void reviewAd4() {
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 4);
        removeRequest();
    }

    @FXML
    private void reviewAd5() {
        reviewPaneToggle();
        byme.reviewAccount(request.getReceiver(), 5);
        removeRequest();
    }

    /**
     * If a user accepts a request then the request item changes color to a green color
     **/
    @FXML
    private void acceptRequest() {
        request.setState(RequestState.ACCEPTED);
        requestAnchorPane.setStyle("-fx-background-color: greenyellow");

    }

    /**
     * If a user declines a request then the request item changes color to a red color
     **/
    @FXML
    private void declineRequest() {
        request.setState(RequestState.DECLINED);
        requestAnchorPane.setStyle("-fx-background-color: IndianRed");
    }

    /**
     * Removes a request item from the request item ArrayList
     **/
    @FXML
    private void removeRequest() {
        byme.removeRequest(request);
    }
}
