package Model;

import java.util.*;

public class Search {

    private String activeTag = "";




    public ArrayList<Ad> findAds(String input, HashMap<String, Ad> ads) {
        ArrayList<Ad> result = new ArrayList();
        String inputArray[] = input.toLowerCase().split(" ");

        for(Ad ad:ads.values()) {
            String adName = ad.getTitle().toLowerCase();
            String adDesc = ad.getDescription().toLowerCase();
            String adUser = ad.getAccount().toLowerCase();
            ArrayList<String> adTags = tagsToLowerCase(ad.getTagsList());

            int match = 0;

            for(int i=0; i < inputArray.length; i++) {
                if (adName.contains(activeTag) || adDesc.contains(activeTag) || adUser.contains(activeTag) || adTags.contains(activeTag)){

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
}
