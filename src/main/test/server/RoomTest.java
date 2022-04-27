package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    Room room;
    Channel defaultChannel;
    Chat normalUserChat;
    User admin = new User("Tom", "admin_url", "I'm Tom!");
    User normalUser = new User("Alex", "alex_url", "I'm Alex!");
    final int INITIAL_NUM_OF_USERS = 2; // admin + normal user
    final int INITIAL_NUM_OF_CHANNELS = 1;

    @BeforeEach
    void setUp() {
        /* Create a room */
        room = new Room("Test Room", admin);
        /* As an admin, add one normal user to the room */
        room.addUser(admin.getUserName(), normalUser, Role.normal);
        /* Get the default channel that is generated on room creation */
        defaultChannel = (Channel) room.getChannels().values().toArray()[0];
        /* As a normal user, send a chat in the default channel */
        defaultChannel.sendChat(normalUser.getUserName(), "Hello!");
        /* Get the chat sent */
        normalUserChat = defaultChannel.getChats().get(0);
    }

    @Test
    void adminCanAddChannel() {
        /* Add channel operation should succeed */
        assertTrue(room.addChannel(admin.getUserName(), "New Channel"));
        /* Check that the number of channels in the room increased */
        assertEquals(INITIAL_NUM_OF_CHANNELS + 1, room.getChannels().size());
    }

    @Test
    void adminCanDeleteChannel() {
        /* Delete channel operation should succeed */
        assertTrue(room.deleteChannel(admin.getUserName(), defaultChannel.getId()));
        /* Check that the room no longer contains the deleted channel */
        assertFalse(room.getChannels().containsValue(defaultChannel));
    }

    @Test
    void adminCanAddUser() {
        /* Create a user to add to the room */
        User newUser = new User("Bob", "bob_url", "I'm Bob!");
        /* Add user operation should succeed */
        assertTrue(room.addUser(admin.getUserName(), newUser, Role.normal));
        /* Get users in the room */
        Collection<User> users = room.getUsers().values();
        /* Check that the number of users in the room increased */
        assertEquals(INITIAL_NUM_OF_USERS + 1, users.size());
        /* Check that the users list contains the new user */
        assertTrue(users.contains(newUser));
    }

    @Test
    void adminCanRemoveUser() {
        /* Remove user operation should succeed */
        assertTrue(room.removeUser(admin.getUserName(), normalUser.getUserName()));
        /* Check the number of users if the room */
        assertEquals(INITIAL_NUM_OF_USERS - 1, room.getUsers().size());
        /* Check that the deleted user is not in the room */
        boolean isDeletedUserInRoom =
            room.getUsers().values().stream().map(User::getUserName).toList().contains(normalUser.getUserName());
        assertFalse(isDeletedUserInRoom);
    }

    @Test
    void normalUserCannotRemoveAnotherUser() {
        /* Remove user operation should fail */
        assertFalse(room.removeUser(normalUser.getUserName(), admin.getUserName()));
        /* Check the number of users if the room is unchanged */
        assertEquals(INITIAL_NUM_OF_USERS, room.getUsers().size());
        /* Check that the user is still in the room */
        boolean isDeletedUserInRoom =
                room.getUsers().values().stream().map(User::getUserName).toList().contains(admin.getUserName());
        assertTrue(isDeletedUserInRoom);
    }

    @Test
    void adminCanDeleteChat() {
        /* Delete chat operation should succeed */
        assertTrue(room.deleteChat(admin.getUserName(), defaultChannel.getId(), normalUserChat.getId()));
        /* Check that the default channel no longer contains the deleted chat */
        assertFalse(defaultChannel.getChats().contains(normalUserChat));
    }

    @Test
    void normalUserCanDeleteTheirOwnChats() {
        /* Delete chat operation should succeed */
        assertTrue(room.deleteChat(normalUser.getUserName(), defaultChannel.getId(), normalUserChat.getId()));
        /* Check that the default channel no longer contains the deleted chat */
        assertFalse(defaultChannel.getChats().contains(normalUserChat));
    }

    @Test
    void normalUserCannotDeleteChatsSentByAnotherUser() {
        /* As Tom (another user), send a chat */
        room.sendChat(defaultChannel.getId(), admin.getUserName(), "Yo!");
        /* Get the chat sent */
        Chat tomChat = null;
        for (Chat chat : room.getChatsInChannel(defaultChannel.getId()))
            if (chat.getSenderName() == admin.getUserName()) tomChat = chat;
        /* As a normal user (Alex), try to delete Tom's message. This should fail */
        assertFalse(room.deleteChat(normalUser.getUserName(), defaultChannel.getId(), tomChat.getId()));
        /* Check that the default channel still has Tom's chat */
        assertTrue(defaultChannel.getChats().contains(tomChat));
    }
}