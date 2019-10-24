package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Request {
    private String sender;
    private String receiver;
    private String ad;
    private String message;
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm");
    private RequestState state;

    public Request(String sender, String receiver, String ad, String message, String dateString, RequestState state) throws ParseException {
        this.sender = sender;
        this.receiver = receiver;
        this.ad = ad;
        this.message = message;
        this.date = dateFormat.parse(dateString);
        this.state = state;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getAd() {
        return ad;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return dateFormat.format(date);
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }


    public boolean isAccepted() {
        return state == RequestState.ACCEPTED;
    }

    public boolean isAcceptedAndDone() {
        return state == RequestState.ACCEPTEDANDDONE;
    }

    public boolean isPending() {
        return state == RequestState.PENDING;
    }

    public boolean isDeclined() {
        return state == RequestState.DECLINED;
    }

}
