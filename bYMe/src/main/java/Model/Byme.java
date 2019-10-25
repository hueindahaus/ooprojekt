package Model;

import java.text.ParseException;
import java.util.*;

/**
 * Byme is the aggregate object in the model. It has responsibility for the fundamental logic in the model
 * (such as adding an ad to a hashmap, editing an ad, deleting an ad, creating an account etc). Is represented as a singleton.
 *
 * @Author Alexander Huang, Joel Jönsson, Johan Gottlander, Milos Bastajic, Adam Jawad
 *
 * Uses: IObserver, Account, IAccountHandler, IAdHandler
 * Used by: AdCreatorController, Byme, DetailViewController, LoginController,
 *          MainController, MenuController, RequestItem.
 */

public final class Byme {

    private static Byme singleton = null;
    private List<IObserver> observers = new ArrayList<>();
    private Map<String, Account> accounts;
    private Account currentUser;
    private IAccountHandler accountHandler;
    private IAdHandler adHandler;
    private Map<String, Ad> ads = new LinkedHashMap<>();

    private Byme(IAccountHandler accountHandler, IAdHandler adHandler) {
        accounts = new HashMap<>();
        this.accountHandler = accountHandler;
        this.adHandler = adHandler;
        accountHandler.loadAccounts(accounts);
        adHandler.loadAds(ads);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveObjects(), "Shutdown-thread"));
    }

    /**
     * Singleton pattern offers a getInstance()-method which caps the amount that can be created to 1.
     * Its a static and public method which can be accessed everywhere in the program.
     *
     * @param accountHandler is the one that handles saving/loading of accounts
     * @param adHandler      is the one that handles saving/loading of ads
     * @return returns the singleton object.
     */

    public static Byme getInstance(IAccountHandler accountHandler, IAdHandler adHandler) {   //argumenten måste vi ha för att Modellen inte ska vara beroende av "services"
        if (singleton == null) {
            singleton = new Byme(accountHandler, adHandler);
        }
        return singleton;
    }

    /**
     * Getter for the HashMap with all the ads in the program.
     *
     * @return HashMap with the ads in the program.
     */

    public Map<String, Ad> getAds() {
        return ads;
    }

    /**
     * A method which removes an ad from the HashMap with ads.
     *
     * @param adID The id to the ad that shall be removed.
     */
    public void removeAd(String adID) {
        try {
            ads.remove(adID);
            notifyObservers();
        } catch (NullPointerException none) {
            none.getMessage();
        }
    }

    /**
     * A method which edits an ad from the HashMap with ads.
     *
     * @param adID        The id to the ad that shall be edited.
     * @param title       The title that should be setted in the ad.
     * @param price       The price that should be setted in the ad
     * @param description The description that should be setted in the ad.
     * @param location    The Location that should be setted in the ad.
     * @param tags        The list of tags that should be setted in the ad.
     */

    public void editAd(String adID, String title, int price, String description, String location, List<String> tags) {

        Ad ad = ads.get(adID);

        ad.setTitle(title);
        ad.setPrice(price);
        ad.setDescription(description);
        ad.setLocation(location);
        ad.setTagsList(tags);

        notifyObservers();
    }

    private void addTagsToAd(String adID, List<String> tags) {
        Ad ad = ads.get(adID);
        ad.setTagsList(tags);
    }


    private void saveObjects() {
        accountHandler.saveAccounts(accounts);
        adHandler.saveAds(ads);
    }

    /**
     * This method enables the user to create an account.
     * The method adds the account into a hashmap.
     * If the account is already registered the user
     * gets a message telling them about it.
     *
     * @param username The username which is setted for the account to be made.
     * @param password The password which is setted for the account to be made.
     */

    public void registerAccount(String username, String password) {
        if (!isAlreadyRegistered(username)) {
            accounts.put(username, new Account(username, password));
        } else {
            System.out.println("User already exist: " + username);
        }
    }

    /**
     * Checks if a username is already taken.
     *
     * @param username The string value that will be checked if it is a username for an account or not.
     * @return Returns a boolean that is true or false based on if an account with the given username exist.
     */

    public boolean isAlreadyRegistered(String username) {   //metod som kollar om ett användarnamn redan är registrerat eller ej
        return accounts.containsKey(username);
    }

    /**
     * Getter for the current username.
     *
     * @return Returns the username for the current user.
     */
    public String getCurrentUsersUsername() {
        return currentUser.getUsername();
    }

    /**
     * Method used for logging in.
     * Changes the currentUser.
     * In order to login the password must
     * match the given password at registration.
     *
     * @param username
     * @param password
     */

    public void loginUser(String username, String password) {
        if (accounts.containsKey(username)) {
            Account user = accounts.get(username);
            if (user.passwordMatches(password)) {
                currentUser = user;
                notifyObservers();
                System.out.println(currentUser.getUsername() + " logged in");
            }
        }
    }

    /**
     * Is used to confirm if a users exist.
     *
     * @param username The username of the account.
     * @param password The password of the account.
     * @return Returns a boolean which is true or false based on if both the username and password exists or not.
     */

    public boolean userExist(String username, String password) {
        return accounts.containsKey(username) && accounts.get(username).passwordMatches(password);
    }

    /**
     * Is used when a user signs out.
     * Sets the current user to null and notifies observers.
     */

    public void signoutUser() {
        currentUser = null;
        notifyObservers();
    }

    /**
     * Method which can add an observer to the list of observers.
     *
     * @param observer The object that will be added to the list of observers.
     */

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update();
        }
    }

    private String generateRandomAdId() {
        StringBuilder stringBuilder = new StringBuilder();
        Random randomizer = new Random();

        do {
            for (int i = 0; i < 4; i++) {
                stringBuilder.append(randomizer.nextInt(10));
            }
        } while (ads.containsKey(stringBuilder.toString()));

        String value = stringBuilder.toString();

        if (ads.containsKey(value)) {
            value = generateRandomAdId();
        }

        return value;
    }

    /**
     * Method that creates an ad and puts it in the HashMap with ads.
     *
     * @param title       Title of the ad that is created.
     * @param description Description of the ad that is created.
     * @param price       Price of the ad that is created.
     * @param location    Location of the ad that is created.
     * @param account     Username of the account that creates the ad.
     * @param tags        The list of tags that the ad should include
     */
    public void createAd(String title, String description, int price, String location, String account, List<String> tags) {
        String adId = generateRandomAdId();
        ads.put(adId, new Ad(title, price, description, location, adId, account));
        addTagsToAd(adId, tags);
    }

    /**
     * Method which checks if a user is logged in nor not.
     *
     * @return Returns a boolean that is true or false based on if the
     * user is logged in or not.
     */

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Method which is used when a user wants to send a request for an ad.
     * A new request for that specific ad is created.
     *
     * @param sender   Username of the user that sends the request.
     * @param receiver Username of the user that receives the request.
     * @param ad       That ad that is requested.
     * @param message  A message from the user that sends the request.
     * @param date     The date of when the sender wants to use the service of the ad.
     * @throws ParseException
     */

    public void sendRequest(String sender, String receiver, Ad ad, String message, String date) throws ParseException {
        ad.addRequest(new Request(sender, receiver, ad.getAdId(), message, date, RequestState.PENDING));
        notifyObservers();
    }

    /**
     * Used to remove a request from the lis of requests.
     *
     * @param request The request that will be removed.
     */

    public void removeRequest(Request request) {
        Ad currentAd = ads.get(request.getAd());
        List<Request> requests = currentAd.getRequests();
        requests.remove(request);
        currentAd.setRequests(requests);
        notifyObservers();
    }

    /**
     * Is used when an account should be reviewed/rated.
     *
     * @param username The username of the account the will be rated.
     * @param rating   The rating that will be added to the accounts total rating.
     */

    public void reviewAccount(String username, int rating) {
        accounts.get(username).addRating(rating);
    }

    /**
     * Getter for the rating of an acount.
     *
     * @param username The username of the account.
     * @return Returns the average rating of the account.
     */

    public double getAccountRating(String username) {
        return accounts.get(username).getAverageRating();
    }

    /**
     * Getter for the rating count of an account.
     *
     * @param username The username of the account.
     * @return Returns an Integer which represents the total count of ratings for an account.
     */

    public double getAccountRatingCount(String username) {
        return accounts.get(username).getRatingCount();
    }

    /**
     * Getter which is used to get the last added id for an Ad.
     *
     * @return Returns the id of the ad that was last added.
     */

    public String getLastAddedAdId() {
        Ad lastAdded = null;
        for (Ad ad : ads.values()) {
            lastAdded = ad;
        }
        return lastAdded.getAdId();
    }

    public String getAdTitle(String adID) {
        return ads.get(adID).getTitle();
    }

    public double getAccountRatingCount(String username) {
        return accounts.get(username).getRatingCount();
    }
}

