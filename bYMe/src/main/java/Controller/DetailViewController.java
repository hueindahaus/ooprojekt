package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class DetailViewController extends AnchorPane {


    DetailViewToggler detailViewToggler;

    @FXML
    Button deleteButton;

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



    @FXML
    void openDetailView(){
        detailViewToggler.toggleDetailView(true);
    }

    @FXML
    void closeDetailView(){
        detailViewToggler.toggleDetailView(false);
    }
}
