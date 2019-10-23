package Model;

import java.util.*;

public class Search {



    private String activeTag = "";
    private String newActiveTag = "";

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
            ArrayList<String> adTags = tagsToLowerCase(ad.getTagsList());

            int match = 0;

            for(int i=0; i < inputArray.length; i++) {
                if (adTags.contains(activeTag)){
                    if (adName.contains(inputArray[i]) || adDesc.contains(inputArray[i]) || adUser.contains(inputArray[i])
                            || adTags.contains(inputArray[i])){
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


    public static ArrayList<String> tagsToLowerCase(ArrayList<String> tags)
    {
        ListIterator<String> iterator = tags.listIterator();
        while (iterator.hasNext())
        {
            iterator.set(iterator.next().toLowerCase());
        }
        return tags;
    }


    public void setActiveTag(String activeTag){
        this.activeTag = activeTag;
    }

    public String getActiveTag() {
        return activeTag;
    }

    public String getNewActiveTag() {
        return newActiveTag;
    }

    public void setNewActiveTag(String newActiveTag) {
        this.newActiveTag = newActiveTag;
    }

}
