package Services;

import Model.Request;
import Model.RequestState;
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


/**
 * Read and writes to requests.json, responsible for storing Requests
 * When the application starts it reads from the json files and creates Request-objects with the parameters from the json-file
 * When the application is closed it writes all Request-object's parameters to the json-file so that they can be created when the application is opened.
 * @author Alexander Huang, Joel Jönsson
 *
 * Uses: Request, RequestState.
 * Used by: AdHandler.
 */
public final class RequestHandler {

    private static RequestHandler singleton;

    private RequestHandler() {

    }

    /**
     * Singleton getInstance() method.
     * @return the singleton object.
     */
    public static RequestHandler getInstance() {
        if (singleton == null) {
            singleton = new RequestHandler();
        }
        return singleton;
    }


    private String getRequestsFilePath() {
        return "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "Services" + File.separatorChar + "data" + File.separatorChar + "requests.json";
    }

    List<Request> loadRequests(String adId) {
        List<Request> requests = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(getRequestsFilePath());
            File file = new File(getRequestsFilePath());

            if (file.length() > 0) {
                Object obj = parser.parse(fileReader);
                JSONArray objects = (JSONArray) obj;

                for (Object object : objects) {
                    Request request = parseJSONObject((JSONObject) object);
                    if (request.getAd().equals(adId)) {
                        requests.add(request);
                    }
                }
            }

        } catch (IOException | ParseException | java.text.ParseException exception) {
            exception.printStackTrace();
        }
        return requests;
    }

    void saveRequests(List<Request> requests) {
        JSONArray jsonList = new JSONArray();

        for (Request request : requests) {
            jsonList.add(convertToJSONObject(request));
        }

        try {
            FileWriter file = new FileWriter(getRequestsFilePath());
            file.write(jsonList.toString());
            file.flush();
        } catch (IOException exception) {
            System.out.println("Can't save list with requests");
            exception.printStackTrace();
        }

    }

    private JSONObject convertToJSONObject(Request request) {
        JSONObject object = new JSONObject();
        object.put("sender", request.getSender());
        object.put("receiver", request.getReceiver());
        object.put("ad", request.getAd());
        object.put("message", request.getMessage());
        object.put("date", request.getDateString());
        object.put("state", request.getState().name());

        return object;
    }

    private Request parseJSONObject(JSONObject obj) throws java.text.ParseException {
        return new Request((String) obj.get("sender"), (String) obj.get("receiver"), (String) obj.get("ad"), (String) obj.get("message"), (String) obj.get("date"), Enum.valueOf(RequestState.class, (String) obj.get("state")));
    }


}
