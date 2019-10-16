package Model;

import java.util.HashMap;

public interface IRequestHandler {
    void loadRequests(HashMap<Integer, Request> accounts);
    void saveRequests(HashMap<Integer, Request> accounts);
}
