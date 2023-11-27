package Old2;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerListener {
    private int clientCounter;


    public int getClientCounter() {
        return clientCounter;
    }

    public ServerListener() throws IOException {
        try(ServerSocket ss = new ServerSocket(55557)){


            while (true){
                Player p1 = new Player(ss.accept(), "Player1");
                clientCounter++;
                System.out.println("Player 1 created and connected");
                Player p2 = new Player(ss.accept(), "Player2");
                clientCounter++;
                System.out.println("Player 2 created and connected");
                Server server = new Server(p1, p2);
                server.start();
                System.out.println(clientCounter);
            }

        } catch (EOFException e){
            System.out.println("Slutet av filen");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        ServerListener serverListener = new ServerListener();
    }


}
