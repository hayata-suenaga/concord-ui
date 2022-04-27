package server;

import org.junit.jupiter.api.Test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncodingTest {

    @Test
    void userEncoding() throws Exception {
        String fileName = "user.xml";
        new File(fileName).createNewFile();

        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
        User original = new User("tom", "tom_url", "I'm Tom!");
        encoder.writeObject(original);
        encoder.close();

        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
        User decoded = (User) decoder.readObject();
        decoder.close();

        assertEquals(original, decoded);
    }

    @Test
    void chatEncoding() throws Exception {
        String fileName = "chat.xml";
        new File(fileName).createNewFile();

        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
        Chat original = new Chat("tom", "yo!");
        encoder.writeObject(original);
        encoder.close();

        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
        Chat decoded = (Chat) decoder.readObject();
        decoder.close();

        System.out.println("original uuid: " + original.getId());
        System.out.println("decoded uuid: " + decoded.getId());
        System.out.println("original text: " + original.getMessage());
        System.out.println("decoded text: " + decoded.getMessage());
        assertEquals(original, decoded);
    }

    @Test
    void channelEncoding() throws Exception {
        String fileName = "channel.xml";
        new File(fileName).createNewFile();

        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
        Channel original = new Channel("Test Channel");
        encoder.writeObject(original);
        encoder.close();

        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
        Channel decoded = (Channel) decoder.readObject();
        decoder.close();

        System.out.println("original uuid: " + original.getId());
        System.out.println("decoded uuid: " + decoded.getId());
        assertEquals(original, decoded);
    }

    @Test
    void roomEncoding() throws Exception {
        String fileName = "room.xml";
        new File(fileName).createNewFile();

        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
        User testUser = new User("tom", "tom_url", "I'm Tom!");
        Room original = new Room("Test Room", testUser);
        encoder.writeObject(original);
        encoder.close();

        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
        Room decoded = (Room) decoder.readObject();
        decoder.close();

        System.out.println("original uuid: " + original.getId());
        System.out.println("decoded uuid: " + decoded.getId());
        assertEquals(original, decoded);
    }
}
