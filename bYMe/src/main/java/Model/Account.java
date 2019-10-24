package Model;

public class Account {
    private final String username;
    private String password;
    private double rating;
    private double ratingCount;

    public Account(String username, String password, double rating, double ratingCount){
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.ratingCount = ratingCount;
    }
    Account(String username, String password){
        this.username = username;
        this.password = password;
        this.rating = 0;
        this.ratingCount = 0;
    }
    public double getRatingCount() {return this.ratingCount;}

    public double getRating() { return rating; }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    void addRating(int rating){
        this.rating += rating;
        ratingCount ++;
    }
    public double getAverageRating(){
        if(ratingCount<1) {
            return (0.0);
        }else{
            return rating/ratingCount;
        }

    }


}
