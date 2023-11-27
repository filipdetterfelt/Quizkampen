import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class QuizkampenServer {

    public static void main(String[] args) throws Exception {

        try (ServerSocket ss = new ServerSocket(44444)) {
            System.out.println("Quizkampen server is running");
            while (!ss.isClosed()) {

                QuizkampenPlayer playerOne = new QuizkampenPlayer(ss.accept(), "Player 1");
                System.out.println("Client 1 found");
                QuizkampenPlayer playerTwo = new QuizkampenPlayer(ss.accept(), "Player 2");
                System.out.println("Client 2 found");

                QuizkampenGame game = new QuizkampenGame(playerOne, playerTwo);
                game.run();
            }
        }
    }
}