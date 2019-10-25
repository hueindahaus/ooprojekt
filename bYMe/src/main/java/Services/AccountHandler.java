package Services;

import Model.Account;
import Model.IAccountHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Read and writes to logins.json, responsible for storing Accounts
 * When the application starts it reads from the json files and creates Account-objects with the parameters from the json-file
 * When the application is closed it writes all Account-object's parameters to the json-file so that they can be created when the application is opened.
 * @author Alexander Huang
 *
 * Uses: Account.
 * Used by: MainController.
 */
public final class AccountHandler implements IAccountHandler {
    private static AccountHandler singleton = null;


    private AccountHandler() {
    }

    public static AccountHandler getInstance() {
        if (singleton == null) {
            singleton = new AccountHandler();

        }
        return singleton;
    }


    private String getLoginFilePath() {
        return "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "Services" + File.separatorChar + "data" + File.separatorChar + "logins.json";
    }

    /**
     * Reads accounts from accounts.json and adds them to a Map of accounts
     * @param accounts Map of accounts
     */
    @Override
    public void loadAccounts(Map<String, Account> accounts) {
        JSONParser parser = new JSONParser();

        try {
            FileReader fileaReader = new FileReader(getLoginFilePath());
            File file = new File(getLoginFilePath());

            if (file.length() > 0) {
                Object obj = parser.parse(fileaReader);
                JSONArray accountList = (JSONArray) obj;

                for (Object object : accountList) {
                    Account account = parseJSONObject((JSONObject) object);
                    accounts.put(account.getUsername(), account);
                }
            }

        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reads accounts a Map of accounts and writes them to accounts.json
     * @param accounts Map of accounts
     */
    @Override
    public void saveAccounts(Map<String, Account> accounts) {
        JSONArray jsonList = new JSONArray();


        for (Account account : accounts.values()) {
            jsonList.add(convertToJSONObject(account));
        }

        try {
            FileWriter file = new FileWriter(getLoginFilePath());
            file.write(jsonList.toString());
            file.flush();
        } catch (IOException exception) {
            System.out.println("Can't save list with accounts");
            exception.printStackTrace();
        }

    }

    private JSONObject convertToJSONObject(Account account) {
        JSONObject object = new JSONObject();
        object.put("username", account.getUsername());
        object.put("password", account.getPassword());
        object.put("rating", account.getRating());
        object.put("ratingCount", account.getRatingCount());

        return object;
    }

    private Account parseJSONObject(JSONObject obj) {
        return new Account((String) obj.get("username"), (String) obj.get("password"), (double) obj.get("rating"), (double) obj.get("ratingCount"));
    }


}
