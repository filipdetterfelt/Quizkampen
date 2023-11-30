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
    int score;
    boolean isFinnished = false;
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
            List<Object> tempList = new ArrayList<>();
            List<Integer> scoreList = new ArrayList<>();
            String cat1 = null, cat2 = null;
            int answeredQuestions = 0;


            while ((tempObject = in.readObject()) != null) {
                System.out.println("Klient mottagit object: " + tempObject);

                //Om objektet vi tagit emot från servern är en List<>, följ nedan kodblock
                if (tempObject instanceof List<?>){
                    tempList.add(((List<?>) tempObject).get(0));
                    tempList.add(((List<?>) tempObject).get(1));

                    if (isListOfInteger(tempList)) {
                        System.out.println("Fick en lista med int");
                        scoreList.add((Integer) ((List<?>) tempObject).get(0));
                        scoreList.add((Integer) ((List<?>) tempObject).get(1));
                        g.drawWaitingForOpponentScreen(scoreList);
                    } else if (isListOfString(tempList)){
                        System.out.println("Fick en lista med string");
                        cat1 = (String) tempList.get(0);
                        cat2 = (String) tempList.get(1);
                        g.drawCategoryScreen(cat1,cat2);
                    } else System.out.println("Tom lista, nåt är fel");


                    //System.out.println("Tog emot lista med kategorier");
                    //Ritar upp kategorifönstret med de mottagna kategorierna som inparametrar

  
                //Om objektet vi tagit emot från servern är en Question, följ nedan kodblock
                } else if (tempObject instanceof Question) {
                    tempQ = (Question) tempObject;
                    System.out.println("Client fick fråga: " + tempQ.getQuestion());
                    g.drawQuestionsScreen(tempQ);

                   //Lägger till answeredQuestions med 1 för varje besvarad fråga
                    answeredQuestions++;
                    System.out.println("answeredQuestion = "+answeredQuestions);

                    //Om antal besvarade frågor är rätt antal så ritas endScreen ut

                }
                else if (tempObject instanceof Boolean){
                    System.out.println("Drawing endScreen");

                    g.drawEndScreen();
                }
                else if (tempObject instanceof Integer) {
                    int tempInt = (Integer) tempObject;
                    if(tempInt == 4){
                        g.drawResultScreen(scoreList);
                    } else {
                        //g.drawWaitingForOpponentScreen(tempInt);
                        out.writeObject("testString");

                        System.out.println("Test efter draw");

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

    public boolean isListOfString(List<Object> list){
        return list.stream().anyMatch(type -> type instanceof String);
    }
    public boolean isListOfInteger(List<Object> list){
        return list.stream().anyMatch(type -> type instanceof Integer);
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
