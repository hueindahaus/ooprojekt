package Model;

import com.sun.xml.internal.bind.v2.TODO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Request {
    private String sender;
    private String receiver;
    private String ad;
    private String message;
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy-hh:mm");
    private int state; // 0 = requested, 1 = accepted, 2 = declined, 3 = accepted and done

    public Request(String sender, String receiver, String ad, String message, String dateString, int state) throws ParseException {
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void remove(){
        //Fult, fixa nedan :) Behövs också någon typ av update!
        Byme byme = Byme.getInstance(null, null);
        HashMap<String, Ad> ads = byme.getAds();
        Ad currentAd = ads.get(ad);
        ArrayList<Request> requests = currentAd.getRequests();
        requests.remove(this);
        currentAd.setRequests(requests);
    }
}
