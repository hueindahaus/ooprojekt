package Model;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class AccountHandler {
    private static AccountHandler singleton = null;
    private final String DATAFOLDER = "data";
    private ArrayList<Account> accounts;


    protected AccountHandler(){
    }

    public static AccountHandler getInstance(){
        if(singleton == null){
            singleton = new AccountHandler();
            singleton.init();
        }
        return singleton;
    }

    private void init(){ //initiera alla instansvariabler
        accounts = new ArrayList<>();
        loadAccounts();
    }

    public void shutDown(){
        this.saveAccounts();
    }

    private String getLoginFilePath(){
        return DATAFOLDER + File.separatorChar + "logins.txt";
    }


    private void loadAccounts(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.getLoginFilePath()), "ISO-8859-1"));
            System.out.println("loadAccounts, starting...");

            String line;
            while((line=reader.readLine()) != null){
                parseAccount(line);
            }
            reader.close();



        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    private void parseAccount(String line){
        String[] tokens = line.split(";");
        if(tokens.length == 2) {
            Account account = new Account(tokens[0], tokens[1]);
            accounts.add(account);
        } else if(!line.isEmpty()){
            System.out.println("AccountHandlers logins.txt, invalid line: " + line);
        }
    }

    private void saveAccounts(){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(this.getLoginFilePath());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "ISO-8859-1");
            System.out.println("saving accounts");
            String line = "";
            Iterator iterator = accounts.iterator();

            while(iterator.hasNext()){
                Account account = (Account)iterator.next();
                line = "" + account.getUsername() + ";" + account.getPassword() + "\n";
                outputStreamWriter.write(line);
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void registerAccount(String username, String password){   //test för att se om det skriver till textfilen
        accounts.add(new Account(username,password));
    }






}
