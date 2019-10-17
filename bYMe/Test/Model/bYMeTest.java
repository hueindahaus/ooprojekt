package Model;

import Services.AccountHandler;
import Services.AdHandler;
import Services.RequestHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class bYMeTest {

    @Test
    void registerAccount() {
        Byme bYMe = Byme.getInstance(AccountHandler.getInstance(),AdHandler.getInstance(),RequestHandler.getInstance());
        bYMe.registerAccount("User1","Password1");
        assertEquals(bYMe.getAccounts().get("User1").getPassword(), ("Password1"));
        assertEquals(bYMe.getAccounts().get("User1").getUsername(), ("User1"));

    }

    @Test
    void isAlreadyRegistered() {

        Byme bYMe = Byme.getInstance(AccountHandler.getInstance(),AdHandler.getInstance(),RequestHandler.getInstance());

        bYMe.registerAccount("User1","Password1");
        bYMe.registerAccount("User1","Password2"); // User already exist: User1
        assertEquals(bYMe.getAccounts().get("User1").getPassword(), ("Password1")); // Password has not changed
        bYMe.registerAccount("User2","Password1"); // ok
    }



    @Test
    void getAccounts() {
        Byme bYMe = Byme.getInstance(AccountHandler.getInstance(),AdHandler.getInstance(),RequestHandler.getInstance());

        assertEquals(bYMe.getAccounts().size(), 0); //empty
        bYMe.registerAccount("User1","Password1");
        bYMe.registerAccount("User2","Password2");
        bYMe.registerAccount("User3","Password3");

        assertEquals(bYMe.getAccounts().size(), 3); // 3 accounts registered


    }

    @Test
    void loginUser() {
        Byme bYMe = Byme.getInstance(AccountHandler.getInstance(),AdHandler.getInstance(),RequestHandler.getInstance());

        bYMe.registerAccount("User1","Password1");
        bYMe.registerAccount("User2","Password1");

        //  bYMe.loginUser("User1","Password");
        bYMe.loginUser("User1","Password1"); // User1 logged in
        assertEquals("User1",bYMe.getCurrentUser().getUsername());
        assertFalse("User2".equals(bYMe.getCurrentUser().getUsername()));


    }
    @Test
    void signout() {
        Byme bYMe = Byme.getInstance(AccountHandler.getInstance(),AdHandler.getInstance(),RequestHandler.getInstance());

        bYMe.registerAccount("User1","Password1");
        bYMe.loginUser("User1","Password1"); // User1 logged in
        assertEquals("User1",bYMe.getCurrentUser().getUsername());
        bYMe.signoutUser();
        assertEquals(null,bYMe.getCurrentUser());



    }


}