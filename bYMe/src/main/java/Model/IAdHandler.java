package Model;

import java.util.Map;

/**
 *@Author Alexander Huang
 * Used by:Byme
 */
public interface IAdHandler {
    void loadAds(Map<String, Ad> ads);

    void saveAds(Map<String, Ad> ads);

}
