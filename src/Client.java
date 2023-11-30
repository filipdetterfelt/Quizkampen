import QuestionManager.Question;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public Client() {}
    Socket socket;
    String username;
    ObjectOutputStream out;
    ObjectInputStream in;
    ClientHandler player;
    ClientHandler opponent;
    Question tempQ;
    private static int PORT = 55555;


    /*
    Klienten skapas upp med angivet användarnamn, och vi skapar upp våra in & output streams.
    Sedan skapar vi upp ett nytt GameWindow med denna klient samt output stream som inparameter.
     */
    public Client(Socket socket, String username) throws ClassNotFoundException, IOException {
        this.socket = socket;
        this.username = username;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        GameWindow g = new GameWindow(out, this);
        try

        {
            //Skickar användarnamnet till sin ClientHandler & ritar upp startskärmen med användarnamnet
            out.writeObject(username);
            g.drawStartScreen(username);

            //Används för att hantera inkommande objekt och listor av okända object
            Object tempObject;
            List<Object> tempList = new ArrayList<>(2);

            //Initierar våra listor och variabler som vi behöver komma åt
            List<Integer> scoreList = new ArrayList<>();
            String cat1 = null, cat2 = null;
            scoreList.add(0,0);
            scoreList.add(1,0);
            tempList.add(0,0);
            tempList.add(0,0);



            //Här ligger vi och lyssnar och väntar på att få in ett objekt
            while ((tempObject = in.readObject()) != null) {

                /*
                Om objektet vi fått in är en lista, då följer vi nedan if-sats.
                Vi har här inne ytterligare if-satser som anropar på metoder som returnerar
                en bool. Dessa metoder kollar vad för object listan innehåller.
                 */
                if (tempObject instanceof List<?>){
                    Thread.sleep(1000);
                    tempList.set(0,((List<?>) tempObject).get(0));
                    tempList.set(1,((List<?>) tempObject).get(1));


                    if (isListOfInteger(tempList)) {
                        scoreList.set(0,(Integer) ((List<?>) tempObject).get(0));
                        scoreList.set(1,(Integer) ((List<?>) tempObject).get(1));
                        g.drawWaitingForOpponentScreen(scoreList);
                        out.writeObject("testString");

                    } else if (isListOfString(tempList)){
                        cat1 = (String) tempList.get(0);
                        cat2 = (String) tempList.get(1);
                        g.drawCategoryScreen(cat1,cat2);

                    } else if (isListOfPlayers(tempList)){
                        player = (ClientHandler) tempList.get(0);
                        opponent = (ClientHandler) tempList.get(1);
                    }

                /*
                Om objektet vi fått in är en fråga, så plockar vi ut den, kastar om den till ett
                Question object och ritar upp frågeskärmen som tar in den frågan som inparameter.
                 */
                } else if (tempObject instanceof Question) {
                    Thread.sleep(1000);
                    tempQ = (Question) tempObject;
                    g.drawQuestionsScreen(tempQ);
                }
                /*
                Om objektet vi fått in är en bool, så vet vi att det är spelet som avslutats,
                så då ritar vi upp våran slutskärm, med de två Clienthandlers (spelare) som spelat
                som inparameter.
                 */
                else if (tempObject instanceof Boolean){
                    g.drawEndScreen(player,opponent);
                }
                /*
                Om objektet vi fått in är en Integer, så vet vi att vi ska rita upp resultatskärmen, med
                de två spelarnas poäng på för varje rond.
                 */
                else if (tempObject instanceof Integer) {
                    Thread.sleep(1000);
                    int tempInt = (Integer) tempObject;
                    if(tempInt == 4){
                        g.drawResultScreen(scoreList);
                    }
                }
            }
        } catch (EOFException e){
            System.out.println("EOFException hos Client");
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Här nedan följer de tre metoder som kollar av vad för typ av objekt våra inkommande listor
    består utav. De kollar alltså på varje element i listan, och kollar om dom är respektive typer
    av objekt.
     */
    public boolean isListOfString(List<Object> list){
        return list.stream().anyMatch(type -> type instanceof String);
    }
    public boolean isListOfInteger(List<Object> list){
        return list.stream().anyMatch(type -> type instanceof Integer);
    }
    public boolean isListOfPlayers(List<Object> list) {
        return list.stream().anyMatch(type -> type instanceof ClientHandler);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String userName = null;
        while (userName == null || userName.trim().isEmpty()){
            userName = JOptionPane.showInputDialog(null, "Användarnamn?");
        }
        Socket socket = new Socket("localhost", PORT);
        Client client = new Client(socket,userName);
    }
}
