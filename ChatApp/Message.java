import java.io.Serializable;
import java.util.*;

public class Message implements Serializable {

    private String messageText;
    private Calendar time;
    private String author;

    Message(String messageText, Calendar time, String author) {
        this.time = time;
        this.messageText = messageText;
        this.author = author;
    }

    public void setId(String author) {
        this.author = author;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getId() {
        return author;
    }

    public String getMessageText() {
        return messageText;
    }

    public Calendar getTime() {
        return time;
    }

    @Override
    public String toString() {
        String formattedTime = getFormattedTime();
        return author + " " + formattedTime + "\n" + messageText;
    }

    public String getFormattedTime() {
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);

        // Format the time as "10:04"
        String formattedTime = String.format("%02d:%02d", hour, minute);
        return formattedTime;
    }
}
