import java.io.IOException;
import java.net.ServerSocket;

public class QuizkampenServer {

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(44444);
        System.out.println("Quizkampen Server körs");
        try{
            while (true){
                QuizkampenPlayer p1 = new QuizkampenPlayer(ss.accept(),"Player1");
                System.out.println("Client 1 found");
                QuizkampenPlayer p2 = new QuizkampenPlayer(ss.accept(),"Player2");
                System.out.println("Client 2 found");
                QuizkampenGame game = new QuizkampenGame(p1,p2);
                game.start();
            }
        }
        finally {
            ss.close();
        }



    }
}
