import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QuizkampenPlayer extends Thread  {
    String name;
    int pointsPerGame = 0;
    int totalPoints = 0;

    List<Integer> listOfPoints = new ArrayList<>();

    QuizkampenPlayer opponent;
    Socket socket;

    ObjectInputStream in;
    ObjectOutputStream out;

    /**
     * Constructs a handler thread for a given socket and mark
     * initializes the stream fields, displays the first two
     * welcoming messages.
     */
    public QuizkampenPlayer(Socket socket, String name) throws Exception {
        this.socket = socket;
        this.name = name;
        System.out.println("Trying to create input + output streams for " + this.name );
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.in = new ObjectInputStream(this.socket.getInputStream());
        System.out.println("Successfully created input + output streams for " + this.name );
    }

    //Sends data to client
    public void send(Object mess) throws IOException{
        this.out.writeObject(mess);
    }

    //Receives data from client
    public Object receive()  {
        try {
            return this.in.readObject();
        } catch (Exception e ) {
            System.out.println("Player "+name+" could not receive data " + e);
            throw new RuntimeException(e);
        }
    }

    //Accepts notification of who the opponent is.
    public void setOpponent(QuizkampenPlayer opponent) {
        this.opponent = opponent;
    }

    //Returns the opponent.
    public QuizkampenPlayer getOpponent() {
        return opponent;
    }

}
