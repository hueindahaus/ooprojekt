/**
 * Represents an ad object.
 */
package Model;


import java.util.ArrayList;

public class Ad{
        private String title;
        private int price;
        private String description;
        private String location;
        private final String adId;
        private final String account;
        private ArrayList<String> tagsList = new ArrayList<>(5);
        private ArrayList<Request> requests = new ArrayList<>();

    public String getTitle(){
            return title;
        }

        public Ad(String title, int price, String description, String location, String adId, String account){
            this.title = title;
            this.price = price;
            this.description = description;
            this.location = location;
            this.adId = adId;
            this.account = account;
            this.tagsList = tagsList;

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

        public String getAdId(){
            return adId;
        }

        public String getAccount(){
            return account;
        }

        public ArrayList<Request> getRequests() {
        return requests;
    }

        public void setPrice(int price) { this.price = price; }

        public void setTitle(String title) { this.title = title; }

        public void setDescription(String description) { this.description = description; }

        public void setLocation(String location) { this.location = location; }

        public ArrayList<String> getTagsList() { return tagsList; }

        public void setRequests(ArrayList<Request> requests) {
            this.requests = requests;
        }

        public void setTagsList(ArrayList<String> tagsList) { this.tagsList = tagsList; }

        public void addRequest(Request request){
            requests.add(request);
        }
}

