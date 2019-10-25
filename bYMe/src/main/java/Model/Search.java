package Model;

import java.util.*;

/**
 * A class that handles the search logic.
 * @Author Joel Jönsson, Johan Gottlander, Adam Jawad
 */


public final class Search {

    private static String activeTag = "";
    private static String newActiveTag = "";
    private Search() {
    }


    /**
     * The method takes in input from the user.
     * The input from the user and the name, description and user from all ads are changed to lowercase at the start.
     * If activetags are empty the search will only look for ads that have a matching description, name, tags or username.
     * If there is an active tag, the method will only look for ads that also include that tag.
     * @param input The data that the user types into the searchfield.
     * @param ads Map for all the available ads.
     * @return
     */
    public static List<Ad> findAds(String input, Map<String, Ad> ads) {
        List<Ad> result = new ArrayList();
        String[] inputArray = input.toLowerCase(Locale.ENGLISH).split(" ");

        for (Map.Entry<String, Ad> stringAdEntry : ads.entrySet()) {
            Ad ad = (Ad) ((Map.Entry) stringAdEntry).getValue();
            String adName = ad.getTitle().toLowerCase(Locale.ENGLISH);
            String adDesc = ad.getDescription().toLowerCase(Locale.ENGLISH);
            String adUser = ad.getAccount().toLowerCase(Locale.ENGLISH);
            List<String> adTags = tagsToLowerCase(ad.getTagsList());

            int match = 0;

            for (String s : inputArray) {
                if (activeTag.equals("")) {
                    if (adName.contains(s) || adDesc.contains(s) || adUser.contains(s)
                            || adTags.contains(s)) {
                        match++;
                    }
                } else if (adTags.contains(activeTag)) {
                    if (adName.contains(s) || adDesc.contains(s) || adUser.contains(s)
                            || adTags.contains(s)) {
                        match++;
                    }
                }
            }
            if (match == inputArray.length) {
                result.add(ad);
            }
        }
        return result;
    }


    private static List<String> tagsToLowerCase(List<String> tags) {
        ListIterator<String> iterator = tags.listIterator();
        while (iterator.hasNext()) {
            iterator.set(iterator.next().toLowerCase(Locale.ENGLISH));
        }
        return tags;
    }

    /**
     * Activetags represent the tags that are chosen by the user. Used for filtering.
     * @return
     */
    static public String getActiveTag() {
        return activeTag;
    }

    static public void setActiveTag(String activeTag) {
        Search.activeTag = activeTag;
    }

    static public String getNewActiveTag() {
        return newActiveTag;
    }

    static public void setNewActiveTag(String newActiveTag) {
        Search.newActiveTag = newActiveTag;
    }

}