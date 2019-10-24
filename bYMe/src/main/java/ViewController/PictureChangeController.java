package ViewController;

import Services.PictureHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class PictureChangeController extends AnchorPane {


    @FXML
    private
    ImageView imageChanger1;
    @FXML
    private
    ImageView imageChanger2;
    @FXML
    private
    ImageView imageChanger3;
    @FXML
    private
    ImageView imageChanger4;
    @FXML
    private
    ImageView imageChanger5;

    ColorAdjust pictureEffect = new ColorAdjust();

    private List<BufferedImage> images = new ArrayList<>();

    private PictureHandler pictureHandler = PictureHandler.getInstance();

    private ImageViewUpdater imageViewUpdater;


    PictureChangeController(ImageViewUpdater imageViewUpdater){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/pictureChanger.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }

        setHoverEffectOnImageView(imageChanger1);
        setHoverEffectOnImageView(imageChanger2);
        setHoverEffectOnImageView(imageChanger3);
        setHoverEffectOnImageView(imageChanger4);
        setHoverEffectOnImageView(imageChanger5);

        pictureEffect.setBrightness(-0.5);
        pictureEffect.setInput(new BoxBlur());

        this.imageViewUpdater = imageViewUpdater;

        this.setLayoutX(220);
        this.setLayoutY(150);

    }

    void update(){
        try {
            Image defaultImage = SwingFXUtils.toFXImage(ImageIO.read(new File("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "ViewController/images" + File.separatorChar + "insert_photo.png")), null);
            imageChanger1.setImage(defaultImage);
            imageChanger2.setImage(defaultImage);
            imageChanger3.setImage(defaultImage);
            imageChanger4.setImage(defaultImage);
            imageChanger5.setImage(defaultImage);
        }catch(IOException e){
            e.printStackTrace();
        }
        setImageIfPossible(imageChanger1,0,images);
        setImageIfPossible(imageChanger2,1,images);
        setImageIfPossible(imageChanger3,2,images);
        setImageIfPossible(imageChanger4,3,images);
        setImageIfPossible(imageChanger5,4,images);
    }


    private void setImageIfPossible(ImageView imageView, int num, List<BufferedImage> list){
        if(list.size() > num){
            imageView.setImage(pictureHandler.makeSquareImage(SwingFXUtils.toFXImage(list.get(num),null)));
        }
    }


    @FXML
    void changeAdPic1(){
        changeAdPic(0);
    }

    @FXML
    void changeAdPic2(){
        changeAdPic(1);
    }

    @FXML
    void changeAdPic3(){
        changeAdPic(2);
    }

    @FXML
    void changeAdPic4(){
        changeAdPic(3);
    }

    @FXML
    void changeAdPic5(){
        changeAdPic(4);
    }

    private void changeAdPic(int index){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpeg", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);



        if(selectedFile != null){
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                if(images.size() - 1 >= index) {
                    images.add(index, image);
                    images.remove(index+1);
                } else {
                    images.add(image);
                }
                if(imageViewUpdater != null) {
                    imageViewUpdater.updateImageViews();
                }
                update();
            } catch(IOException exception){
                System.out.println("Can't read image: " + selectedFile.getPath());
            }
        }
    }


    private void setHoverEffectOnImageView(ImageView imageView){
        imageView.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                imageView.setEffect(pictureEffect);
            } else {
                imageView.setEffect(null);
            }
        });
    }

    List<BufferedImage> getImages(){
        return images;
    }

    void setImages(List<BufferedImage> images){
        this.images = images;
    }


    void resetImageList(){
        images.clear();
    }
}
