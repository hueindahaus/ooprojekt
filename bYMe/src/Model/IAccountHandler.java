package Model;

import java.util.ArrayList;

public interface IAccountHandler {      //utnyttjar dependency-injection pattern för att kunna read:a/write:a lagring

    void loadAccounts(ArrayList<Account> accounts);
    void saveAccounts(ArrayList<Account> accounts);
}
