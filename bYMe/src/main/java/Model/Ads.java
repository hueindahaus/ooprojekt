package Model;

public class Ads {
    private String title;
    private int price;
    private String description;
    private String location;
    private String imagePath;

    public Ads(String title, int price, String description, String location, String imagePath) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.location = location;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }
}
