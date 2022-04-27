package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {
    private String userName;
    private String displayURL;
    private String profile;
    private boolean isOnline;
    private List<String> roomIds = new ArrayList<>();

    public User(String userName, String displayURL, String profile) {
        this.userName = userName;
        this.displayURL = displayURL;
        this.profile = profile;
        /* When user is instantiated, the user is online */
        this.isOnline = true;
    }

    public void joinRoom(String roomId) { roomIds.add(roomId); }
    public void leaveRoom(UUID roomId) { roomIds.remove(roomId); }

    public void toggleOnline() { isOnline = !isOnline; }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User another)) return false;
        return this.userName.equals(another.getUserName());
    }

    /* Java Beans default constructor. Not intended to be used */
    public User() { userName = ""; displayURL = ""; profile = ""; isOnline = false; }
    /* Java Beans setters. Not intended to be used */
    public void setUserName(String userName) { this.userName = userName; }
    public void setDisplayURL(String displayURL) { this.displayURL = displayURL; }
    public void setProfile(String profile) { this.profile = profile; }
    public void setIsOnline(boolean online) { isOnline = online; }
    public void setRoomIds(List<String> roomIds) { this.roomIds = roomIds; }
    /* Getters */
    public String getUserName() { return userName; }
    public String getDisplayURL() { return displayURL; }
    public String getProfile() { return profile; }
    public boolean getIsOnline() { return isOnline; }
    public List<String> getRoomIds() { return roomIds; }
}
