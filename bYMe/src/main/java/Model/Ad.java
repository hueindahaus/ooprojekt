package Model;


import java.util.ArrayList;
import java.util.List;

public class Ad{
        private String title;
        private int price;
        private String description;
        private String location;
        private final String adId;
        private final String account;
        private List<String> tagsList = new ArrayList<>(5);
        private List<Request> requests = new ArrayList<>();

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

        public List<Request> getRequests() {
        return requests;
    }

        void setPrice(int price) { this.price = price; }

        void setTitle(String title) { this.title = title; }

        void setDescription(String description) { this.description = description; }

        void setLocation(String location) { this.location = location; }

        public List<String> getTagsList() { return tagsList; }

        public void setRequests(List<Request> requests) {
            this.requests = requests;
        }

        public void setTagsList(List<String> tagsList) { this.tagsList = tagsList; }

        void addRequest(Request request){
            requests.add(request);
        }
}

