package Controller;

import Model.Byme;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sun.applet.Main;

import java.io.IOException;

public class tagItem extends AnchorPane {

    @FXML
    private Label tagName;
    @FXML
    private Label tagNo;

    public tagItem(String tagName, int tagNo) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../tags.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.tagName.setText(tagName);
        this.tagNo.setText(tagNo + "st");
    }
}
