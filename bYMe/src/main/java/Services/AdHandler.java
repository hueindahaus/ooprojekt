package Services;

import Model.Ad;
import Model.IAdHandler;
import Model.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Read and writes to ads.json, responsible for storing Ads
 * When the application starts it reads from the json files and creates Ad-objects with the parameters from the json-file.
 * Uses a RequestHandler to store requests in the correct ad.
 * When the application is closed it writes all Ad-object's parameters to the json-file so that they can be created when the application is opened.
 * @author Alexander Huang
 *
 * Uses: Ad, Request, RequestHandler.
 * Used by: MainController.
 */
public final class AdHandler implements IAdHandler {

    private static AdHandler singleton;

    private RequestHandler requestHandler = RequestHandler.getInstance();

    private AdHandler() {

    }

    /**
     * Singleton getInstance() method.
     * @return the singleton object.
     */
    public static AdHandler getInstance() {
        if (singleton == null) {
            singleton = new AdHandler();
        }
        return singleton;
    }


    private String getAdsFilePath() {
        return "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "Services" + File.separatorChar + "data" + File.separatorChar + "ads.json";
    }

    /**
     * Reads ads from ads.json and adds them to a Map of ads
     * @param ads Map of ads
     */
    @Override
    public void loadAds(Map<String, Ad> ads) {
        JSONParser parser = new JSONParser();

        try {
            FileReader fileaReader = new FileReader(getAdsFilePath());
            File file = new File(getAdsFilePath());

            if (file.length() > 0) {
                Object obj = parser.parse(fileaReader);
                JSONArray objects = (JSONArray) obj;

                for (Object object : objects) {
                    Ad ad = parseJSONObject((JSONObject) object);
                    ads.put(ad.getAdId(), (Ad) ad);
                }
            }

        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reads ads from a Map of ads and writes them to ads.json
     * @param ads Map of ads
     */
    @Override
    public void saveAds(Map<String, Ad> ads) {
        JSONArray jsonList = new JSONArray();
        ArrayList<Request> requests = new ArrayList<>();

        for (Ad ad : ads.values()) {
            requests.addAll(ad.getRequests());
            jsonList.add(convertToJSONObject(ad));
        }

        requestHandler.saveRequests(requests);

        try {
            FileWriter file = new FileWriter(getAdsFilePath());
            file.write(jsonList.toString());
            file.flush();
        } catch (IOException exception) {
            System.out.println("Can't save list with ads");
            exception.printStackTrace();
        }

    }

    private JSONObject convertToJSONObject(Ad ad) {
        JSONObject object = new JSONObject();
        object.put("adId", ad.getAdId());
        object.put("title", ad.getTitle());
        object.put("description", ad.getDescription());
        object.put("location", ad.getLocation());
        object.put("price", ad.getPrice());
        object.put("account", ad.getAccount());
        object.put("tagsList", ad.getTagsList());

        return object;
    }

    private Ad parseJSONObject(JSONObject obj) {
        Ad ad = new Ad((String) obj.get("title"), Integer.valueOf(String.valueOf(obj.get("price"))), (String) obj.get("description"), (String) obj.get("location"), (String) obj.get("adId"), (String) obj.get("account"));
        List<Request> requests = requestHandler.loadRequests(ad.getAdId());
        ad.setRequests(requests);
        ad.setTagsList((List<String>) obj.get("tagsList"));
        return ad;
    }


}
