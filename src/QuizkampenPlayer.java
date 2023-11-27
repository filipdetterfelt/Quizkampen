import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QuizkampenPlayer {
    String name;
    int pointsPerGame = 0;
    int totalPoints = 0;
    List<Integer> listOfPoints = new ArrayList<>();
    QuizkampenPlayer opponent;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;

    /**
     * Constructs a handler thread for a given socket and mark
     * initializes the stream fields, displays the first two
     * welcoming messages.
     */
    public QuizkampenPlayer(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        System.out.println(this.name);
        try {
            System.out.println("Trying to create input + output streams for " + this.name );
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sucess created input + output streams for " + this.name );
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }
/*    public ServerSidePlayer(Socket socket, char mark) {
        this.socket = socket;
        this.mark = mark;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }*/

    //Sends data to client
    public void send(Object mess) throws IOException{
        output.writeObject(mess);
    }

    //Receives data from client
    public Object receive()  {
        try {
            return input.readObject();
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
