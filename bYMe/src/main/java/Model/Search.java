package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Search {

    public ArrayList<Ad> findAds(String input, HashMap<String, Ad> ads) {
        String input_lowercase = input.toLowerCase();
        ArrayList<Ad> result = new ArrayList();
        Iterator iterator = ads.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry account = (Map.Entry) iterator.next();
            Ad ad = (Ad) account.getValue();
            String adName = ad.getTitle().toLowerCase();
            String adDesc = ad.getDescription().toLowerCase();
            String adUser = ad.getAccount().toLowerCase();
            if (adName.indexOf(input_lowercase) > -1) {
                result.add(ad);
            } else if (adDesc.indexOf(input_lowercase) > -1) {
                result.add(ad);
            }
            else if (adUser.indexOf(input_lowercase) > -1) {
                result.add(ad);
            }
        }
        return result;
    }

}
