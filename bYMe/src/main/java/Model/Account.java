package Model;

/**
 *This class is used to create Account objects for users. It has getters for its instance variables such as username, password,
 *rating and ratingCount and does also calculate the rating of an user.
 * @author Milos Bastajic, Alexander Huang
 *
 * Uses: -
 * Used by: AccountHandler, Byme, IAccountHandler.
 */

public class Account {
    private final String username;
    private String password;
    private double rating;
    private double ratingCount;

    /**
     *Is used when logins.json is read to create all the accounts when the program starts.
     * @param username sets the accounts username.
     * @param password sets the accounts password.
     * @param rating sets the rating of an account.
     * @param ratingCount sets the ratingCount for the account.
     */

    public Account(String username, String password, double rating, double ratingCount) {
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.ratingCount = ratingCount;
    }

    /**
     * Constructor which is called upon when a user is creating a new account.
     * The rating and ratingCounts sets to 0 since it's a new user.
     * @param username sets the accounts username.
     * @param password sets the accounts password.
     */

    Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.rating = 0;
        this.ratingCount = 0;
    }

    /**
     * Getter for an accounts ratingCount.
     * @return Returns the accounts ratingCount which is a double.
     */

    public double getRatingCount() {
        return this.ratingCount;
    }

    /**
     * Getter for an accounts rating.
     * @return Returns the rating of an account which is a double.
     */

    public double getRating() {
        return rating;
    }

    /**
     * Getter for the accounts username.
     * @return Returns the username of an account which is a string.
     */

    public String getUsername() {
        return this.username;
    }

    /**
     * Getter for the accounts password.
     * @return Returns the password of an account which is a string.
     */

    public String getPassword() {
        return this.password;
    }

    /**
     * Method which is used to add a rating to an account.
     * It also incremments the ratingCount of the account.
     * @param rating    The rating that should be added to an accounts rating.
     */

    void addRating(int rating) {
        this.rating += rating;
        ratingCount++;
    }

    /**
     *Getter for an accounts average rating. Is used to show an users rating as a seller in various places of
     * the application.
     * @return Returns the average rating by dividing the rating with the amounts of rating.
     */

    double getAverageRating() {
        if (ratingCount < 1) {
            return 0.0;
        } else {
            return rating / ratingCount;
        }
    }

    /**
     * A method which checks if the password input matches the password of the account when a user
     * tries to log in.
     * @param password      The password that the user types in the password textfield.
     * @return
     */

    boolean passwordMatches(String password) {
        return this.password.equals(password);
    }

}
