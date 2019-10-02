package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Byme {

    /**
     *
     */

    private static Byme singleton = null;

    public static Byme getInstance(IAccountHandler accountHandler){
        if(singleton == null){
            singleton = new Byme(accountHandler);
        }
        return singleton;
    }

    private List<IObserver> observers = new ArrayList<>();

    private HashMap<String, Account> accounts;

    private Account currentUser;

    private IAccountHandler accountHandler;

    /**
     *
     * @param accountHandler
     */

    private Byme(IAccountHandler accountHandler){
        accounts = new HashMap<>();
        this.accountHandler = accountHandler;
        accountHandler.loadAccounts(accounts);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> accountHandler.saveAccounts(accounts), "Shutdown-thread"));
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

    public HashMap<String, Account> getAccounts() {
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

    private void notifyObservers(){
        for(IObserver observer: observers){
            observer.update();
        }
    }





}
