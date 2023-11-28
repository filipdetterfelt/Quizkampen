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
    private int state = PLAYER_ONE_CHOOSE_CATEGORY;
    private int clients;

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

    List<Question> qList = new ArrayList<>();
    List<Question> questionsForThisGame = new ArrayList<>();
    int questions = 0; //VAR 1
    public Object process(Object inObj) {
        System.out.println("Process har mottagit object: " + inObj);
        int categories = 0;
        Object processedObject = null;

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
                System.out.println("STATE = PLAYER_ONE_CHOOSE_CATEGORY");
                if (inObj instanceof List<?> ){
                    tempCategoryList.add((String) ((List<?>) inObj).get(0));
                    tempCategoryList.add((String) ((List<?>) inObj).get(1));
                    processedObject = tempCategoryList;
                }
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
                    if (questions == inputQuestions){
                        if ((Integer) inObj == 1){
                            System.out.println("RÄTT");
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("questions == inputQuestions");
                        state = PLAYER_TWO_QUESTION;
                        server.setCurrentPlayer(p2);
                        System.out.println("Current player set as: " + server.currentPlayer.getClientUsername());
                        questions = 0;
                        System.out.println("Questions set at: " + questions);
                        processedObject = 3;
                        System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());

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
                }

            }
            case PLAYER_TWO_CHOOSE_CATEGORY -> {
                System.out.println("STATE = PLAYER_TWO_CHOOSE_CATEGORY");
                if (inObj instanceof String){
                    questionsForThisGame = qm.getQuestions((String) inObj);
                    processedObject = questionsForThisGame.get(questions);
                    System.out.println("P2 Question number " + questions);
                    System.out.println("processedObject is: " + questionsForThisGame.get(questions).getQuestion());
                    questions++;
                    state = PLAYER_TWO_QUESTION;
                    System.out.println("State: " + state);
                }

            }
            case PLAYER_TWO_QUESTION -> {
                System.out.println("STATE = PLAYER_TWO_QUESTION");
                if (inObj instanceof Integer){
                    if (questions == inputQuestions){
                        if ((Integer) inObj == 1){
                            System.out.println("RÄTT");
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                        }
                        System.out.println("questions == inputQuestions");
                        state = PLAYER_TWO_CHOOSE_CATEGORY;
                        questions = 0;
                        System.out.println("Questions set at: " + questions);
                        processedObject = server.getListOfCategories();

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
                }
            }
        }
        System.out.println("Process returning: " + processedObject);
        return processedObject;




        /*

        if (state == AWAITING_CLIENT_CONNECTION) {
            System.out.println("state = AWAITING_CLIENT_CONNECTION");
            if (inObj instanceof Integer) {
                if (inObj == (Integer) 2) {
                    state = PLAYER_ONE_CHOOSE_CATEGORY;
                    System.out.println("state set as = PLAYER_ONE_CHOOSE_CATEGORY");
                }
            }
        } else if (state == PLAYER_ONE_CHOOSE_CATEGORY) {
            System.out.println("state == PLAYER_ONE_CHOOSE_CATEGORY");
            if (categories < inputCategories) {
                if (inObj instanceof List<?>) {
                    tempList.add((String) ((List<?>) inObj).get(0));
                    tempList.add((String) ((List<?>) inObj).get(1));
                    processedObject = tempList;
                }
                state = PLAYER_ONE_QUESTION;
            }





        } else if (state == PLAYER_ONE_QUESTION) {
            System.out.println("state == PLAYER_ONE_QUESTION");

            if (inputQuestions > questions ) {
                if (inObj instanceof String) {
                    tempString = (String) inObj;
                    qList = qm.getQuestions(tempString);

                } else if (inObj instanceof Integer) {
                    tempInt = (Integer) inObj;
                    if (tempInt == 1){
                        System.out.println("Poäng: 1");
                    } else if (tempInt == 0){
                        System.out.println("Poäng: 0");
                    }
                    questions++;
                }
                System.out.println("P1 Question number " + questions);
                processedObject = qList.get(questions-1);
                if (inputQuestions < questions){
                    System.out.println("Setting state = PLAYER_TWO_QUESTION");
                    state = PLAYER_TWO_QUESTION;
                }

            }//

        } else if (state == PLAYER_TWO_QUESTION) {
            System.out.println("state == PLAYER_TWO_QUESTION");

            if (inputQuestions > questions ) {
                if (inObj instanceof String) {
                    tempString = (String) inObj;
                    qList = qm.getQuestions(tempString);

                } else if (inObj instanceof Integer) {
                    tempInt = (Integer) inObj;
                    if (tempInt == 1){
                        System.out.println("Poäng: 1");
                    } else if (tempInt == 0){
                        System.out.println("Poäng: 0");
                    }

                    questions++;
                    System.out.println("P2 Question number " + questions);

                }
                processedObject = qList.get(questions-1);

            } else {
                state = PLAYER_TWO_CHOOSE_CATEGORY;
                questions = 1;
            }

        }

         */

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
