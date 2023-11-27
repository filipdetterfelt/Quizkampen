import QuestionManager.QuestionManager;
import QuestionManager.QuestionDatabase;
import QuestionManager.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    Socket socket;
    Player p1;
    Player p2;
    Player currentPlayer;

    public Server(Player p1, Player p2) throws IOException {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
    }

    public Server(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run(){

        System.out.println("Server is running");
        QuestionDatabase qdb = new QuestionDatabase();
        QuestionManager qm = new QuestionManager();
        List<String> listOfCategories = qm.getCategories();

        try(
            ObjectOutputStream p1Out = new ObjectOutputStream(p1.sock.getOutputStream());
            ObjectInputStream p1In = new ObjectInputStream(p1.sock.getInputStream());
            ObjectOutputStream p2Out = new ObjectOutputStream(p2.sock.getOutputStream());
            ObjectInputStream p2In = new ObjectInputStream(p2.sock.getInputStream())) {

            TestProtocol testProtocol = new TestProtocol();
            testProtocol.setClientCounter(2);
            testProtocol.process(2);
            p1Out.writeObject(testProtocol.process(listOfCategories));
            Object p1InObj, p2InObj = null, outObj;


            while ((p1InObj = p1In.readObject()) != null || (p2InObj = p2In.readObject()) != null){
                try {
                    p1Out.writeObject(testProtocol.process(p1InObj));
                    p2Out.writeObject(testProtocol.process(p2InObj));
                    }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}