package Services;

import Model.Ad;
import Model.IAdHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AdHandler implements IAdHandler {

    private static AdHandler singleton;

    private AdHandler(){

    }

    public static AdHandler getInstance(){
        if (singleton==null){
            singleton = new AdHandler();
        }
        return singleton;
    }


    private String getAdsFilePath(){
        return "data" + File.separatorChar + "ads.json";
    }

    public void loadAds(HashMap<String, Ad> ads){
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

        } catch(IOException exception){
            exception.printStackTrace();
        } catch (ParseException excpetion){
            excpetion.printStackTrace();
        }
    }

    public void saveAds(HashMap<String, Ad> ads){
        JSONArray jsonList = new JSONArray();


        Iterator iterator = ads.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry account = (Map.Entry) iterator.next();
            Ad ad = (Ad) account.getValue();
            jsonList.add(convertToJSONObject(ad));
        }

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

        return object;
    }

    private Ad parseJSONObject(JSONObject obj){
        return new Ad((String)obj.get("title"),Integer.valueOf(String.valueOf(obj.get("price"))),(String)obj.get("description"),(String)obj.get("location"),(String)obj.get("adId"), (String)obj.get("account"));
    }




}
