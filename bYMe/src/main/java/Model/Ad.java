package Model;


import java.util.ArrayList;
import java.util.List;

/**
 * A class that creates and handles Ad objects which the whole application circles around. It has many attributes such as
 * adId, account, title, price etc. It also has two ArrayLists of type String which holds the tags and requests of an ad.
 * @author
 */


public class Ad {
    private final String adId;
    private final String account;
    private String title;
    private int price;
    private String description;
    private String location;
    private List<String> tagsList = new ArrayList<>(5);
    private List<Request> requests = new ArrayList<>();

    /**
     * Constructor which sets the many instance variables.
     */

    public Ad(String title, int price, String description, String location, String adId, String account) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.location = location;
        this.adId = adId;
        this.account = account;
    }

    /**
     * Getter for an ads title.
     * @return  Returns the String value of the title.
     */

    public String getTitle() {
        return title;
    }
    /**
     * Setter for an ads title.
     */

    void setTitle(String title) {
        this.title = title;
    }
    /**
     * Getter for an ads price.
     * @return  Returns the Integer value of the price.
     */

    public int getPrice() {
        return price;
    }

    /**
     * Setter for an ads price.
     */

    void setPrice(int price) {
        this.price = price;
    }

    /**
     * Getter for an ads description.
     * @return  Returns the String value of the description.
     */

    public String getDescription() {
        return description;
    }

    /**
     * Setter for an ads description.
     */

    void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for an ads location.
     * @return  Returns the String value of the location.
     */

    public String getLocation() {
        return location;
    }

    /**
     * Setter for an ads location.
     */

    void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for an ads ID.
     * @return  Returns the String value of the ID.
     */

    public String getAdId() {
        return adId;
    }

    /**
     * Getter for the account that an ad is connected to.
     * @return  Returns the String value of the account.
     */

    public String getAccount() {
        return account;
    }

    /**
     * Getter for an ads list of request.
     * @return  Returns the list of the ads requests.
     */

    public List<Request> getRequests() {
        return requests;
    }

    /**
     * Setter for an ads list of request.
     */

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    /**
     * Getter for an ads list of tags.
     * @return  Returns the list of the ads tags.
     */

    public List<String> getTagsList() {
        return tagsList;
    }

    /**
     * Setter for an ads list of request.
     */

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
    }

    /**
     * Method which adds a request to an ad.
     */

    void addRequest(Request request) {
        requests.add(request);
    }
}

