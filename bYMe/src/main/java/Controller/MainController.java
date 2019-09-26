package Controller;

import Model.Byme;
import Services.AccountHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, PanelToggler{

    //FX grejer, borde kanske flyttas

    @FXML
    AnchorPane root;
    @FXML
    Button primaryButton;

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



}


