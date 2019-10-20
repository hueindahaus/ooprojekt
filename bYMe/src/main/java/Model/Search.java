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
            String adTag1 = ad.getTagsList().get(0).toLowerCase();
            String adTag2 = ad.getTagsList().get(1).toLowerCase();
            String adTag3 = ad.getTagsList().get(2).toLowerCase();
            String adTag4 = ad.getTagsList().get(3).toLowerCase();
            String adTag5 = ad.getTagsList().get(4).toLowerCase();

            int match = 0;
            for(int i=0; i < inputArray.length; i++) {
                if (adName.contains(activeTag) || adDesc.contains(activeTag) || adUser.contains(activeTag) || adTag1.contains(activeTag)
                        || adTag2.contains(activeTag) || adTag3.contains(activeTag) || adTag4.contains(activeTag) || adTag5.contains(activeTag)){
                    if (adName.contains(inputArray[i]) || adDesc.contains(inputArray[i]) || adUser.contains(inputArray[i])
                            || adTag1.contains(inputArray[i]) || adTag2.contains(inputArray[i]) || adTag3.contains(inputArray[i])
                            || adTag4.contains(inputArray[i]) || adTag5.contains(inputArray[i])) {
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
