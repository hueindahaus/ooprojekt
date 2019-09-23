package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bYMe {

    private HashMap<String, Account> accounts;

    private Account currentUser;

    IAccountHandler accountHandler;

    public bYMe(IAccountHandler accountHandler){
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
            }
        }
        System.out.println(currentUser.getUsername());
    }



}
