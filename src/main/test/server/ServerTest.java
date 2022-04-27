package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@TestInstance(Lifecycle.PER_CLASS)
class ServerTest {

    static Registry registry;
    Server server;
    ConcordAPI clientProxy;
    Client tom;
    Client alex;

    /* Start RMI registry */
//    @BeforeAll
//    static void createRegistry() throws RemoteException { registry = LocateRegistry.createRegistry(1099); }

    @BeforeEach
    void setupData() {
        try {
            registry = LocateRegistry.createRegistry(2500);
            /* Instantiate a new server */
            server = new Server();
            registry.rebind("CONCORD", server);
            /* On the client side, get the proxy from the remote registry */
            clientProxy = (ConcordAPI) Naming.lookup("rmi://127.0.0.1:2500/CONCORD");
            /* Sign up as Tom, create a client for Tom, register the client as observer */
            tom = new Client(clientProxy, "tom", "tom_url", "I'm Tom", "tom_ps");
            /* Sign up as Alex, create a client for Alex, register the client as observer */
            alex = new Client(clientProxy, "alex", "alex_url", "I'm Alex", "alex_ps");
            /* Create a room as Tom */
            clientProxy.createRoom("Tom's room", "tom");
            /* Create a room as Alex */
            clientProxy.createRoom("Alex's room", "alex");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* Remove the server instance from the RMI registry */
    @AfterEach
    void unregister() throws Exception { registry.unbind("CONCORD"); }

    @Test
    void signup() throws RemoteException {
        assertTrue(clientProxy.getUsers().containsKey("tom"));
    }

//    @Test
//    void shouldLoginWithCorrectPassword() throws RemoteException {
//        User tom = clientProxy.login("tom", "tom_ps");
//        assertNotNull(tom);
//        assertEquals("tom", tom.getUserName());
//    }
//
//    @Test
//    void shouldNotLoginWithIncorrectPassword() throws RemoteException {
//        User tom = clientProxy.login("tom", "incorrect_ps");
//        assertNull(tom);
//    }
//
//    @Test
//    void getRoomsForUser() throws RemoteException {
//        Map<String, Room> rooms = clientProxy.getRoomsForUser("tom");
//        /* Should only get Rooms to which Tom belongs */
//        assertEquals(1, rooms.size());
//    }
//
//    @Test
//    void clientDataShouldBeSynced() throws RemoteException {
//        Map<String, User> usersOnClient = tom.users;
//        Map<String, User> usersOnServer = clientProxy.getUsers();
//        assertEquals(usersOnClient, usersOnServer);
//
//        Map<String, Room> roomsOnClient = tom.rooms;
//        Map<String, Room> roomsOnServer = clientProxy.getRoomsForUser("tom");
//        roomsOnClient.values().forEach(room -> System.out.println(room.getName() + " "));
//        roomsOnServer.values().forEach(room -> System.out.println(room.getName() + " "));
//        assertEquals(roomsOnClient, roomsOnServer);
//    }

    //    @Test
//    void xmlBackupShouldBeUpdated() throws Exception {
//        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("backup.xml")));
//        Server decodedServer = (Server) decoder.readObject();
//
//        System.out.print("Decoded value: ");
//        decodedServer.getUsers().keySet().forEach(userName -> System.out.print(userName + " "));
//        System.out.print("\nOriginal value: ");
//        server.getUsers().keySet().forEach(userName -> System.out.print(userName + " "));
//        System.out.println();
//
//        System.out.print("Decoded value: ");
//        decodedServer.getRooms().values().forEach(room -> System.out.print(room.getName() + " "));
//        System.out.print("\nOriginal value: ");
//        server.getRooms().values().forEach(room -> System.out.print(room.getName() + " "));
//        System.out.println();
//
//
//        assertTrue(decodedServer.getUsers().equals(server.getUsers()));
//        assertTrue(decodedServer.getRooms().equals(server.getRooms()));
//        assertTrue(decodedServer.getPasswords().equals(server.getPasswords()));
//    }

//    @Test
//    void checkRoleEncoding() throws Exception {
//        UUID id = UUID.randomUUID();
//        new File("example.xml").createNewFile();
//        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("example.xml")));
//        encoder.writeObject(id);
//        encoder.close();
//
//        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("example.xml")));
//        UUID decodedObj = (UUID) decoder.readObject();
//        assertTrue(true);
//    }

//    @Test
//    void addUserToRoom() throws RemoteException {
//        /* Get the room id of Tom's room */
//        UUID roomId = clientProxy.getRoomsForUser("tom").stream()
//                .filter(room -> room.getName() == "Tom's room")
//                .findAny()
//                .orElse(null)
//                .getId();
//
//        /* Add Alex to the room as Tom */
//        assertTrue(clientProxy.addUserToRoom(roomId, "tom", "alex", Role.normal));
////        /* Alex should now be in Tom's room */
////        //TODO: think about how to return list of rooms, users, and channels */
////        clientProxy.getRoomsForUser("tom").get(0).getUsers().contains();
//    }
}