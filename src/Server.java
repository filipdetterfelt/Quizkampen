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

    public Server(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
    }

    public Server(Socket socket) {
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

            p1Out.writeObject(listOfCategories);

            Object tempObject;
            String tempString;
            List<Question> tempQuestions = new ArrayList<>();

            while ((tempObject = p1In.readObject()) != null){
                if (tempObject instanceof String){
                    tempString = (String) tempObject;
                    System.out.println(tempString);
                    tempQuestions = qm.getQuestions(tempString,1);
                    p1Out.writeObject(tempQuestions.get(0));
                }
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }



}