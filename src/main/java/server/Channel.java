package server;

import java.io.Serializable;
import java.util.*;

public class Channel implements Serializable {
    private String id;
    private String name;
    private Map<String, Chat> chats = new HashMap<>();

    public Channel(String name) {
        id = UUID.randomUUID().toString();
        this.name = name;
    }

    public boolean sendChat(String senderName, String text) {
        Chat chat = new Chat(senderName, text);
        chats.put(chat.getId(), chat);
        return true;
    }

    public void updateChat(String id, String newMessage) { chats.get(id).setMessage(newMessage); }

    public void deleteChat(String id) { chats.remove(id); }

    public void togglePin(String id) { chats.get(id).togglePin(); }

    /* Custom getter */
    public Chat getChat(String id) { return chats.get(id); }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Channel another)) return false;
        return id.equals(another.id)
                && name.equals(another.name)
                && chats.equals(another.chats);
    }

    /* Java Beans default constructor */
    public Channel() { id = UUID.randomUUID().toString(); }
    /* Java Beans setters. Not intended to be used */
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setChats(Map<String, Chat> chats) { this.chats = chats; }
    /* Getters */
    public String getId() { return id; }
    public String getName() { return name; }
    //TODO: Might need to change this to Map
    public List<Chat> getChats() { return new ArrayList<>(chats.values()); }
}
