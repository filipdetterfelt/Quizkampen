
import QuestionManager.Question;

import QuestionManager.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QuizkampenClient {

        private static int PORT = 44444;
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        GameWindow gw = new GameWindow();

        private boolean blocked = false;

        public QuizkampenClient(String serverAddress) throws Exception {


            socket = new Socket("localhost", PORT); // Replace "localhost" with the server address

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

        }

        /**
         * The main thread of the client will listen for messages
         * from the server. The first message will be a "WELCOME"
         * message in which we receive our mark. Then we go into a
         * loop listening for "VALID_MOVE", "OPPONENT_MOVED", "VICTORY",
         * "DEFEAT", "TIE", "OPPONENT_QUIT or "MESSAGE" messages,
         * and handling each message appropriately. The "VICTORY",
         * "DEFEAT" and "TIE" ask the user whether or not to play
         * another game. If the answer is no, the loop is exited and
         * the server is sent a "QUIT" message. If an OPPONENT_QUIT
         * message is recevied then the loop will exit and the server
         * will be sent a "QUIT" message also.
         */


        public void play() throws Exception {
            gw.drawStartScreen();
            Object response;
            char mark = 'S';
            char opponentMark = 'P';
            String cat1, cat2;
            List<String> tempList = new ArrayList<>();
            try {
                response = in.readObject();
                System.out.println("Klienen har mottagit: " + response);
                if (response instanceof String) {
                    String temp = (String) response;
                }
                    while ((response = in.readObject()) != null) {
                        System.out.println("Klient mottagit object");

                        //Om objektet vi tagit emot från servern är en List<>, följ nedan kodblock
                        if (response instanceof List<?>) {
                            tempList.add((String) ((List<?>) response).get(0));
                            tempList.add((String) ((List<?>) response).get(1));
                            cat1 = tempList.get(0);
                            cat2 = tempList.get(1);

                            //Ritar upp kategorifönstret med de mottagna kategorierna som inparametrar
                            gw.drawCategoryScreen(cat1, cat2);

                            //Action listener för kategorierna, skickar vald kategori som sträng till servern
                            gw.category1Btn.addActionListener(e -> {String temp = gw.category1Btn.getText();try {out.writeObject(temp);
                                } catch (IOException ex) {throw new RuntimeException(ex);}});
                            gw.category2Btn.addActionListener(e -> {String temp = gw.category2Btn.getText();try {out.writeObject(temp);
                                } catch (IOException ex) {throw new RuntimeException(ex);}});

                            //Om objektet vi tagit emot från servern är en Question, följ nedan kodblock
                        } else if (response instanceof Question) {
                            Question tempQ = (Question) response;

                            //Ritar upp frågeskärmen med frågan som inparameter
                            gw.drawQuestionsScreen(tempQ);

                            /* Action listener för svarsknapparna, skickar tillbaka en int beroende på om
                               svaret är rätt eller fel. Rätt = 1, Fel = 0 */
                            gw.answer1Btn.addActionListener(e -> {if (gw.checkAnswer(0, tempQ.getCorrectOptionIndex(), gw.answer1Btn)) {
                                    try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}}
                                    else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}});
                            gw.answer2Btn.addActionListener(e -> {if (gw.checkAnswer(1, tempQ.getCorrectOptionIndex(), gw.answer2Btn)) {
                                    try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}}
                                    else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}});
                            gw.answer3Btn.addActionListener(e -> {if (gw.checkAnswer(2, tempQ.getCorrectOptionIndex(), gw.answer3Btn)) {
                                    try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}}
                                    else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}});
                            gw.answer4Btn.addActionListener(e -> {if (gw.checkAnswer(3, tempQ.getCorrectOptionIndex(), gw.answer4Btn)) {
                                    try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}}
                                    else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}});
                        }
                    }

            } finally {socket.close();}
        }

            public static void main (String[]args) throws Exception {

                    String serverAddress = (args.length == 0) ? "localhost" : args[1];
                    QuizkampenClient client = new QuizkampenClient(serverAddress);
                    client.play();

            }
        }

