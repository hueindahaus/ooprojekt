package Model;
import java.util.*;

public class Byme {

    private static Byme singleton = null;

    public static Byme getInstance(IAccountHandler accountHandler, IAdHandler adHandler, IRequestHandler requestHandler){   //argumenten måste vi ha för att Modellen inte ska vara beroende av "services"
        if(singleton == null){
            singleton = new Byme(accountHandler, adHandler, requestHandler);
        }
        return singleton;
    }

    private List<IObserver> observers = new ArrayList<>();

    private HashMap<String, Account> accounts;

    private Account currentUser;

    private IAccountHandler accountHandler;

    private IAdHandler adHandler;

    private IRequestHandler requestHandler;

    private HashMap<String,Ad> ads= new HashMap<>();

    public HashMap<String,Ad> getAds(){
        return ads;
    }

    private HashMap<Integer, Request> requests = new HashMap<>();



    private Byme(IAccountHandler accountHandler, IAdHandler adHandler, IRequestHandler requestHandler){
        accounts = new HashMap<>();
        this.accountHandler = accountHandler;
        this.adHandler = adHandler;
        this.requestHandler = requestHandler;
        accountHandler.loadAccounts(accounts);
        adHandler.loadAds(ads);
        requestHandler.loadRequests(requests);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveObjects(), "Shutdown-thread"));
    }


    public void removeAd(String adID){
        try {
            ads.remove(adID);
            notifyObservers();
        }
        catch(NullPointerException none){
            none.getMessage();
        }
    }

    public void editAd(String adID, String title, int price, String description, String location){

        Ad ad = ads.get(adID);

        ad.setTitle(title);
        ad.setPrice(price);
        ad.setDescription(description);
        ad.setLocation(location);

        notifyObservers();
    }

    private void saveObjects(){
        accountHandler.saveAccounts(accounts);
        adHandler.saveAds(ads);
        requestHandler.saveRequests(requests);
    }


    /**
     * This method enables the user to create an account.
     * The method adds the account into a hashmap.
     * If the account is already registered the user
     * gets a message telling them about it.
     * @param username
     * @param password
     */

    public void registerAccount(String username, String password){
        if(!isAlreadyRegistered(username)) {
            accounts.put(username, new Account(username, password));
        } else {
            System.out.println("User already exist: " + username);
        }
    }


    /**
     * This method checks if an account
     * has already been registered.
     * @param username
     * @return
     */

    public boolean isAlreadyRegistered(String username){   //metod som kollar om ett användarnamn redan är registrerat eller ej
        for(Map.Entry account: accounts.entrySet()){
            if(account.getKey().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the account that is currently
     * logged in.
     * @return
     */

    public Account getCurrentUser() {
        return currentUser;
    }

    protected HashMap<String, Account> getAccounts() {
        return accounts;
    }


    /**
     * Method used for logging in.
     * Changes the currentUser.
     * In order to login the password must
     * match the given password at registration.
     * @param username
     * @param password
     */

    public void loginUser(String username, String password){
        if(accounts.containsKey(username)){
            Account user = accounts.get(username);
            if(user.getPassword().equals(password)){
                currentUser = user;
                notifyObservers();
                System.out.println(currentUser.getUsername() + " logged in");
            }
        }
    }

    public void signoutUser(){
        currentUser = null;
        notifyObservers();
    }


    public void addObserver(IObserver observer){
        observers.add(observer);
    }

    /**
     * Used for notifying everything that is dependent
     * on the model that something has changed in the model.
     */


    private void notifyObservers(){         //används för att uppdatera allt som är beroende av modellen när något i modellen ändras
        for(IObserver observer: observers){
            observer.update();
        }
    }


    private String generateRandomAdId(){
        StringBuilder stringBuilder = new StringBuilder();
        Random randomizer = new Random();

        do {
            for (int i = 0; i < 4; i++) {
                stringBuilder.append(randomizer.nextInt(10));
            }
        } while (ads.containsKey(stringBuilder.toString()));

        return stringBuilder.toString();
    }


    public void createAd(String title, String description, int price, String location, String account){
        String adId = generateRandomAdId();
        ads.put(adId,new Ad(title,price,description,location,adId, account));
    }

    public boolean isLoggedIn(){
        return currentUser != null;
    }

    public void sendRequest(String sender, String receiver, String ad, String content){
        int index = requests.size();
        requests.put(index, new Request(sender, receiver, ad, content));
    }

    public HashMap<Integer, Request> getRequests() {
        return requests;
    }
}
