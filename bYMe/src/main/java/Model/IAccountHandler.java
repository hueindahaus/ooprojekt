package Model;

import java.util.Map;

public interface IAccountHandler {      //utnyttjar dependency-injection pattern för att kunna read:a/write:a lagring

    void loadAccounts(Map<String, Account> accounts);
    void saveAccounts(Map<String, Account> accounts);
}
