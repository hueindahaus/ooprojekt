package Services;

import Model.Ad;
import Model.IAdHandler;
import Model.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.util.*;


public final class AdHandler implements IAdHandler {

    private static AdHandler singleton;

    private RequestHandler requestHandler = RequestHandler.getInstance();

    private AdHandler(){

    }

    public static AdHandler getInstance(){
        if (singleton==null){
            singleton = new AdHandler();
        }
        return singleton;
    }


    private String getAdsFilePath(){
        return "src" + File.separatorChar + "main" + File.separatorChar  + "java" + File.separatorChar + "Services" + File.separatorChar +"data" + File.separatorChar + "ads.json";
    }

    @Override
    public void loadAds(Map<String, Ad> ads){
        JSONParser parser = new JSONParser();

        try{
            FileReader fileaReader = new FileReader(getAdsFilePath());
            File file = new File(getAdsFilePath());

            if(file.length() > 0) {
                Object obj = parser.parse(fileaReader);
                JSONArray objects = (JSONArray) obj;

                for (Object object : objects) {
                        Ad ad = parseJSONObject((JSONObject) object);
                        ads.put(ad.getAdId(), (Ad) ad);
                }
            }

        } catch(IOException | ParseException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void saveAds(Map<String, Ad> ads){
        JSONArray jsonList = new JSONArray();
        ArrayList<Request> requests = new ArrayList<>();

        for(Ad ad: ads.values()){
            requests.addAll(ad.getRequests());
            jsonList.add(convertToJSONObject(ad));
        }

        requestHandler.saveRequests(requests);

        try{
            FileWriter file = new FileWriter(getAdsFilePath());
            file.write(jsonList.toString());
            file.flush();
        } catch (IOException exception){
            System.out.println("Can't save list with ads");
            exception.printStackTrace();
        }

    }

    private JSONObject convertToJSONObject(Ad ad){
        JSONObject object = new JSONObject();
        object.put("adId", ad.getAdId());
        object.put("title", ad.getTitle());
        object.put("description",ad.getDescription());
        object.put("location", ad.getLocation());
        object.put("price", ad.getPrice());
        object.put("account", ad.getAccount());
        object.put("tagsList", ad.getTagsList());

        return object;
    }

    private Ad parseJSONObject(JSONObject obj){
        Ad ad = new Ad((String)obj.get("title"),Integer.valueOf(String.valueOf(obj.get("price"))),(String)obj.get("description"),(String)obj.get("location"),(String)obj.get("adId"), (String)obj.get("account"));
        List<Request> requests = requestHandler.loadRequests(ad.getAdId());
        ad.setRequests(requests);
        ad.setTagsList((List<String>)obj.get("tagsList"));
        return ad;
    }


}
