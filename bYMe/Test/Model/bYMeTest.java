package Model;

import Services.AccountHandler;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class bYMeTest {

    @Test
    void registerAccount() {
        IAccountHandler accountHandler= new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        Byme bYMe = Byme.getInstance(accountHandler);
        bYMe.registerAccount("User1","Password1");
        assertEquals(bYMe.getAccounts().get("User1").getPassword(), ("Password1"));
        assertEquals(bYMe.getAccounts().get("User1").getUsername(), ("User1"));

    }

    @Test
    void isAlreadyRegistered() {
        IAccountHandler accountHandler= new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        Byme bYMe = Byme.getInstance(accountHandler);

        bYMe.registerAccount("User1","Password1");
        bYMe.registerAccount("User1","Password2"); // User already exist: User1
        assertEquals(bYMe.getAccounts().get("User1").getPassword(), ("Password1")); // Password has not changed
        bYMe.registerAccount("User2","Password1"); // ok
    }



    @Test
    void getAccounts() {
        IAccountHandler accountHandler= new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        Byme bYMe = Byme.getInstance(accountHandler);
        assertEquals(2, bYMe.getAccounts().size()); //2 accounts made earlier
        bYMe.registerAccount("User1","Password1");
        bYMe.registerAccount("User2","Password2");
        bYMe.registerAccount("User3","Password3");

        assertEquals(bYMe.getAccounts().size(), 3); // 3 accounts registered


    }

    @Test
    void loginUser() {
        IAccountHandler accountHandler= new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        Byme bYMe = Byme.getInstance(accountHandler);
        bYMe.registerAccount("User1","Password1");
        bYMe.registerAccount("User2","Password1");

        //  bYMe.loginUser("User1","Password");
        bYMe.loginUser("User1","Password1"); // User1 logged in
        assertEquals("User1",bYMe.getCurrentUser().getUsername());
        assertFalse("User2".equals(bYMe.getCurrentUser().getUsername()));


    }
    @Test
    void signout() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        Byme bYMe = Byme.getInstance(accountHandler);
        bYMe.registerAccount("User1","Password1");
        bYMe.loginUser("User1","Password1"); // User1 logged in
        assertEquals("User1",bYMe.getCurrentUser().getUsername());
        bYMe.signoutUser();
        assertEquals(null,bYMe.getCurrentUser());



    }
}