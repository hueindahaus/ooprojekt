package Model;

public class Account {
    private final String username;
    private String password;
    private int rating;
    private int ratingCount;

    public Account(String username, String password, int rating, int ratingCount){
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.ratingCount = ratingCount;
    }
    public Account(String username, String password){
        this.username = username;
        this.password = password;
        this.rating = 0;
        this.ratingCount = 0;
    }
    public int getRatingCount() {return this.ratingCount;}

    public int getRating()      { return this.rating; }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public void addRating(int rating){
        this.rating += rating;
        ratingCount ++;
    }
    public int getAveregeRating(){
        return (rating/ratingCount);
    }


}
