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
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public Request(String sender, String receiver, String ad, String message, String dateString) throws ParseException {
        this.sender = sender;
        this.receiver = receiver;
        this.ad = ad;
        this.message = message;
        this.date = dateFormat.parse(dateString);
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
}
