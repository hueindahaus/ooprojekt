package Model;

import java.util.Map;

public interface IAdHandler {
    void loadAds(Map<String,Ad> ads);
    void saveAds(Map<String,Ad> ads);

}
