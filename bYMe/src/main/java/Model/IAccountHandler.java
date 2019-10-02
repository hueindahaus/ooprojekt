package Model;

import java.util.HashMap;

/**
 * Uses dependency-injection pattern in order to read and write data.
 */

public interface IAccountHandler {      //utnyttjar dependency-injection pattern för att kunna read:a/write:a lagring

    void loadAccounts(HashMap<String, Account> accounts);
    void saveAccounts(HashMap<String, Account> accounts);
}
