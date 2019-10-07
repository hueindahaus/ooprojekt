package Model;

import java.util.HashMap;

public interface IAccountHandler {      //utnyttjar dependency-injection pattern för att kunna read:a/write:a lagring

    void loadAccounts(HashMap<String, Account> accounts);
    void saveAccounts(HashMap<String, Account> accounts);
}
