package Services;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PictureHandler {

    private String getProfilePictureFilePath(String username){
        return "profile_pics" + File.separatorChar + username + ".jpg";
    }


    public BufferedImage getProfilePic(String username){
        try{
            BufferedImage image = ImageIO.read(new File(getProfilePictureFilePath(username)));
            return image;
        } catch(IOException expection){
            System.out.println("Could not associate account: " + username + " with a profile picture with path: " + getProfilePictureFilePath(username));
        }
        return null;
    }


    public void saveProfilePic(BufferedImage file, String username){

        try{
            ImageIO.write(file, "jpg", new File(getProfilePictureFilePath(username )));
        } catch(IOException exception){
            System.out.println("Could not write: " + getProfilePictureFilePath(username));
        }
    }


    public Image makeSquareImage(Image image){
        int x= 0;
        int y=0;
        int width=0;
        int height=0;

        if (image.getWidth() > image.getHeight()){
            width = (int)image.getHeight();
            height = (int)image.getHeight();
            x=(int)(image.getWidth()-width)/2;
            y=0;
        } else if(image.getHeight() > image.getWidth()){
            width = (int)image.getWidth();
            height = (int)image.getWidth();
            x = 0;
            y = (int)(image.getHeight()-height)/2;
        } else {
            return image;
        }
        return new WritableImage(image.getPixelReader(),x,y,width,height);
    }

}
