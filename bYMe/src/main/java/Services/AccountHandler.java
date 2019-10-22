package Services;

import Model.Account;
import Model.IAccountHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AccountHandler implements IAccountHandler {
    private static AccountHandler singleton = null;



    private AccountHandler(){
    }

    public static AccountHandler getInstance(){
        if(singleton == null){
            singleton = new AccountHandler();

        }
        return singleton;
    }




    private String getLoginFilePath(){
        return "data" + File.separatorChar + "logins.json";
    }   //För att denna path:en ska fungera måste man importa projektet som mappen "Byme"


    public void loadAccounts(HashMap<String, Account> accounts){
        JSONParser parser = new JSONParser();

        try{
            FileReader fileaReader = new FileReader(getLoginFilePath());
            File file = new File(getLoginFilePath());

            if(file.length() > 0) {
                Object obj = parser.parse(fileaReader);
                JSONArray accountList = (JSONArray) obj;

                for (Object object : accountList) {
                    Account account = parseJSONObject((JSONObject)object);
                    accounts.put(account.getUsername(), account);
                }
            }

        } catch(IOException exception){
            exception.printStackTrace();
        } catch (ParseException excpetion){
            excpetion.printStackTrace();
        }
    }

    public void saveAccounts(HashMap<String,Account> accounts){
        JSONArray jsonList = new JSONArray();


        Iterator iterator = accounts.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry acc = (Map.Entry) iterator.next();
            Account account = (Account) acc.getValue();
            jsonList.add(convertToJSONObject(account));
        }

        try{
            FileWriter file = new FileWriter(getLoginFilePath());
            file.write(jsonList.toString());
            file.flush();
        } catch (IOException exception){
            System.out.println("Can't save list with accounts");
            exception.printStackTrace();
        }

    }

    private JSONObject convertToJSONObject(Account account){
        JSONObject object = new JSONObject();
        object.put("username", account.getUsername());
        object.put("password", account.getPassword());
        object.put("rating", account.getRating());
        object.put("ratingCount", account.getRatingCount());

        return object;
    }

    private Account parseJSONObject(JSONObject obj){
        return new Account((String)obj.get("username"),(String)obj.get("password"),(double)obj.get("rating"),(double)obj.get("ratingCount"));
    }






}
