package server;

import java.io.Serializable;

public class Role implements Serializable {
    public boolean canDeleteRoom;
    public boolean canAddUser;
    public boolean canRemoveUser;
    public boolean canCreateChannel;
    public boolean canDeleteChannel;
    public boolean canDeleteChat;

    public static Role admin =
            new Role(true, true, true, true, true, true);
    public static Role moderator =
            new Role(false, true, true, true, true, true);
    public static Role normal =
            new Role(false, false, false, false, false, false);

    private Role(
            boolean canDeleteRoom,
            boolean canAddUser,
            boolean canRemoveUser,
            boolean canCreateChannel,
            boolean canDeleteChannel,
            boolean canDeleteChat
            )
    {
        this.canDeleteRoom = canDeleteRoom;
        this.canAddUser = canAddUser;
        this.canRemoveUser = canRemoveUser;
        this.canCreateChannel = canCreateChannel;
        this.canDeleteChannel = canDeleteChannel;
        this.canDeleteChat = canDeleteChat;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (o == this) return true;
//        if (!(o instanceof Role another)) return false;
//        return
//    }

    /* Java Beans default constructor */
    public Role() {
        canDeleteRoom = false;
        canAddUser = false;
        canRemoveUser = false;
        canCreateChannel = false;
        canDeleteChannel = false;
        canDeleteChat = false;
    }

    /* Java Beans getters and setters. Not intended to be used */
    public static Role getAdmin() { return admin; }
    public static Role getModerator() { return moderator; }
    public static Role getNormal() { return normal; }
    public static void setAdmin(Role admin) { Role.admin = admin; }
    public static void setModerator(Role moderator) { Role.moderator = moderator; }
    public static void setNormal(Role normal) { Role.normal = normal; }
    public boolean isCanDeleteRoom() { return canDeleteRoom; }
    public boolean isCanAddUser() { return canAddUser; }
    public boolean isCanRemoveUser() { return canRemoveUser; }
    public boolean isCanCreateChannel() { return canCreateChannel; }
    public boolean isCanDeleteChannel() { return canDeleteChannel; }
    public boolean isCanDeleteChat() { return canDeleteChat; }
    public void setCanDeleteRoom(boolean canDeleteRoom) { this.canDeleteRoom = canDeleteRoom; }
    public void setCanAddUser(boolean canAddUser) { this.canAddUser = canAddUser; }
    public void setCanRemoveUser(boolean canRemoveUser) { this.canRemoveUser = canRemoveUser; }
    public void setCanCreateChannel(boolean canCreateChannel) { this.canCreateChannel = canCreateChannel; }
    public void setCanDeleteChannel(boolean canDeleteChannel) {this.canDeleteChannel = canDeleteChannel; }
    public void setCanDeleteChat(boolean canDeleteChat) { this.canDeleteChat = canDeleteChat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role another)) return false;
        return canDeleteRoom == another.canDeleteRoom
                && canAddUser == another.canAddUser
                && canRemoveUser == another.canRemoveUser
                && canCreateChannel == another.canCreateChannel
                && canDeleteChannel == another.canDeleteChannel
                && canDeleteChat == another.canDeleteChat;
    }
}
