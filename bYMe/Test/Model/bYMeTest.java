package Model;

import Services.AdHandler;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class bYMeTest {

    @Test
    void registerAccount() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };

        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        assertEquals(bYMe.getAccounts().get("User1").getPassword(), ("Password1"));
        assertEquals(bYMe.getAccounts().get("User1").getUsername(), ("User1"));

    }

    @Test
    void isAlreadyRegistered() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);

        bYMe.registerAccount("User1", "Password1");
        bYMe.registerAccount("User1", "Password2"); // User already exist: User1
        assertEquals(bYMe.getAccounts().get("User1").getPassword(), ("Password1")); // Password has not changed
        bYMe.registerAccount("User2", "Password1"); // ok
    }


    @Test
    void getAccounts() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        assertEquals(bYMe.getAccounts().size(), 2); //User1 and User2 saved from previous tests (bYMe is singleton).
        bYMe.registerAccount("User1", "Password1");
        bYMe.registerAccount("User2", "Password2");
        bYMe.registerAccount("User3", "Password3");

        assertEquals(bYMe.getAccounts().size(), 3); // 3 accounts registered


    }

    @Test
    void loginUser() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        bYMe.registerAccount("User2", "Password1");

        //  bYMe.loginUser("User1","Password");
        bYMe.loginUser("User1", "Password1"); // User1 logged in
        assertEquals("User1", bYMe.getCurrentUser().getUsername());
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
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        bYMe.loginUser("User1", "Password1"); // User1 logged in
        assertEquals("User1", bYMe.getCurrentUser().getUsername());
        bYMe.signoutUser();
        assertEquals(null, bYMe.getCurrentUser());
    }

    @Test
    void createAd() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
         int before =   bYMe.getAds().size();
        bYMe.createAd("Title1", "Description", 10, "Chalmers");
        int after = bYMe.getAds().size();
        assertTrue(after>before);
    }

}
