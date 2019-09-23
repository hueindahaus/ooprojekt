package Services;

import Model.Account;
import Model.IAccountHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

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
    }   //För att denna path:en ska fungera måste man importa projektet som mappen "bYMe"


    public void loadAccounts(ArrayList<Account> accounts){
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

    private void parseAccount(String line, ArrayList<Account> accounts){
        String[] tokens = line.split(";");
        if(tokens.length == 2) {
            Account account = new Account(tokens[0], tokens[1]);
            accounts.add(account);
        } else if(!line.isEmpty()){
            System.out.println("AccountHandlers logins.txt, invalid line: " + line);
        }
    }

    public void saveAccounts(ArrayList<Account> accounts){
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










}