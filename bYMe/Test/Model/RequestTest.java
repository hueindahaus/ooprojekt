package Model;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestTest {

    Request request;

    {
        try {
            request = new Request("user1", "user2", "testAd", "Hello", "2019-10-01/12:00", RequestState.PENDING);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSender() {
        assertEquals("user1", request.getSender());
    }

    @Test
    void getReceiver() {
        assertEquals("user2", request.getReceiver());
    }

    @Test
    void getAd() {
        assertEquals("testAd", request.getAd());
    }

    @Test
    void getMessage() {
        assertEquals("Hello", request.getMessage());
    }

    @Test
    void getDate() {
        assertEquals("user1", request.getSender());
    }

    @Test
    void getDateString() {
        assertEquals("2019-10-01/12:00", request.getDateString());
    }

    @Test
    void getState() {
        assertEquals(RequestState.PENDING, request.getState());
    }

    @Test
    void setState() {
        request.setState(RequestState.ACCEPTED);
        assertEquals(RequestState.ACCEPTED, request.getState());
    }

    @Test
    void isAccepted() {
        request.setState(RequestState.ACCEPTED);
        assertEquals(true, request.isAccepted());

    }

    @Test
    void isAcceptedAndDone() {
        request.setState(RequestState.ACCEPTEDANDDONE);
        assertEquals(true, request.isAcceptedAndDone());
    }

    @Test
    void isRequested() {
        request.setState(RequestState.PENDING);
        assertEquals(true, request.isPending());
    }

    @Test
    void isDeclined() {
        request.setState(RequestState.DECLINED);
        assertEquals(true, request.isDeclined());
    }
}