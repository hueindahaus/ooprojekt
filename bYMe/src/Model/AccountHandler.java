package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class AccountHandler {
    private static AccountHandler singleton = null;
    private ArrayList<Account> accounts;


    private AccountHandler(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutDown(), "Shutdown-thread"));
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

    private void shutDown(){
        this.saveAccounts();
    }

    private String getLoginFilePath(){
        return "data" + File.separatorChar + "logins.txt";
    }   //För att denna path:en ska fungera måste man importa projektet som mappen "bYMe"


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

    public boolean isAlreadyRegistered(String username){   //metod som kollar om ett användarnamn redan är registrerat eller ej
        for(Account account: accounts){
            if(account.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void registerAccount(String username, String password){
        if(!isAlreadyRegistered(username)) {
            accounts.add(new Account(username, password));
        } else {
            System.out.println("User already exist: " + username);
        }
    }






}
