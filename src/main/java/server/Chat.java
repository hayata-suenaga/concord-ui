package server;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Chat implements Serializable {
    private String id;
    private String senderName;
    private String message;
    private Date timestamp;
    private boolean isPinned;

    public Chat(String senderName, String message) {
        id = UUID.randomUUID().toString();
        this.senderName = senderName;
        this.message = message;
        /* Set timestamp to the current time */
        timestamp = new Date();
    }

    public void togglePin() { isPinned = !isPinned; }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Chat another)) return false;
        return  id.equals(another.id)
                && message.equals(another.message)
                && timestamp.equals(another.timestamp)
                && isPinned == another.isPinned;
    }

    /* Java Beans default constructor. Not intended to be used */
    public Chat() { id = UUID.randomUUID().toString(); }
    /* Java Beans setters. Not intended to be used */
    public void setId(String id) { this.id = id; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public void setPinned(boolean pinned) { this.isPinned = pinned; }
    /* Getters and setters for use */
    public String getId() { return id; }
    public String getSenderName() { return senderName; }
    public String getMessage() { return message; }
    public Date getTimestamp() { return timestamp; }
    public boolean getIsPinned() { return isPinned; }
    public void setMessage(String message) { this.message = message; }
}
