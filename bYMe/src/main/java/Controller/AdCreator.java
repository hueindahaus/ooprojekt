package Controller;


import java.util.ArrayList;

public interface AdCreator {
    void createAd(String title, String description, int price, String location, ArrayList <String> tags);

    void populateAds();

}
