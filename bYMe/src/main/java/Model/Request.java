package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class which is used to create request objects.
 * A request object is used when a user wants to send a request for an ad.
 * @author Joel Jönsson
 *
 * Uses: RequestState
 * Used by: Ad, AdHandler, Byme, MenuController, RequestHandler, RequestItem
 */

public class Request {
    private String sender;
    private String receiver;
    private String ad;
    private String message;
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm");
    private RequestState state;

    /**
     * Constructor which is called to create a new requet object..
     * @param sender    The username of the account that sends the request.
     * @param receiver  The username of the account that receives the request.
     * @param ad        The ad that is requested.
     * @param message   A message from the sender to the receiver.
     * @param dateString The date of when the sender wants to use the service of the ad.
     * @param state     The state of the ad. Enum which can be PENDING, ACCEPTED, DECLINED or ACCEPTEDANDDONE.
     * @throws ParseException
     */
    public Request(String sender, String receiver, String ad, String message, String dateString, RequestState state) throws ParseException {
        this.sender = sender;
        this.receiver = receiver;
        this.ad = ad;
        this.message = message;
        this.date = dateFormat.parse(dateString);
        this.state = state;
    }

    /**
     * Getter for the sender.
     * @return Returns string value of the senders username.
     */

    public String getSender() {
        return sender;
    }

    /**
     * Getter for the receiver.
     * @return Returns string value of the receivers username.
     */

    public String getReceiver() {
        return receiver;
    }

    /**
     * Getter for the ad.
     * @return Returns the ad that is requested.
     */

    public String getAd() {
        return ad;
    }

    /**
     * Getter for the message.
     * @return Returns string value of the senders message.
     */

    public String getMessage() {
        return message;
    }

    /**
     * Getter for the date.
     * @return Returns date of the request.
     */

    public Date getDate() {
        return date;
    }

    /**
     * Getter for the date in String format. Converts the date to a string which
     * makes it easier to store and handle the data in JSON.
     * @return Returns string value of the date.
     */
    public String getDateString() {
        return dateFormat.format(date);
    }

    /**
     * Getter for the state of a request.
     * @return Returns the state.
     */
    public RequestState getState() {
        return state;
    }

    /**
     * Setter for the state of a request.
     * @param state The state that should be set.
     */

    public void setState(RequestState state) {
        this.state = state;
    }

    /**
     * Checks if a request is accepted.
     * @return A boolean which is true or false based on if the request is accepted or not.
     */

    public boolean isAccepted() {
        return state == RequestState.ACCEPTED;
    }

    /**
     * Checks if a request is Accepted and done.
     * @return A boolean which is true or false based on if the request is accepted and done or not.
     */

    public boolean isAcceptedAndDone() {
        return state == RequestState.ACCEPTEDANDDONE;
    }

    /**
     * Checks if a request is pending.
     * @return A boolean which is true or false based on if the request is pending or not.
     */

    public boolean isPending() {
        return state == RequestState.PENDING;
    }

    /**
     * Checks if a request is declined.
     * @return A boolean which is true or false based on if the request is declined or not.
     */

    public boolean isDeclined() {
        return state == RequestState.DECLINED;
    }

}
