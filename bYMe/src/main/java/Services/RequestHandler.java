package Services;

import Model.IRequestHandler;
import Model.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RequestHandler implements IRequestHandler {

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

    public void loadRequests(HashMap<Integer, Request> requests){
        JSONParser parser = new JSONParser();
        int index = 0;

        try{
            FileReader fileaReader = new FileReader(getRequestsFilePath());
            File file = new File(getRequestsFilePath());

            if(file.length() > 0) {
                Object obj = parser.parse(fileaReader);
                JSONArray objects = (JSONArray) obj;

                for (Object object : objects) {
                        Request request = parseJSONObject((JSONObject) object);
                        requests.put(index, (Request) request);
                        index++;
                }
            }

        } catch(IOException exception){
            exception.printStackTrace();
        } catch (ParseException excpetion){
            excpetion.printStackTrace();
        }
    }

    public void saveRequests(HashMap<Integer, Request> requests){
        JSONArray jsonList = new JSONArray();


        Iterator iterator = requests.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry request = (Map.Entry) iterator.next();
            Request currentRequest= (Request) request.getValue();
            jsonList.add(convertToJSONObject(currentRequest));
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

        return object;
    }

    private Request parseJSONObject(JSONObject obj){
        return new Request((String)obj.get("sender"), (String)obj.get("receiver"), (String)obj.get("ad"), (String)obj.get("message"));
    }




}
