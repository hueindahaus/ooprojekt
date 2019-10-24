package ViewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

class TagItem extends AnchorPane {

    @FXML
    private Label tagName;
    @FXML
    private Label tagNo;

    private TagSearcher tagSearcher;

    TagItem(String tagName, int tagNo, boolean isActive, TagSearcher tagSearcher){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/tags.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.tagName.setText(tagName);
        this.tagNo.setText(tagNo + "st");
        this.tagSearcher = tagSearcher;

        if(isActive){
            this.setStyle("-fx-background-color: primary-dark");
        }
    }

    @FXML
    void searchTags(){
        tagSearcher.searchTags(tagName.getText());
    }

}
