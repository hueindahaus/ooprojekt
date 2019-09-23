package Services;

import Model.Account;
import Model.IAccountHandler;
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
        return "data" + File.separatorChar + "logins.txt";
    }   //För att denna path:en ska fungera måste man importa projektet som mappen "Byme"


    public void loadAccounts(HashMap<String, Account> accounts){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.getLoginFilePath()), "ISO-8859-1"));
            System.out.println("loadAccounts, starting...");

            String line;
            while((line=reader.readLine()) != null){
                parseAccount(line,accounts);
            }
            reader.close();



        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    private void parseAccount(String line, HashMap<String, Account> accounts){
        String[] tokens = line.split(";");
        if(tokens.length == 2) {
            Account account = new Account(tokens[0], tokens[1]);
            accounts.put(account.getUsername(), account);
        } else if(!line.isEmpty()){
            System.out.println("AccountHandlers logins.txt, invalid line: " + line);
        }
    }

    public void saveAccounts(HashMap<String, Account> accounts){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(this.getLoginFilePath());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "ISO-8859-1");
            System.out.println("saving accounts");
            String line = "";
            Iterator iterator = accounts.entrySet().iterator();

            while(iterator.hasNext()){
                Map.Entry account = (Map.Entry) iterator.next();
                Account ac = (Account) account.getValue();
                line = "" + account.getKey() + ";" + ac.getPassword() + "\n";
                outputStreamWriter.write(line);
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch(IOException exception){
            exception.printStackTrace();
        }
    }










}
