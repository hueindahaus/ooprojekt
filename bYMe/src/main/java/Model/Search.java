package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Search {

    private String activeTag = "";

    public ArrayList<Ad> findAds(String input, HashMap<String, Ad> ads) {
        ArrayList<Ad> result = new ArrayList();
        String inputArray[] = input.toLowerCase().split(" ");
        Iterator iterator = ads.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry account = (Map.Entry) iterator.next();
            Ad ad = (Ad) account.getValue();
            String adName = ad.getTitle().toLowerCase();
            String adDesc = ad.getDescription().toLowerCase();
            String adUser = ad.getAccount().toLowerCase();

            int match = 0;
            for(int i=0; i < inputArray.length; i++) {
                if (adName.contains(activeTag) || adDesc.contains(activeTag) || adUser.contains(activeTag)){
                    if (adName.contains(inputArray[i]) || adDesc.contains(inputArray[i]) || adUser.contains(inputArray[i])) {
                        match++;
                    }
                }
            }
            if(match == inputArray.length){
                result.add(ad);
            }
        }
        return result;
    }

    public void setActiveTag(String activeTag){
        this.activeTag = activeTag;
    }
}
