import QuestionManager.Question;
import QuestionManager.QuestionManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

public class TestProtocol {

    //State för att invänta att båda är connected
    private static final int ROUND_ONE_START = 0;
    //State för att dela ut kategori till Player 1, och be Player 2 att vänta
    private static final int PLAYER_ONE_CHOOSE_CATEGORY = 1;
    //State för att Player 1 ska få och svara på x frågor
    private static final int PLAYER_ONE_QUESTION = 2;
    //State för att dela ut kategori till Player 2, och be Player 1 att vänta
    private static final int PLAYER_TWO_CHOOSE_CATEGORY = 3;
    //State för att Player 2 ska få och svara på x frågor
    private static final int PLAYER_TWO_QUESTION = 4;
    //State för avslutat spel
    private static final int GAME_END = 5;
    private int state = ROUND_ONE_START;

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
    Server server;
    ClientHandler currentPlayer;
    ClientHandler p1;
    ClientHandler p2;

    public TestProtocol(Server server, ClientHandler currentPlayer, ClientHandler p1, ClientHandler p2) throws IOException {
        this.currentPlayer = currentPlayer;
        this.server = server;
        this.p1 = p1;
        this.p2 = p2;
        System.out.println("Protocol initialized");
    }

    List<Question> questionsForThisGame = new ArrayList<>();
    int questions = 0;
    int categories = 0;
    boolean gameEnd = false;
    public Object process(Object inObj) {
        System.out.println("ENTER STATE = " + state);
        System.out.println("Process har mottagit object: " + inObj);

        Object processedObject = null;
        System.out.println(server.getCurrentPlayer().getClientUsername());

        List<String> tempCategoryList = new ArrayList<>();
        QuestionManager qm = new QuestionManager();

        switch (state){
            case ROUND_ONE_START -> {
                /*
                    När båda spelarna har connectat så kommer spelet starta, och klient 1 kommer alltid att börja
                    med att skicka över listan med blandade kategorier som finns i databasen.
                    Då vill vi returnera en lista med 2 utvalda kategorier till klienten,
                    som då ritar upp kategoriskärmen.
                 */
                if (inObj instanceof List<?>){
                    tempCategoryList.add((String) ((List<?>) inObj).get(0));
                    tempCategoryList.add((String) ((List<?>) inObj).get(1));
                    processedObject = tempCategoryList;
                    state = PLAYER_ONE_CHOOSE_CATEGORY;
                }
            }
            case PLAYER_ONE_CHOOSE_CATEGORY -> {
                /*
                    När klienten returnerar sin valda kategori, så vill vi ta två frågor ur den kategorins lista
                    och lägga dom i en temporär lista med Questions. Sedan returnerar vi den första frågan ur den listan
                    till klienten, och adderar frågorna som skickats med 1. Sedan byter vi state till PLAYER_ONE_QUESTION
                    för att hantera de integer som kommer som svar.
                 */
                if (inObj instanceof String){
                    questionsForThisGame = qm.getQuestions((String) inObj);
                    processedObject = questionsForThisGame.get(questions);
                    System.out.println("P1 Question number" + questions);
                    System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                    questions++;
                    categories++;
                    System.out.println("Sending question from category state");
                }
                state = PLAYER_ONE_QUESTION;
            }
            case PLAYER_ONE_QUESTION -> {
                 /*
                    Om det vi får in är en integer, så är det antingen rätt eller fel (1, 0) från klienten när den har
                    svarat på en fråga. Om det vi får in är svaret på den andra frågan, så vill vi ändra state samt ändra
                    currentPlayer hos servern. Sedan returnerar vi första frågan som klient 1 svarat på. Denna går
                    då till klient 2.
                 */
                if (inObj instanceof Integer){
                    if (questions == inputQuestions){
                /*
                    Är questions == inputQuestions, så har klient 1 nu svarat på sina frågor, och vi vill då sätta
                    currentPlayer hos servern till klient 2. Sedan returnerar vi en 3'a, så klienten ritar upp sin
                    waitScreen med sina poäng. Vi sätter också state till PLAYER_TWO_QUESTION.
                 */
                        System.out.println("questions == inputQuestions");
                        state = PLAYER_TWO_QUESTION;
                        server.setCurrentPlayer(p2);
                        System.out.println("Current player set as: " + server.currentPlayer.getClientUsername());
                        questions = 0;
                        System.out.println("Questions set at: " + questions);
                        processedObject = 3;
                        System.out.println("processedQuestion is: " + questionsForThisGame.get(questions).getQuestion());
                        if (categories == inputCategories){
                            System.out.println("categories == inputCategories");
                            processedObject = 3;
                            state = GAME_END;
                            System.out.println("Current player set as: " + server.currentPlayer.getClientUsername());
                            questions = 0;
                        }

                    } else if (questions < inputQuestions){
                /*
                    Är question < inputQuestions så ska klient 1 svara på fler frågor.
                    Vi kontrollerar om vi fick rätt eller fel svar (1, 0), och sedan returnerar vi nästa fråga
                    till klienten och sätter questions ++;
                 */
                        if ((Integer) inObj == 1){
                            System.out.println("RÄTT");
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("Vi är i questions < inputQuestions");
                        processedObject = questionsForThisGame.get(questions);
                        System.out.println("processedQuestion is: " + questionsForThisGame.get(questions).getQuestion());
                        System.out.println("P1 Question number" + questions);
                        questions++;
                    }
                } else if (inObj instanceof String) {
                    questions = 0;
                    processedObject = questionsForThisGame.get(questions);
                    questions++;
                }
            }
            case PLAYER_TWO_CHOOSE_CATEGORY -> {
                /*
                Om vi får in en sträng från klienten så är det svaret på vilken kategori spelaren valt.
                Vi returnerar då den första frågan till klienten och sätter state som PLAYER_TWO_QUESTION.

                 */
                if (inObj instanceof String){
                    questions = 0;
                    questionsForThisGame = qm.getQuestions((String) inObj);
                    processedObject = questionsForThisGame.get(questions);
                    questions++;
                    System.out.println("P2 Question number " + questions);
                    System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                    state = PLAYER_TWO_QUESTION;
                    categories++;
                }
                if (inObj instanceof List<?> ){
                    tempCategoryList.add((String) ((List<?>) inObj).get(0));
                    tempCategoryList.add((String) ((List<?>) inObj).get(1));
                    processedObject = tempCategoryList;
                }
                state = PLAYER_TWO_QUESTION;
                if (inObj instanceof Integer){
                    System.out.println("P2 CATEGORY FICK IN EN INT");
                    state = PLAYER_TWO_CHOOSE_CATEGORY;
                }
                System.out.println("P2 QUESTION COUNTER: " + questions);
            }
            case PLAYER_TWO_QUESTION -> {
                if (inObj instanceof Integer){
                    if (questions == inputQuestions){
                        if ((Integer) inObj == 1){
                            System.out.println("RÄTT");
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("questions == inputQuestions");
                        if (categories == inputCategories){
                            System.out.println("categories == inputCategories");
                            processedObject = 3;
                            server.setCurrentPlayer(p1);
                            System.out.println("Current player set as: " + server.currentPlayer.getClientUsername());
                            questions = 0;
                            state = PLAYER_ONE_QUESTION;
                        }
                        if (categories < inputCategories) {
                            System.out.println("categories < inputCategories");
                            state = PLAYER_TWO_CHOOSE_CATEGORY;
                            questions = 0;
                            System.out.println("Questions set at: " + questions);
                            processedObject = server.getListOfCategories();
                        }

                    } else if (questions < inputQuestions){
                        if ((Integer) inObj == 1){
                            System.out.println("RÄTT");
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("Vi är i questions < inputQuestions");
                        processedObject = questionsForThisGame.get(questions);
                        System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                        questions++;
                    }
                } else if (inObj instanceof String) {
                    processedObject = questionsForThisGame.get(questions);
                    questions++;
                }
                System.out.println("P2 QUESTION COUNTER: " + questions);
            }

            case GAME_END -> {
                gameEnd = true;
                processedObject = gameEnd;
            }
        }
        System.out.println("EXIT STATE = " + state);
        System.out.println("Process returning: " + processedObject);
        return processedObject;
    }
    public int getState() {
        return state;
    }
}
