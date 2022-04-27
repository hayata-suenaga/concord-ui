package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChannelTest {
    /* Channel against which to test */
    Channel channel;
    /* Test messages */
    String[] messages = {"msg1", "msg2", "msg3", "msg4", "msg5"};

    @BeforeEach
    void setUp() {
        /* Create a new channel */
        channel = new Channel("Test Channel");
        /* Create a user who sends chats in the channel */
        User sender = new User("Tom", "test_url", "I'm Tom!");
        /* Send chats */
        for (String message : messages) { channel.sendChat(sender.getUserName(), message); }
    }

    @Test
    void sendChat() {
        /* Get all the chats in the channel */
        List<Chat> chats = channel.getChats();
        /* Extract message from each chat */
        List<String> chatMsg = chats.stream().map(chat -> chat.getMessage()).toList();
        /* Check the number of chats in the channel is correct */
        assertEquals(messages.length, chats.size());
        /* Check each chat message in the channel is correct */
        for (int i = 0; i < messages.length; i++) {
            assertTrue(chatMsg.contains(messages[i]));
        }
    }

    @Test
    void deleteChat() {
        /* Get the first chat */
        Chat chatToDelete = channel.getChats().get(0);
        /* Delete the first chat */
        channel.deleteChat(chatToDelete.getId());
        /* Get the updated chats */
        List<Chat> chats = channel.getChats();
        /* Check the number of chats after deletion */
        assertEquals(messages.length - 1, chats.size());
        /* Check that the channel doesn't contain the deleted chat */
        assertFalse(chats.contains(chatToDelete));
    }

    @Test
    void updateChat() {
        /* New message */
        final String newMessage = "This is new message!";
        /* Get the first chat */
        Chat chatToUpdate = channel.getChats().get(0);
        /* Update the message of the first chat */
        channel.updateChat(chatToUpdate.getId(), newMessage);
        /* Get the updated chats */
        List<Chat> chats = channel.getChats();
        /* Check that the number of chats is unchanged */
        assertEquals(messages.length, chats.size());
        /* Check that the first chat is correctly updated */
        assertEquals(newMessage, chats.get(0).getMessage());
    }

    @Test
    void togglePin() {
        /* Get the first chat */
        Chat chatToUpdate = channel.getChats().get(0);
        /* Toggle the pined status of the first chat */
        channel.togglePin(chatToUpdate.getId());
        /* Get the updated chats */
        List<Chat> chats = channel.getChats();
        /* Check that the number of chats is unchanged */
        assertEquals(messages.length, chats.size());
        /* Check that the first chat's pinned status is correctly toggled */
        assertTrue(chats.get(0).getIsPinned());
        /* Toggle the pinned status of the first chat again */
        channel.togglePin(chatToUpdate.getId());
        /* Check the pinned status is correctly toggled for the second time */
        assertFalse(chats.get(0).getIsPinned());
    }
}