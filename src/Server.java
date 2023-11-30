import QuestionManager.QuestionManager;
import QuestionManager.QuestionDatabase;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Klassen ärver Thread
//Skapar upp nödvändiga variabler osv
public class Server extends Thread{
    Socket socket;
    ClientHandler p1;
    ClientHandler p2;
    ClientHandler currentPlayer;
    QuestionManager qm = new QuestionManager();
    List<String> listOfCategories = this.getListOfCategories();
    List<ClientHandler> players = new ArrayList<>();

    //Konstruktor med 2 ch som inparameter
    //Addar 2 player i players lista
    public Server(ClientHandler p1, ClientHandler p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
        players.add(0,p1);
        players.add(1,p2);
        System.out.println("Game has started!");
        System.out.println(p1.getClientUsername() + " VS " + p2.getClientUsername());
    }

    //Våran run metod för o köra
    public void run(){

        System.out.println("Server is running");

        Object fromPLayer;
        int objectCounter = 0;

                //Try sats, skickar kategorier till spelare
            try {
                TestProtocol tp = new TestProtocol(this, currentPlayer, p1, p2);
                System.out.println("Sending 2 categories to " + currentPlayer.getClientUsername());
                currentPlayer.getOutputStream().writeObject(tp.process(listOfCategories));

                //Nästlade while loopar
                while(true) {
                if (currentPlayer == p1) {
                    objectCounter = 0;

                    //Kontrollera så de inte är null
                    while ((fromPLayer = p1.getInputStream().readObject()) != null) {

                        //Om instans av bool
                        if (fromPLayer instanceof Boolean) {
                            System.out.println("försöker skriva bool");
                            p2.getOutputStream().writeObject(fromPLayer);
                            System.out.println("p2 bool ");

                            p1.getOutputStream().writeObject(fromPLayer);
                            System.out.println("p1 bool ");

                            break;
                        }


                        //Bryter sig ur loopen
                        objectCounter++;
                        if (currentPlayer != p1) {
                            p2.getOutputStream().writeObject(tp.process(fromPLayer));
                            break;
                        }
                        //System.out.println("Sending new object to process: " + fromPLayer);
                        p1.getOutputStream().writeObject(tp.process(fromPLayer));
                    }

                } else if (currentPlayer == p2) {
                    //System.out.println("Inside p2 loop");
                    objectCounter = 0;

                    //Kontrollera så de inte är null
                    while ((fromPLayer = p2.getInputStream().readObject()) != null) {


                        //Samma som ovan instace av bool ?
                        if (fromPLayer instanceof Boolean) {
                            System.out.println("försöker skriva bool");
                            p2.getOutputStream().writeObject(fromPLayer);
                            System.out.println("p2 bool ");
                            p1.getOutputStream().writeObject(fromPLayer);
                            System.out.println("p1 bool");
                            break;
                        }
                        objectCounter++;
                        //System.out.println("Object counter: " + objectCounter);
                        if (currentPlayer != p2) {
                            //System.out.println("breaking out of loop");
                            p1.getOutputStream().writeObject(tp.process(fromPLayer));
                            //System.out.println("p1 sent: " + fromPLayer);
                            break;
                        }
                        //System.out.println("Sending new object to process: " + fromPLayer);
                        p2.getOutputStream().writeObject(tp.process(fromPLayer));

                    }

                    }

                }

                //fångar exception
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

    //Lista av string för kategorier
    public List<String> getListOfCategories()
    {
        //Gets another set of categories and returns them
        this.listOfCategories = qm.getCategories();

        System.out.println("Server - getListOfCategories() -> called the qm.getCategories() and received "
                + this.listOfCategories.size() + " categories");
        return listOfCategories;
    }

    //metod för endGame
    public void endGame() throws IOException {
        Boolean b = true;
        p1.getOutputStream().writeObject(players);
        p2.getOutputStream().writeObject(players);
        this.p1.getOutputStream().writeObject(b);
        this.p2.getOutputStream().writeObject(b);
    }
}