package ViewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * This class is responsible for the FXML-file:tags containing all of the fxml-elements needed to display a tag.
 * Displays the name of the tag and the number of occurrences of that tag.
 * @author Johan Gottlander, Joel Jönsson
 *
 * Uses: TagSearcher.
 * Used by: MainController.
 */
class TagItem extends AnchorPane {

    @FXML
    private Label tagName;
    @FXML
    private Label tagNo;

    private TagSearcher tagSearcher;

    TagItem(String tagName, int tagNo, boolean isActive, TagSearcher tagSearcher) {
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

        if (isActive) {
            this.setStyle("-fx-background-color: primary-dark");
        }
    }

    @FXML
    void searchTags() {
        tagSearcher.searchTags(tagName.getText());
    }

}
