package Model;

import java.util.HashMap;

public interface IAdHandler {
    void loadAds(HashMap<String,Ad> ads);
    void saveAds(HashMap<String,Ad> ads);

}
