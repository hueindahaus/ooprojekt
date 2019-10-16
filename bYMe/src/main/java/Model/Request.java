package Model;

public class Request {
    private String sender;
    private String receiver;
    private String ad;
    private String message;

    public Request(String sender, String receiver, String ad, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.ad = ad;
        this.message = message;
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
}
