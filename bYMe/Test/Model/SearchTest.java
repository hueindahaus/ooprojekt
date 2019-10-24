package Model;

import Services.AdHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {

    @Test
    void searchInput() {

        IAccountHandler accountHandler = new IAccountHandler() {
            @Override
            public void loadAccounts(HashMap<String, Account> accounts) {

            }

            @Override
            public void saveAccounts(HashMap<String, Account> accounts) {

            }
        };


        IAdHandler adHandler = AdHandler.getInstance();
        Byme byme = Byme.getInstance(accountHandler, adHandler);
        Search searchTest = new Search();


        Ad adTested1 = new Ad("Title1", 10, "Description1", "Chalmers1", "idtest1", "user");
        Ad adTested2 = new Ad("Title2", 20, "Description2", "Chalmers2", "idtest2", "user");

        ArrayList<String> tagList1 = new ArrayList<>();
        tagList1.add("tag1");
        tagList1.add("tag2");

        adTested1.setTagsList(tagList1);
        adTested2.setTagsList(tagList1);

        byme.getAds().put(adTested1.getAdId(), adTested1);
        byme.getAds().put(adTested2.getAdId(), adTested2);

        String inputTest2 = adTested2.getTitle();
        String inputTest3 = tagList1.get(0);

        assertEquals(1, searchTest.findAds("Title1", byme.getAds()).size()); //clear data.ads.JSON before tests
        assertEquals(adTested1, searchTest.findAds("Title1", byme.getAds()).get(0));
        assertEquals(adTested2, searchTest.findAds("Description2", byme.getAds()).get(0));
        assertEquals(adTested1, searchTest.findAds("tag1", byme.getAds()).get(0));
        assertEquals(adTested2, searchTest.findAds("tag2", byme.getAds()).get(1));

    }

    @Test
    void tagsToLowerCase(){

        Search searchTest = new Search();

        ArrayList<String> testList = new ArrayList<>();

        testList.add("TestTag");

        assertEquals("testtag", searchTest.tagsToLowerCase(testList).get(0));

    }

}
