package Model;

import java.util.Map;

/**
 * @Author Alexander Huang
 * Uses dependency-injection pattern in order to read and write data.
 * Used by: Byme
 */
public interface IAccountHandler {

    void loadAccounts(Map<String, Account> accounts);

    void saveAccounts(Map<String, Account> accounts);
}
