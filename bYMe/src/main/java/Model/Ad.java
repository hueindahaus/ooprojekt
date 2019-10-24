package Model;


import java.util.ArrayList;
import java.util.List;

public class Ad {
    private final String adId;
    private final String account;
    private String title;
    private int price;
    private String description;
    private String location;
    private List<String> tagsList = new ArrayList<>(5);
    private List<Request> requests = new ArrayList<>();

    public Ad(String title, int price, String description, String location, String adId, String account) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.location = location;
        this.adId = adId;
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }

    public String getAdId() {
        return adId;
    }

    public String getAccount() {
        return account;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<String> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
    }

    void addRequest(Request request) {
        requests.add(request);
    }
}

