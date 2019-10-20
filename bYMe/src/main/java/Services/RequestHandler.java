package Services;

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



public class RequestHandler {

    private static RequestHandler singleton;

    private RequestHandler(){

    }

    public static RequestHandler getInstance(){
        if (singleton==null){
            singleton = new RequestHandler();
        }
        return singleton;
    }


    private String getRequestsFilePath(){
        return "data" + File.separatorChar + "requests.json";
    }

    public ArrayList<Request> loadRequests(String adId){
        ArrayList<Request> requests = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try{
            FileReader fileaReader = new FileReader(getRequestsFilePath());
            File file = new File(getRequestsFilePath());

            if(file.length() > 0) {
                Object obj = parser.parse(fileaReader);
                JSONArray objects = (JSONArray) obj;

                for (Object object : objects) {
                        Request request = parseJSONObject((JSONObject) object);
                        if(request.getAd().equals(adId)) {
                            requests.add((Request) request);
                        }
                }
            }

        } catch(IOException exception){
            exception.printStackTrace();
        } catch (ParseException excpetion){
            excpetion.printStackTrace();
        } catch (java.text.ParseException exception) {
            exception.printStackTrace();
        }
        return requests;
    }

    public void saveRequests(ArrayList<Request> requests){
        JSONArray jsonList = new JSONArray();

        for(Request request : requests){
            jsonList.add(convertToJSONObject(request));
        }

        try{
            FileWriter file = new FileWriter(getRequestsFilePath());
            file.write(jsonList.toString());
            file.flush();
        } catch (IOException exception){
            System.out.println("Can't save list with requests");
            exception.printStackTrace();
        }

    }

    private JSONObject convertToJSONObject(Request request){
        JSONObject object = new JSONObject();
        object.put("sender", request.getSender());
        object.put("receiver", request.getReceiver());
        object.put("ad", request.getAd());
        object.put("message", request.getMessage());
        object.put("date", request.getDateString());
        object.put("state", request.getState());

        return object;
    }

    private Request parseJSONObject(JSONObject obj) throws java.text.ParseException {
        return new Request((String)obj.get("sender"), (String)obj.get("receiver"), (String)obj.get("ad"), (String)obj.get("message"), (String)obj.get("date"), ((Long)obj.get("state")).intValue());
    }




}
