import QuestionManager.Question;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    public Client() {
    }

    Socket socket;
    String username;
    ObjectOutputStream out;
    ObjectInputStream in;
    Question tempQ;
    private static int PORT = 55555;

    public Client(Socket socket, String username) throws ClassNotFoundException, IOException {
        this.socket = socket;
        this.username = username;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        GameWindow g = new GameWindow(out, this);
        try

        {
            out.writeObject(username);
            g.drawStartScreen(username);

            Object tempObject;
            String tempString;
            List<String> tempList = new ArrayList<>();
            String cat1 = null, cat2 = null;

            while ((tempObject = in.readObject()) != null) {
                System.out.println("Klient mottagit object: " + tempObject);

                //Om objektet vi tagit emot från servern är en List<>, följ nedan kodblock
                if (tempObject instanceof List<?>){
                    tempList.add((String) ((List<?>) tempObject).get(0));
                    tempList.add((String) ((List<?>) tempObject).get(1));
                    cat1 = tempList.get(0);
                    cat2 = tempList.get(1);
                    System.out.println("Tog emot lista med kategorier");
                    //Ritar upp kategorifönstret med de mottagna kategorierna som inparametrar
                    g.drawCategoryScreen(cat1,cat2);
  
                //Om objektet vi tagit emot från servern är en Question, följ nedan kodblock
                } else if (tempObject instanceof Question) {
                    tempQ = (Question) tempObject;
                    System.out.println("Client fick fråga: " + tempQ.getQuestion());
                    g.drawQuestionsScreen(tempQ);
                } else if (tempObject instanceof Integer) {
                    int tempInt = (Integer) tempObject;
                    if(tempInt == 3){
                        g.drawWaitingForOpponentScreen(tempInt);
                        out.writeObject("testString");
                        System.out.println("Test efter draw");
                    } if(tempInt == 4){
                        g.drawResultScreen(5,5);
                    }
                }
            }
        } catch (EOFException e){
            System.out.println("Slutet av filen");
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String userName = null;
        while (userName == null || userName.trim().isEmpty()){
            userName = JOptionPane.showInputDialog(null, "Vad heter du?");
        }

        Socket socket = new Socket("localhost", PORT);
        Client client = new Client(socket,userName);
    }
}
