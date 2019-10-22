package Model;

import Services.AdHandler;
import Services.RequestHandler;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.text.ParseException;
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
    void userExists(){
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
        assertTrue(bYMe.userExist("User1","Password1"));
    }


    @Test
    void sendRequest() throws ParseException {
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
        bYMe.registerAccount("User2", "Password2");
        bYMe.loginUser("User1", "Password1");
        Ad ad = new Ad("add",4,"ad","GBG","1234","123");
        bYMe.getAds().put(ad.getAdId(),ad);
        bYMe.sendRequest("User1", "User2", ad,"Hej","12/12/12-12:12");
        bYMe.signoutUser();
        bYMe.loginUser("User2","Password2");
        assertEquals("User1",ad.getRequests().get(0).getSender());

    }
    @Test
    void isLoggedIn(){
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
        bYMe.loginUser("User1","Password1");
        assertTrue(bYMe.isLoggedIn());

    }
    @Test
    void editAd(){
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
        Ad ad = new Ad("add",4,"ad","GBG","1234","123");
        bYMe.getAds().put(ad.getAdId(),ad);
        int priceBefore = ad.getPrice();
        String titleBefore = ad.getTitle();
        String descBefore = ad.getDescription();
        String locBefore = ad.getLocation();
        bYMe.editAd("1234","Changed",1,"Changed","Changed",null);
        int priceAfter = ad.getPrice();
        String titleAfter = ad.getTitle();
        String descAfter = ad.getDescription();
        String locAfter = ad.getLocation();
        assertTrue(priceBefore != priceAfter);
        assertNotEquals(descBefore,descAfter);
        assertNotEquals(titleBefore,titleAfter);
        assertNotEquals(locBefore,locAfter);
    }
    @Test
    void removeAd(){
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
        Ad ad = new Ad("add",4,"ad","GBG","1234","123");
        bYMe.getAds().put(ad.getAdId(),ad);
        int before = bYMe.getAds().size();
        bYMe.removeAd(ad.getAdId());
        int after = bYMe.getAds().size();
        assertTrue(before>after);
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
        bYMe.createAd("Title1", "Description", 10, "Chalmers",null,null);
        int after = bYMe.getAds().size();
        assertTrue(after>before);
    }

}
