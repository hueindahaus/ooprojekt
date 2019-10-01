package Model;

public class Ad {

    private String price;
    private String title;
    private String description;
    private String location;

    public Ad(String price, String title, String description, String location){
        this.price = price;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public String getPrice() { return price; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getLocation() { return location; }

    public void setPrice(String price) { this.price = price; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setLocation(String location) { this.location = location; }
}
