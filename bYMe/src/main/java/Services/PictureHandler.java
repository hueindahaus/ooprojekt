package Services;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that handles reading/writing of images. Is responisble of both profile-pictures and pictures of an ad.
 * It handles them both separately and different since every user only have one profile-picture while an ad can have up to five.
 * It is a singleton class since there are no points in having multiple objects of this class.
 * @author Alexander Huang, Milos Bastajic
 */
public final class PictureHandler {

    private static PictureHandler singleton;

    private PictureHandler() {
    }

    /**
     * Singleton getInstance() method.
     * @return the singleton object.
     */
    public static PictureHandler getInstance() {
        if (singleton == null) {
            singleton = new PictureHandler();
        }
        return singleton;
    }


    private String getAdPictureDirPath(String id) {
        return "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "Services" + File.separatorChar + "ad_pictures" + File.separatorChar + id;
    }

    private String getProfilePictureFilePath(String username) {
        return "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "Services" + File.separatorChar + " profile_pics" + File.separatorChar + username + ".jpg";
    }

    private String getAdPicturePath(int index, String id) {
        return getAdPictureDirPath(id) + File.separatorChar + index + ".jpg";
    }


    /**
     * Used to get the profile-picture that is linked to the given username.
     * @param username The username that you want the profile-picture of.
     * @return The image(BufferedImage) that is returned with the given username.
     */
    public BufferedImage getProfilePic(String username) {
        try {
            return ImageIO.read(new File(getProfilePictureFilePath(username)));
        } catch (IOException expection) {
            System.out.println("Could not associate account: " + username + " with a profile picture with path: " + getProfilePictureFilePath(username));
        }
        return null;
    }

    /**
     * Used to get the ad-pictures that is linked to the given ad-id.
     * @param adId The id of the ad that you want the pictures of.
     * @return The list of images(BufferedImage) that is returned with the given ad-id.
     */
    public List<BufferedImage> getAdPictures(String adId) {
        File folder = new File(getAdPictureDirPath(adId));
        ArrayList<BufferedImage> images = new ArrayList<>();
        if (folder.isDirectory() && folder.listFiles().length > 0) {
            for (File file : folder.listFiles()) {
                try {
                    images.add(ImageIO.read(file));
                } catch (IOException expection) {
                    System.out.println("Could not associate ad: " + adId + " with a picture with path: " + file.getName());
                }
            }
        }
        return images;
    }


    /**
     * Method that saves the images in the argument in a map that is named with the given ad-id.
     * It creates a directory if there is no directory with the name of an ad-id.
     * @param adID Name of the directory that should hold the images.
     * @param images List of images that shall be saved.
     */
    public void saveAdPictures(String adID, List<BufferedImage> images) {

        File theDir = new File(getAdPictureDirPath(adID));
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getName());
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                se.printStackTrace();
            }
            if (result) {
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


    /**
     * Method that writes a profile-picture with a given username.
     * @param file The image that is saved.
     * @param username The name of the picture that is saved.
     */
    public void saveProfilePic(BufferedImage file, String username) {

        try {
            ImageIO.write(file, "jpg", new File(getProfilePictureFilePath(username)));
        } catch (IOException exception) {
            System.out.println("Could not write: " + getProfilePictureFilePath(username));
        }
    }

    /**
     * Method that removes a folder with ad-pictures. If an ad gets removed, the pictures that is linked with the same ad gets deleted.
     * @param adId The id of the ad that gets deleted. (Also the name of the folder that is deleted)
     */
    public void removePictureFolder(String adId) {


        File directory = new File(getAdPictureDirPath(adId));

        if (directory.exists()) {
            System.out.println("Removing picture directory for " + adId);
            for (File file : directory.listFiles()) {
                file.delete();
            }
            System.out.println("Directory for " + adId + "deleted: " + directory.delete());
        }
    }

    /**
     * Method that crops an image to a square.
     * @param image The given image that is getting cropped.
     * @return A copy of the given image with square dimension.
     */
    public Image makeSquareImage(Image image) {
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        if (image.getWidth() > image.getHeight()) {
            width = (int) image.getHeight();
            height = (int) image.getHeight();
            x = (int) (image.getWidth() - width) / 2;
            y = 0;
        } else if (image.getHeight() > image.getWidth()) {
            width = (int) image.getWidth();
            height = (int) image.getWidth();
            x = 0;
            y = (int) (image.getHeight() - height) / 2;
        } else {
            return image;
        }
        return new WritableImage(image.getPixelReader(), x, y, width, height);
    }

}
