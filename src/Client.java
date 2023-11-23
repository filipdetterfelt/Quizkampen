import QuestionManager.QuestionDatabase;
import QuestionManager.Question;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private static int PORT = 55555;




    public Client(String serverAdress) throws ClassNotFoundException {
        GameWindow g = new GameWindow();
        try(
        Socket socket = new Socket(serverAdress,PORT);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());)
        {
            g.drawStartScreen();

            Object tempObject;
            String tempString;
            List<String> tempList = new ArrayList<>();
            String cat1 = null, cat2 = null;

            while ((tempObject = in.readObject()) != null) {
                System.out.println("I while");
                if (tempObject instanceof List<?>){
                    tempList.add((String) ((List<?>) tempObject).get(0));
                    tempList.add((String) ((List<?>) tempObject).get(1));
                    cat1 = tempList.get(0);
                    cat2 = tempList.get(1);
                    System.out.println(cat1 + cat2);

                    g.drawCategoryScreen(cat1,cat2);

                    g.category1Btn.addActionListener(e -> {
                        String temp = g.category1Btn.getText();
                        try {
                            out.writeObject(temp);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    g.category2Btn.addActionListener(e -> {
                        String temp = g.category2Btn.getText();
                        try {
                            out.writeObject(temp);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                } else if (tempObject instanceof Question) {
                    boolean answer = (g.drawQuestionsScreen((Question) tempObject));
                    System.out.println(answer);

                }

            }



        } catch (EOFException e){

            System.out.println("Slutet av filen");
        } catch (IOException e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client("127.0.0.1");
    }
}
