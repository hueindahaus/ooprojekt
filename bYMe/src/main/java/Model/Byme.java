package Model;

import java.util.*;

public class Byme {

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


    public HashMap<String,Ad> ads= new HashMap<>();

    public HashMap<String,Ad> getAds(){
        return ads;
    }



    private Byme(IAccountHandler accountHandler){
        accounts = new HashMap<>();
        this.accountHandler = accountHandler;
        accountHandler.loadAccounts(accounts);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> accountHandler.saveAccounts(accounts), "Shutdown-thread"));
    }

    public void registerAccount(String username, String password){
        if(!isAlreadyRegistered(username)) {
            accounts.put(username, new Account(username, password));
        } else {
            System.out.println("User already exist: " + username);
        }
    }

    public boolean isAlreadyRegistered(String username){   //metod som kollar om ett användarnamn redan är registrerat eller ej
        for(Map.Entry account: accounts.entrySet()){
            if(account.getKey().equals(username)){
                return true;
            }
        }
        return false;
    }

    public Account getCurrentUser() {
        return currentUser;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

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


    public void createAd(String title, String description, int price, String location){
        String adId = generateRandomAdId();
        ads.put(adId,new Ad(title,price,description,location,adId));
    }


}
