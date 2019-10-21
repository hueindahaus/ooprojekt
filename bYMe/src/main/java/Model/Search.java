/**
 * This class handles functionality that makes it possible to search on different properties (such as title, description,
 * user and tags) of an ad.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Search {

    private String activeTag = "";


    /**
     * Checks if the input of a search matches an ads title, description, user or tags.
     * @param input input from user.
     * @param ads hashmap of ads.
     * @return the result of the search.
     */
    public ArrayList<Ad> findAds(String input, HashMap<String, Ad> ads) {
        ArrayList<Ad> result = new ArrayList();
        String inputArray[] = input.toLowerCase().split(" ");
        Iterator iterator = ads.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry account = (Map.Entry) iterator.next();
            Ad ad = (Ad) account.getValue();
            String adTitle = ad.getTitle().toLowerCase();
            String adDesc = ad.getDescription().toLowerCase();
            String adUser = ad.getAccount().toLowerCase();
            ArrayList adTags = ad.getTagsList();

            int match = 0;
            for(int i=0; i < inputArray.length; i++) {
                if (adTitle.contains(activeTag) || adDesc.contains(activeTag) || adUser.contains(activeTag) || adTags.contains(activeTag)){
                    if (adTitle.contains(inputArray[i]) || adDesc.contains(inputArray[i]) || adUser.contains(inputArray[i])
                            || adTags.contains(inputArray[i])) {
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
