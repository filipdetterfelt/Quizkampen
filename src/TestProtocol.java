import QuestionManager.Question;
import QuestionManager.QuestionManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestProtocol {

    //State för att invänta att båda är connected
    private static final int AWAITING_CLIENT_CONNECTION = 0;
    //State för att dela ut kategori till OLD.Player 1, och be OLD.Player 2 att vänta
    private static final int PLAYER_ONE_CHOOSE_CATEGORY = 1;
    //State för att OLD.Player 1 ska få och svara på x frågor
    private static final int PLAYER_ONE_QUESTION = 2;
    //State för att dela ut kategori till OLD.Player 2, och be OLD.Player 1 att vänta
    private static final int PLAYER_TWO_CHOOSE_CATEGORY = 3;
    //State för att OLD.Player 2 ska få och svara på x frågor
    private static final int PLAYER_TWO_QUESTION = 4;
    //State för avslutat spel
    private static final int GAME_END = 5;
    private int state = PLAYER_ONE_CHOOSE_CATEGORY;
    private int clients;
    ClientHandler ch = new ClientHandler();
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
    int rounds = Integer.parseInt(p.getProperty("rounds"));
    Server server;
    ClientHandler currentPlayer;
    ClientHandler p1;
    ClientHandler p2;
    Boolean sendNewCategory = true;

    public TestProtocol(Server server, ClientHandler currentPlayer, ClientHandler p1, ClientHandler p2) throws IOException {
        this.currentPlayer = currentPlayer;
        this.server = server;
        this.p1 = p1;
        this.p2 = p2;
        System.out.println("OLD.Protocol initialized");
    }

    List<Question> qList = new ArrayList<>();
    List<Question> questionsForThisGame = new ArrayList<>();
    int questions = 0; //VAR 1
    int gameRounds = 0;
    public Object process(Object inObj) {
        System.out.println("Process har mottagit object: " + inObj);
        int categories = 0;
        Object processedObject = 5;
        System.out.println(server.getCurrentPlayer().getClientUsername());

        List<String> tempCategoryList = new ArrayList<>();
        List<String> tempCategory2List = new ArrayList<>();
        String tempString;
        int tempInt;
        QuestionManager qm = new QuestionManager();

        switch (state){
            case AWAITING_CLIENT_CONNECTION -> {
                System.out.println("STATE = AWAITING_CLIENT_CONNECTION");


            }
            case PLAYER_ONE_CHOOSE_CATEGORY -> {
/*                processedObject = sendNewCategory;
                System.out.println("Boolean skickad");*/
                questions = 0;
                System.out.println("STATE = PLAYER_ONE_CHOOSE_CATEGORY");
                if (inObj instanceof List<?> ){
                    tempCategoryList.add((String) ((List<?>) inObj).get(0));
                    tempCategoryList.add((String) ((List<?>) inObj).get(1));
                    processedObject = tempCategoryList;
                }
                gameRounds ++;
                state = PLAYER_ONE_QUESTION;
            }
            case PLAYER_ONE_QUESTION -> {
                System.out.println("STATE = PLAYER_ONE_QUESTION");
                /*
                    Om det vi får in från klienten är en sträng, så är det svaret på vilket kategori
                    denne har valt, vi vill då lagra två frågor från den kategorin i questionsForThisGame,
                    och returnera den första.
                */
                if (inObj instanceof String){
                    System.out.println("I player1 questions första if satsen");
                    questionsForThisGame = qm.getQuestions((String) inObj);
                    processedObject = questionsForThisGame.get(questions);
                    System.out.println("P1 Question number" + questions);
                    System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                    questions++;
                }
                /*
                    Om det vi får in är en integer, så är det antingen rätt eller fel (1, 0) från klienten när den har
                    svarat på en fråga. Om det vi får in är svaret på den andra frågan, så vill vi ändra state samt ändra
                    currentPlayer hos servern. Sedan returnerar vi första frågan som klient 1 svarat på. Denna går
                    då till klient 2.
                 */
                else if (inObj instanceof Integer){
                    System.out.println("I player 1 questions första else if");
                    if (questions == inputQuestions){
                        System.out.println("poäng i denna omgången " + ch.pointsPerGame);
                        System.out.println("poäng totalt " + ch.totalPoints);
                        ch.setPointsPerGame(0);
                        if ((Integer) inObj == 1){
                            System.out.println("RÄTT");
                            ch.pointCounter(1);
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("Fråga nr " + questions);
                        System.out.println("questions == inputQuestions");
                        questions = 0;
                        state = PLAYER_TWO_QUESTION;
                        server.setCurrentPlayer(p2);
                        System.out.println("Current player set as: " + server.currentPlayer.getClientUsername());
                        System.out.println("Questions set at: " + questions);
                        processedObject = 3;
                        System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());

                    } else if (questions < inputQuestions){
                        System.out.println("I player 1 questions andra else if");
                        if ((Integer) inObj == 1){
                            ch.pointCounter(1);
                            System.out.println("RÄTT");
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("Fråga nr " + questions);
                        System.out.println("Vi är i questions < inputQuestions");
                        processedObject = questionsForThisGame.get(questions);
                        System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                        questions++;

                    }
                }

            }
            case PLAYER_TWO_CHOOSE_CATEGORY -> {
/*                processedObject = sendNewCategory;
                System.out.println("Boolean skickad");*/
                questions = 0;
                System.out.println("STATE = PLAYER_TWO_CHOOSE_CATEGORY");
                System.out.println("I player2 category");
                if (inObj instanceof List<?> ){
                    tempCategoryList.add((String) ((List<?>) inObj).get(0));
                    tempCategoryList.add((String) ((List<?>) inObj).get(1));
                    processedObject = tempCategoryList;
                }
                gameRounds ++;
                state = PLAYER_TWO_QUESTION;
                break;
 /*               processedObject = sendNewCategory;
                questions = 0;
                System.out.println("STATE = PLAYER_TWO_CHOOSE_CATEGORY");
                if (inObj instanceof String){
                    questionsForThisGame = qm.getQuestions((String) inObj);
                    //processedObject = questionsForThisGame.get(questions);
                    System.out.println("P2 Question number " + questions);
                    System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                    questions++;
                    state = PLAYER_TWO_QUESTION;
                    System.out.println("State: " + state);
                }*/

            }
            case PLAYER_TWO_QUESTION -> {
                System.out.println("STATE = PLAYER_TWO_QUESTION");
                if (inObj instanceof String){
                    System.out.println("I player2 questions första if satsen");
                    questionsForThisGame = qm.getQuestions((String) inObj);
                    processedObject = questionsForThisGame.get(questions);
                    System.out.println("P1 Question number" + questions);
                    System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                    //questions++;
                    break;
                }

                else if (inObj instanceof Integer){
                    if (questions == inputQuestions){
                        System.out.println("I player 2 questions första else if");
                        System.out.println("poäng i denna omgången " + ch.pointsPerGame);
                        System.out.println("poäng totalt " + ch.totalPoints);
                        ch.setPointsPerGame(0);
                        if ((Integer) inObj == 1){
                            ch.pointCounter(1);
                            System.out.println("RÄTT");
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("Fråga nr " + questions);
                        System.out.println("questions == inputQuestions");
                        if (gameRounds < rounds){
                            state = PLAYER_TWO_CHOOSE_CATEGORY;
                            gameRounds++;
                        }
                        else{
                            // totalen kommer upp
                        state = GAME_END;
                        }

                        questions = 0;
                        System.out.println("Questions set at: " + questions);
                        processedObject = server.getListOfCategories();


                    } else if (questions < inputQuestions){
                        System.out.println("I player2 andra else if");
                        if ((Integer) inObj == 1){
                            ch.pointCounter(1);
                            System.out.println("RÄTT");
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("Fråga nr " + questions);
                        System.out.println("Vi är i questions < inputQuestions");
                        processedObject = questionsForThisGame.get(questions);
                        System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                        questions++;
                        break;
                    }
                }

            }
        }
        System.out.println("Process returning: " + processedObject);
        return processedObject;

    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int setClientCounter(int clients){
        return this.clients = clients;
    }

}
