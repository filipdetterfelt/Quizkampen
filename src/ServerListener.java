import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    public ServerListener() throws IOException {
        try(ServerSocket ss = new ServerSocket(55555)){


            while (true){
                Player p1 = new Player(ss.accept(), "Player1");
                System.out.println("Player 1 created and connected");
                Player p2 = new Player(ss.accept(), "Player2");
                System.out.println("Player 2 created and connected");
                Server server = new Server(p1, p2);
                server.start();
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) throws IOException {
        ServerListener serverListener = new ServerListener();
    }

}
