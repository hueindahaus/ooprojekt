package Controller;

import Model.Byme;
import Services.AccountHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, PanelToggler{

    //FX grejer, borde kanske flyttas

    @FXML
    AnchorPane root;
    @FXML
    Button primaryButton;
    @FXML
    ImageView exitButtonImage;

    @FXML
    private AnchorPane detailPane;
    @FXML
    private AnchorPane adsPane;

    private LoginController loginController;
    private MenuController menuController;

    private Byme byme = Byme.getInstance(AccountHandler.getInstance());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginController = new LoginController(this);
        root.getChildren().add(loginController);
        menuController = new MenuController();
        root.getChildren().add(menuController);

        setTheme(new Theme("#2c3e50","#34495e","#7f8c8d","#95a5a6","#f1c40f","#f39c12","#f1c40f","#f39c12"));
        //kommentera bort ifall ni ej vill ha detta teamat
    }


    @FXML
    public void togglePanel() {
        if (byme.getCurrentUser() == null) {
            loginController.togglePanel();
        }
        else{
            menuController.togglePanel();
        }
    }

    public void togglePanel(boolean login){
        if(login) {
            loginController.toggleOffPanel();
            menuController.toggleOnPanel();
        }
    }

    private void setTheme(Theme theme){
        root.setStyle(
                "main:"+ theme.main+";"+"\n"+
                "main-dark:" + theme.main_dark+";"+"\n"+
                "primary:"+theme.primary+";" + "\n"+
                "primary-dark:"+theme.primary_dark+";"+"\n"+
                "secondary:"+theme.secondary+";"+"\n"+
                "secondary-dark:"+theme.secondary_dark+";"+"\n"+
                "tertiary:"+theme.tertiary+";"+"\n"+
                "tertiary-dark:"+theme.tertiary_dark+";");
    }

    @FXML
    private void openDetailView(){
        detailPane.toFront();
        adsPane.toBack();
    }
    @FXML
    private void closeDetailView(){
        detailPane.toBack();
        adsPane.toFront();
    }

    @FXML
    public void closeButtonMouseEntered(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "main/images/icon_close_hover.png")));
    }

    @FXML
    public void closeButtonMousePressed(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "main/images/icon_close_pressed.png")));
    }

    @FXML
    public void closeButtonMouseExited(){
        exitButtonImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "main/images/icon_close.png")));
    }

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }



}


