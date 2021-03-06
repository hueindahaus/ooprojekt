package Model;

import Services.AdHandler;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class bYMeTest {

    @Test
    void registerAccount() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };

        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        assertTrue(bYMe.isAlreadyRegistered("User1"));

    }

    @Test
    void isAlreadyRegistered() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);

        bYMe.registerAccount("User1", "Password1");
        assertTrue(bYMe.isAlreadyRegistered("User1"));
    }


    @Test
    void userExists() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {
            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        assertTrue(bYMe.userExist("User1", "Password1"));
    }


    @Test
    void sendRequest() throws ParseException {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {
            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        bYMe.registerAccount("User2", "Password2");
        bYMe.loginUser("User1", "Password1");
        Ad ad = new Ad("add", 4, "ad", "GBG", "1234", "123");
        bYMe.getAds().put(ad.getAdId(), ad);
        bYMe.sendRequest("User1", "User2", ad, "Hej", "12-12-12/12:12");
        bYMe.signoutUser();
        bYMe.loginUser("User2", "Password2");
        assertEquals("User1", ad.getRequests().get(0).getSender());

    }

    @Test
    void isLoggedIn() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        bYMe.loginUser("User1", "Password1");
        assertTrue(bYMe.isLoggedIn());

    }

    @Test
    void editAd() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        Ad ad = new Ad("add", 4, "ad", "GBG", "1234", "123");
        bYMe.getAds().put(ad.getAdId(), ad);
        int priceBefore = ad.getPrice();
        String titleBefore = ad.getTitle();
        String descBefore = ad.getDescription();
        String locBefore = ad.getLocation();
        bYMe.editAd("1234", "Changed", 1, "Changed", "Changed", new ArrayList<>());
        int priceAfter = ad.getPrice();
        String titleAfter = ad.getTitle();
        String descAfter = ad.getDescription();
        String locAfter = ad.getLocation();
        assertTrue(priceBefore != priceAfter);
        assertNotEquals(descBefore, descAfter);
        assertNotEquals(titleBefore, titleAfter);
        assertNotEquals(locBefore, locAfter);
    }

    @Test
    void removeAd() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        Ad ad = new Ad("add", 4, "ad", "GBG", "1234", "123");
        bYMe.getAds().put(ad.getAdId(), ad);
        int before = bYMe.getAds().size();
        bYMe.removeAd(ad.getAdId());
        int after = bYMe.getAds().size();
        assertTrue(before > after);
    }

    @Test
    void loginUser() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        bYMe.registerAccount("User2", "Password1");

        //  bYMe.loginUser("User1","Password");
        bYMe.loginUser("User1", "Password1"); // User1 logged in
        assertEquals("User1", bYMe.getCurrentUsersUsername());
        assertFalse("User2".equals(bYMe.getCurrentUsersUsername()));


    }

    @Test
    void signout() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        bYMe.registerAccount("User1", "Password1");
        bYMe.loginUser("User1", "Password1"); // User1 logged in
        assertEquals("User1", bYMe.getCurrentUsersUsername());
        bYMe.signoutUser();
        assertFalse(bYMe.isLoggedIn());
    }

    @Test
    void createAd() {
        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(Map<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(Map<String, Account> accounts) {

            }
        };
        IAdHandler adHandler = AdHandler.getInstance();
        Byme bYMe = Byme.getInstance(accountHandler, adHandler);
        int before = bYMe.getAds().size();
        bYMe.createAd("Title1", "Description", 10, "Chalmers", "Account", new ArrayList<>());
        int after = bYMe.getAds().size();
        assertTrue(after > before);
    }

}
