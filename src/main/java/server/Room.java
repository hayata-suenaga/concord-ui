package server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Room implements Serializable {
    private String id;
    private String name;
    private Map<String, User> users = new HashMap<>();
    private Map<String, Role> roles = new HashMap<>();
    private Map<String, Channel> channels = new HashMap<>();

    public Room(String name, User user) {
        id = UUID.randomUUID().toString();
        this.name = name;
        /* Add the user who created the room as an admin */
        users.put(user.getUserName(), user);
        roles.put(user.getUserName(), Role.admin);
        /* Add a default channel */
        addChannel(user.getUserName(), "general");
    }

    public boolean addChannel(String actorName, String channelName) {
        if (!roles.get(actorName).canCreateChannel) return false;
        Channel channel = new Channel(channelName);
        channels.put(channel.getId(), channel);
        return true;
    }

    public boolean deleteChannel(String actorName, String channelId) {
        if (!roles.get(actorName).canDeleteChannel) return false;
        channels.remove(channelId);
        return true;
    }

    public boolean addUser(String actorName, User userToAdd, Role role) {
        if (!roles.get(actorName).canAddUser) return false;
        users.put(userToAdd.getUserName(), userToAdd);
        roles.put(userToAdd.getUserName(), role);
        return true;
    }

    public boolean removeUser(String actorName, String userToRemove) {
        if (!roles.get(actorName).canRemoveUser) return false;
        users.remove(userToRemove);
        roles.remove(userToRemove);
        return true;
    }

    public boolean sendChat(String channelId, String senderName, String text) {
        return channels.get(channelId).sendChat(senderName, text);
    }

    public boolean deleteChat(String actorName, String channelId, String chatId) {
        /* Get the channel from which to delete a chat */
        Channel channel = channels.get(channelId);
        /* Check if the actor is the sender of the chat to delete */
        boolean isSender = channels.get(channelId).getChat(chatId).getSenderName().equals(actorName);
        /* Check if the actor has the permission to delete other users' chats */
        boolean canDeleteChat = roles.get(actorName).canDeleteChat;
        /* Check if the actor that invoked removeUser has an appropriate role */
        if (!isSender && !canDeleteChat) return false;
        channel.deleteChat(chatId);
        return true;
    }

    /* Custom getters */
    public Role getRoleOfUser(String userName) { return roles.get(userName); }
    public List<Chat> getChatsInChannel(String channelId) { return channels.get(channelId).getChats(); }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Room another)) return false;
        return id.equals(another.id)
                && name.equals(another.name)
                && channels.equals(another.channels)
                && roles.equals(another.roles)
                && users.equals(another.users);
    }

    /* Default Java Beans constructor. Should not be used */
    public Room() { id = UUID.randomUUID().toString(); name = ""; }
    /* Java Beans getter. Not intended to be used */
    public Map<String, Role> getRoles() { return roles; }
    /* Java Beans setters Not intended to be used */
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUsers(Map<String, User> users) { this.users = users; }
    public void setRoles(Map<String, Role> roles) { this.roles = roles; }
    public void setChannels(Map<String, Channel> channels) { this.channels = channels; }
    /* Getters for use */
    public String getId() { return id; }
    public String getName() { return name; }
    public Map<String, User> getUsers() { return users; }
    public Map<String, Channel> getChannels() { return channels; }
}
