package Services;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PictureHandler {

    private String getAdPictureDirPath(String id){
        return "adPictures" + File.separatorChar + id;
    }
    private String getProfilePictureFilePath(String username){
        return "profile_pics" + File.separatorChar + username + ".jpg";
    }
    private String getAdPicturePath(int index, String id){
        return getAdPictureDirPath(id) + File.separatorChar + index + ".jpg";
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

    public ArrayList<BufferedImage> getAdPictures(String id){
        ArrayList<BufferedImage> images =new ArrayList<>();
        File folder = new File(getAdPictureDirPath(id));
        if(folder.isDirectory() && folder.listFiles().length > 0) {
            for (File file : folder.listFiles()) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    images.add(image);
                } catch (IOException expection) {
                    System.out.println("Could not associate ad: " + id + " with a picture with path: " + file.getName());
                }
            }
            return images;
        } else {
            System.out.println("folder doesn't exist");
            return null;
        }
    }


    //Saves pictures in new directory(If it does not exists
    public void saveAdPictures(String adID,ArrayList<BufferedImage> images){

        File theDir = new File(getAdPictureDirPath(adID));
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getName());
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                se.printStackTrace();
            }
            if(result) {
                System.out.println("DIR created");
            }
        }
        int index = 1;
        for (BufferedImage image : images) {

            try {
                ImageIO.write(image, "jpg", new File(getAdPicturePath(index, adID)));
            } catch (IOException exception) {
                System.out.println("Could not write: " + getAdPicturePath(index, adID));
            }
            index++;
        }
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
