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
    private static final int ROUND_ONE_START = 0;
    //State för att dela ut kategori till Player 1, och be Player 2 att vänta
    private static final int PLAYER_ONE_CHOOSE_CATEGORY = 1;
    //State för att OLD.Player 1 ska få och svara på x frågor
    private static final int PLAYER_ONE_QUESTION = 2;
    //State för att dela ut kategori till OLD.Player 2, och be OLD.Player 1 att vänta
    private static final int PLAYER_TWO_CHOOSE_CATEGORY = 3;
    //State för att OLD.Player 2 ska få och svara på x frågor
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
    int rounds = 0;
    Server server;
    ClientHandler currentPlayer;
    ClientHandler p1;
    ClientHandler p2;
    List<Question> questionsForThisGame = new ArrayList<>();
    ArrayList<Integer> scoreBoard = new ArrayList<>(2);
    int questions = 0;
    int categories = 0;
    boolean gameEnd = false;

    public TestProtocol(Server server, ClientHandler currentPlayer, ClientHandler p1, ClientHandler p2) throws IOException {
        this.currentPlayer = currentPlayer;
        this.server = server;
        this.p1 = p1;
        this.p2 = p2;
    }


    public Object process(Object inObj) throws IOException {

        Object processedObject = null;
        System.out.println(server.getCurrentPlayer().getClientUsername());
        List<String> tempCategoryList = new ArrayList<>();
        QuestionManager qm = new QuestionManager();

        switch (state) {

            case ROUND_ONE_START -> {
                /*
                    När båda spelarna har connectat så kommer spelet starta, och klient 1 kommer alltid att börja med att
                    skicka över listan med blandade kategorier som finns i databasen. Då vill vi returnera en lista med 2
                    utvalda kategorier till klienten, som då ritar upp kategoriskärmen.
                 */
                if (inObj instanceof List<?>) {
                    tempCategoryList.add((String) ((List<?>) inObj).get(0));
                    tempCategoryList.add((String) ((List<?>) inObj).get(1));
                    processedObject = tempCategoryList;
                    state = PLAYER_ONE_CHOOSE_CATEGORY;
                }

                scoreBoard.add(0,0);
                scoreBoard.add(1,0);
            }

            case PLAYER_ONE_CHOOSE_CATEGORY -> {
                /*
                    När klienten returnerar sin valda kategori, så vill vi ta två frågor ur den kategorins lista
                    och lägga dom i en temporär lista med Questions. Sedan returnerar vi den första frågan ur den listan
                    till klienten, och adderar frågorna som skickats med 1. Sedan byter vi state till PLAYER_ONE_QUESTION
                    för att hantera de integer som kommer som svar.
                 */

                if (rounds % 2 != 0) {
                    state = PLAYER_ONE_QUESTION;
                } // vi kollar om antalet rundor är ojämnt, då betyder det att det är p1:s tur att svara på p2:s frågor så då byter vi state

                if (inObj instanceof String) { // om vi som inobjekt får en string, dvs en kategori går vi vidare till question state
                    questionsForThisGame = qm.getQuestions((String) inObj);
                    processedObject = questionsForThisGame.get(questions);
                    questions++;
                    categories++;

                }
                state = PLAYER_ONE_QUESTION;
            }

            case PLAYER_ONE_QUESTION -> {
                 /*
                    Om det vi får in är en integer, så är det antingen rätt eller fel (1, 0) från klienten när den har
                    svarat på en fråga. Om det vi får in är svaret på den andra frågan, så vill vi ändra state samt ändra
                    currentPlayer hos servern. Sedan returnerar vi första frågan som klient 1 svarat på. Denna går då till klient 2.
                 */
                if (inObj instanceof Integer) {
                    if (questions == inputQuestions) {
                        rounds++;
                 /*
                    Är questions == inputQuestions, så har klient 1 nu svarat på sina frågor, och vi vill då sätta
                    currentPlayer hos servern till klient 2. Sedan returnerar vi en 3'a, så klienten ritar upp sin
                    waitScreen med sina poäng. Vi sätter också state till PLAYER_TWO_QUESTION.
                 */
                if ((Integer) inObj == 1){
                            p1.setScore(p1.getScore() +1 );
                            scoreBoard.set(0,p1.getScore());
                            System.out.println("RÄTT");
                            System.out.println("P1 SCORE: " + p1.getScore());
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                            System.out.println("P1 SCORE: " + p1.getScore());
                        }
                        //System.out.println("questions == inputQuestions");
                        processedObject = scoreBoard;
                        // om alla kategorier är gjorda men vi är på ojämnt antal rundor går vi över till p2 som ska svara på p1:s frågor,
                        // annars går vi vidare till game end state
                        if (categories == inputCategories) {
                            System.out.println("Första if");
                            if (rounds % 2 != 0) {
                                System.out.println("Andra if");
                                processedObject = 3;
                                server.setCurrentPlayer(p2);
                                questions = 0;
                                state = PLAYER_TWO_QUESTION;
                            } else {
                                state = GAME_END;
                            }
                        }
                        // om det är fler kategorier kvar och vi är på en udda kategori och runda går vi över till p2 som ska svara på frågorna från p1
                        if (categories < inputCategories && categories % 2 != 0 && rounds % 2 != 0) {
                            System.out.println("Vi är här");
                            processedObject = scoreBoard;
                            server.setCurrentPlayer(p2);
                            questions = 0;
                            state = PLAYER_TWO_QUESTION;
                        }
                        // om det är fler kategorier kvar och vi är på en jämn kategori och runda är det p1:s tur att välja kategori
                        if (categories < inputCategories && categories % 2 == 0 && rounds % 2 == 0) {
                            state = PLAYER_ONE_CHOOSE_CATEGORY;
                            questions = 0;
                            processedObject = server.getListOfCategories();
                        }

                    } else if (questions < inputQuestions) {
                      /*
                    Är question < inputQuestions så ska klient 1 svara på fler frågor.
                    Vi kontrollerar om vi fick rätt eller fel svar (1, 0), och sedan returnerar vi nästa fråga
                    till klienten och sätter questions ++;
                 */

                        if ((Integer) inObj == 1){
                            p1.setScore(p1.getScore() +1 );
                            scoreBoard.set(0,p1.getScore());
                            System.out.println("RÄTT");
                            System.out.println("P1 SCORE: " + p1.getScore());
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                            System.out.println("P1 SCORE: " + p1.getScore());
                        }
                        processedObject = questionsForThisGame.get(questions);
                        questions++;
                    }
                } else if (inObj instanceof String) {
                    questions = 0;
                    processedObject = questionsForThisGame.get(questions);
                    questions++;
                }
            }

            case PLAYER_TWO_CHOOSE_CATEGORY -> {
                //Om vi får in en sträng från klienten så är det svaret på vilken kategori spelaren valt.
                //Vi returnerar då den första frågan till klienten och sätter state som PLAYER_TWO_QUESTION.
                if (inObj instanceof String) {
                    questions = 0;
                    questionsForThisGame = qm.getQuestions((String) inObj);
                    processedObject = questionsForThisGame.get(questions);
                    questions++;
                    state = PLAYER_TWO_QUESTION;
                    categories++;
                }
                if (inObj instanceof List<?>) {
                    tempCategoryList.add((String) ((List<?>) inObj).get(0));
                    tempCategoryList.add((String) ((List<?>) inObj).get(1));
                    processedObject = tempCategoryList;
                }
                state = PLAYER_TWO_QUESTION;

                if (inObj instanceof Integer) {
                    state = PLAYER_TWO_CHOOSE_CATEGORY;
                }

            }

            case PLAYER_TWO_QUESTION -> {
                /*
                    Om det vi får in är en integer, så är det antingen rätt eller fel (1, 0) från klienten när den har
                    svarat på en fråga. Om det vi får in är svaret på den andra frågan, så vill vi ändra state samt ändra
                    currentPlayer hos servern. Sedan returnerar vi första frågan som klient 2 svarat på. Denna går då till klient 1.
                 */
                if (inObj instanceof Integer) {
                  
                    if (questions == inputQuestions) {
                        rounds++;
                      if ((Integer) inObj == 1){
                            p2.setScore(p2.getScore() +1 );
                            scoreBoard.set(1,p2.getScore());
                            System.out.println("RÄTT");
                            System.out.println("P2 SCORE: " + p2.getScore());
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                            System.out.println("P2 SCORE: " + p2.getScore());
                        }
                        
                        // om alla kategorier är gjorda men vi är på ojämnt antal rundor går vi över till p1 som ska svara på p2:s frågor,
                        // annars går vi vidare till game end state
                        if (categories == inputCategories) {
                            if (rounds % 2 != 0) {
                                processedObject = scoreBoard;
                                server.setCurrentPlayer(p1);
                                questions = 0;
                                state = PLAYER_ONE_QUESTION;
                            } else {
                                state = GAME_END;
                            }
                        }
                        // om det är fler kategorier kvar och vi är på en udda kategori och jämn runda går vi över till p1 som ska svara på frågorna från p2
                        if (categories < inputCategories && categories % 2 != 0 && rounds % 2 == 0) {
                            state = PLAYER_TWO_CHOOSE_CATEGORY;
                            questions = 0;
                            processedObject = server.getListOfCategories();
                        }
                        // om det är fler kategorier kvar och vi är på en jämn kategori och udda runda är det p1:s tur att välja kategori
                        if (categories < inputCategories && categories % 2 == 0 && rounds % 2 != 0) {
                            processedObject = scoreBoard;
                            server.setCurrentPlayer(p1);
                            questions = 0;
                            state = PLAYER_ONE_QUESTION;
                        }
                        // Är question < inputQuestions så ska klient 1 svara på fler frågor. Vi kontrollerar om vi fick rätt eller fel svar (1, 0),
                        // och sedan returnerar vi nästa fråga till klienten och sätter questions ++;
                    } else if (questions < inputQuestions) {
                        if ((Integer) inObj == 1){
                            p2.setScore(p2.getScore() +1 );
                            scoreBoard.set(1,p2.getScore());
                            System.out.println("RÄTT");
                            System.out.println("P2 SCORE: " + p2.getScore());
                        } else if ((Integer) inObj == 0){
                            System.out.println("FEL");
                            System.out.println("P2 SCORE: " + p2.getScore());
                        }
                        processedObject = questionsForThisGame.get(questions);
                        questions++;
                    }
                } else if (inObj instanceof String) {
                    processedObject = questionsForThisGame.get(questions);
                    questions++;
                }

            }

            case GAME_END -> {
                gameEnd = true;
                processedObject = gameEnd;
                server.endGame();

            }
        }
        return processedObject;
    }
}

