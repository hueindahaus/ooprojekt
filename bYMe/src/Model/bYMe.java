package Model;

import java.util.ArrayList;
import java.util.List;

public class bYMe {

    private ArrayList<Account> accounts;

    IAccountHandler accountHandler;

    public bYMe(IAccountHandler accountHandler){
        accounts = new ArrayList<>();
        this.accountHandler = accountHandler;
        accountHandler.loadAccounts(accounts);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> accountHandler.saveAccounts(accounts), "Shutdown-thread"));
    }

    public void registerAccount(String username, String password){
        if(!isAlreadyRegistered(username)) {
            accounts.add(new Account(username, password));
        } else {
            System.out.println("User already exist: " + username);
        }
    }

    public boolean isAlreadyRegistered(String username){   //metod som kollar om ett användarnamn redan är registrerat eller ej
        for(Account account: accounts){
            if(account.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }



}
