package Model;

import java.util.ArrayList;
import java.util.List;

public class Ads {
    private String title;
    private int price;
    private String description;
    private String location;
    private final int adId;

    public Ads(String title, int price, String description, String location, String imagePath, int adId) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.location = location;
        this.adId = adId;
    }

    public String getTitle(){
        return title;
    }

    public int getPrice(){
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getAdId(){
        return adId;
    }
}
