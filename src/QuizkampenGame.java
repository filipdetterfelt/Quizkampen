import QuestionManager.QuestionManager;
import QuestionManager.Question;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QuizkampenGame extends Thread{

    QuizkampenPlayer playerOne;
    QuizkampenPlayer playerTwo;
    QuizkampenPlayer currentPlayer;
    QuestionManager qm = new QuestionManager();
    List<Question> questionList = new ArrayList<Question>();
    List<String> tempList = new ArrayList<>();
    Properties p = loadProperties();

    public Properties loadProperties() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("src/MyProperties.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
    int inputQuestions = Integer.parseInt(p.getProperty("questions"));
    int inputCategories = Integer.parseInt(p.getProperty("categories"));


    public QuizkampenGame (QuizkampenPlayer playerOne, QuizkampenPlayer playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;
        this.playerOne.setOpponent(playerTwo);
        this.playerTwo.setOpponent(playerOne);
    }

    int questions = 1;
    int categoriesInt = 0;

    public void run() {
        try {
            playerOne.send("QuizkamenGame - run() -> Welcome " + playerOne);
            playerOne.send("QuizkamenGame - run() -> Waiting for opponent to connect");
            playerTwo.send("QuizkamenGame - run() -> Welcome " + playerTwo);
            playerTwo.send("QuizkamenGame - run() -> "  + playerOne.name + " and " + playerTwo.name + " are connected");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentPlayer = playerOne;
        Object inObj;
        String tempString;
        int tempInt;
        while (true) {
            inObj = currentPlayer.receive();  //ta emot från klient

            while (categoriesInt < inputCategories) { // så länge man inte har kommit upp i rätt antal kategorier

                try { // skickar en lista med kategorier till klienten som genom actionlistener väljer kategori
                    currentPlayer.send(qm.getCategories());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (inObj instanceof List<?>) { // om man får tillbaka ett objekt med en lista med frågorna i den kategorin klienten valt
                    tempList.add((String) ((List<?>) inObj).get(0));
                    tempList.add((String) ((List<?>) inObj).get(1));
                    try {
                        currentPlayer.send(tempList); // skickar ut första frågan
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    categoriesInt++;
                }
                else if (inObj instanceof Question ) { // om man får in ett frågeobjekt och man inte har kommit upp i antal frågor
                    while (inputQuestions > questions) {
                        if (inObj instanceof String) { // om objektet man får in är en string
                            tempString = (String) inObj;
                            questionList = qm.getQuestions(tempString);

                        } else if (inObj instanceof Integer) { // om objektet vi får in är en int så plussar vi på poäng
                            tempInt = (Integer) inObj;
                            if (tempInt == 1) {
                                System.out.println("Poäng: 1");
                                currentPlayer.pointsPerGame++;
                                currentPlayer.totalPoints = currentPlayer.totalPoints+ currentPlayer.pointsPerGame;
                            } else if (tempInt == 0) {
                                System.out.println("Poäng: 0");
                            }
                            questions++;
                        }
                        System.out.println("Questions " + questions);
                        try {
                            currentPlayer.send(questionList.get(questions - 1));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    currentPlayer = currentPlayer.getOpponent(); // när man har svarat på två frågor byter vi current player
                    // så börjar vi om så länge vi inte kommit upp i rätt antal kategorier
                }

                }
            }
        }

    }


