import QuestionManager.QuestionManager;
import QuestionManager.QuestionDatabase;

import java.net.Socket;
import java.util.List;

public class Server extends Thread{
    Socket socket;
    ClientHandler p1;
    ClientHandler p2;
    ClientHandler currentPlayer;
    QuestionManager qm = new QuestionManager();
    List<String> listOfCategories = qm.getCategories();

    public Server(ClientHandler p1, ClientHandler p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
        System.out.println("Game has started!");
        System.out.println(p1.getClientUsername() + " VS " + p2.getClientUsername());
    }

    public void run(){

        System.out.println("Server is running");

        Object fromPLayer;
        int objectCounter = 0;


            try {
                TestProtocol tp = new TestProtocol(this, currentPlayer, p1, p2);
                System.out.println("Sending 2 categories to " + currentPlayer.getClientUsername());
                currentPlayer.getOutputStream().writeObject(tp.process(listOfCategories));
                while(true) {
                if (currentPlayer == p1) {
                    System.out.println("Inside p1 loop");
                    objectCounter = 0;

                    while ((fromPLayer = p1.getInputStream().readObject()) != null) {
                        if (fromPLayer instanceof Boolean) {
                            System.out.println("försöker skriva bool");
                            p2.getOutputStream().writeObject(fromPLayer);
                            System.out.println("p2 bool ");

                            p1.getOutputStream().writeObject(fromPLayer);
                            System.out.println("p1 bool ");

                            break;
                        }
                        objectCounter++;
                        System.out.println("Object counter: " + objectCounter);
                        if (currentPlayer != p1) {
                            System.out.println("breaking out of loop");
                            p2.getOutputStream().writeObject(tp.process(fromPLayer));
                            System.out.println("p2 sent: " + fromPLayer);
                            break;
                        }
                        System.out.println("Sending new object to process: " + fromPLayer);
                        p1.getOutputStream().writeObject(tp.process(fromPLayer));
                    }
                } else if (currentPlayer == p2) {
                    System.out.println("Inside p2 loop");
                    objectCounter = 0;

                    while ((fromPLayer = p2.getInputStream().readObject()) != null) {
                        if (fromPLayer instanceof Boolean) {
                            System.out.println("försöker skriva bool");
                            p2.getOutputStream().writeObject(fromPLayer);
                            System.out.println("p2 bool ");
                            p1.getOutputStream().writeObject(fromPLayer);
                            System.out.println("p1 bool");
                            break;
                        }
                        objectCounter++;
                        System.out.println("Object counter: " + objectCounter);
                        if (currentPlayer != p2) {
                            System.out.println("breaking out of loop");
                            p1.getOutputStream().writeObject(tp.process(fromPLayer));
                            System.out.println("p1 sent: " + fromPLayer);
                            break;
                        }
                        System.out.println("Sending new object to process: " + fromPLayer);

                        p2.getOutputStream().writeObject(tp.process(fromPLayer));

                    }

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public ClientHandler getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(ClientHandler currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<String> getListOfCategories() {
        return listOfCategories;
    }
}