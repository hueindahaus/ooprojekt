package Model;

    public class Ad {
        private String title;
        private int price;
        private String description;
        private String location;
        private int adId;

        public String getTitle(){
            return title;
        }

        public Ad(String title, int price, String description, String location, int adId) {
            this.title = title;
            this.price = price;
            this.description = description;
            this.location = location;
            this.adId = adId;
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

        public void setPrice(int price) { this.price = price; }

        public void setTitle(String title) { this.title = title; }

        public void setDescription(String description) { this.description = description; }

        public void setLocation(String location) { this.location = location; }

        public void setAdId(int adId){this.adId = adId;}
    }

