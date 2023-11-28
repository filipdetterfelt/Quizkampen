import QuestionManager.Question;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    Socket socket;
    String username;
    ObjectOutputStream out;
    ObjectInputStream in;
    Question tempQ;
    private static int PORT = 55555;
    public Client() {}

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

                    //Ritar upp kategorifönstret med de mottagna kategorierna som inparametrar
                    g.drawCategoryScreen(cat1,cat2);

                    //Action listener för kategorierna, skickar vald kategori som sträng till servern
                    /*
                    g.category1Btn.addActionListener(e -> {String temp = g.category1Btn.getText();
                        try {out.writeObject(temp);} catch (IOException ex) {throw new RuntimeException(ex);}
                    });
                    g.category2Btn.addActionListener(e -> {String temp = g.category2Btn.getText();
                        try {out.writeObject(temp);} catch (IOException ex) {throw new RuntimeException(ex);}
                    });

                     */

                //Om objektet vi tagit emot från servern är en Question, följ nedan kodblock
                } else if (tempObject instanceof Question) {
                    tempQ = (Question) tempObject;
                    System.out.println("Client fick fråga: " + tempQ.getQuestion());
                    g.drawQuestionsScreen(tempQ);
                } else if (tempObject instanceof Integer) {
                    int tempInt = (Integer) tempObject;
                            System.out.println("Integer mottagen");
                    g.drawWaitingForOpponentScreen(tempInt);
                    out.writeObject("testString");
                            System.out.println("Test efter draw");
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", PORT);
        Client client = new Client(socket,username);
    }
}