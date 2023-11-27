package Old2;

import Old2.QuestionManager.Question;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    private static int PORT = 55557;

    public Client(String serverAdress) throws ClassNotFoundException, IOException {
        GameWindow g = new GameWindow();
        socket = new Socket(serverAdress,PORT);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        try

        {
            g.drawStartScreen();

            Object tempObject;
            String tempString;
            List<String> tempList = new ArrayList<>();
            String cat1 = null, cat2 = null;

            while ((tempObject = in.readObject()) != null) {
                System.out.println("Klient mottagit object");

                //Om objektet vi tagit emot från servern är en List<>, följ nedan kodblock
                if (tempObject instanceof List<?>){
                    tempList.add((String) ((List<?>) tempObject).get(0));
                    tempList.add((String) ((List<?>) tempObject).get(1));
                    cat1 = tempList.get(0);
                    cat2 = tempList.get(1);

                    //Ritar upp kategorifönstret med de mottagna kategorierna som inparametrar
                    g.drawCategoryScreen(cat1,cat2);

                    //Action listener för kategorierna, skickar vald kategori som sträng till servern
                    g.category1Btn.addActionListener(e -> {String temp = g.category1Btn.getText();
                        try {out.writeObject(temp);} catch (IOException ex) {throw new RuntimeException(ex);}
                    });
                    g.category2Btn.addActionListener(e -> {String temp = g.category2Btn.getText();
                        try {out.writeObject(temp);} catch (IOException ex) {throw new RuntimeException(ex);}
                    });

                //Om objektet vi tagit emot från servern är en Question, följ nedan kodblock
                } else if (tempObject instanceof Question) {
                    Question tempQ = (Question) tempObject;

                    //Ritar upp frågeskärmen med frågan som inparameter
                    g.drawQuestionsScreen(tempQ);

                    /* Action listener för svarsknapparna, skickar tillbaka en int beroende på om
                       svaret är rätt eller fel. Rätt = 1, Fel = 0 */
                    g.answer1Btn.addActionListener(e -> {
                        if (g.checkAnswer(0,tempQ.getCorrectOptionIndex(),g.answer1Btn)){
                            try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}
                        } else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}
                    });
                    g.answer2Btn.addActionListener(e -> {
                        if (g.checkAnswer(1,tempQ.getCorrectOptionIndex(),g.answer2Btn)){
                            try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}
                        } else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}
                    });
                    g.answer3Btn.addActionListener(e -> {
                        if (g.checkAnswer(2,tempQ.getCorrectOptionIndex(),g.answer3Btn)){
                            try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}
               //skriv ut
                            } else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}

                    });
                    g.answer4Btn.addActionListener(e -> {
                        if (g.checkAnswer(3,tempQ.getCorrectOptionIndex(),g.answer4Btn)){
                            try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}
                        } else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}
                    });
                } else if (tempObject instanceof Integer) {
                    int tempInt = (Integer) tempObject;
                            System.out.println("Integer mottagen");
                    g.drawWaitingForOpponentScreen(tempInt);
                            System.out.println("Test efter draw");
                } else if (tempObject instanceof String){
                    System.out.println("Temp mottagen");
                    String temp = (String) tempObject;
                    if (temp.matches("TEMP")){
                        System.out.println("temp matchade");
                        g.drawWaitingForOpponentScreen(0);
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client("127.0.0.1");
    }
}
