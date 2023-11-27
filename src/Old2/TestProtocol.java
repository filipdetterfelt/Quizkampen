package Old2;

import Old2.QuestionManager.Question;
import Old2.QuestionManager.QuestionManager;

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

    private int state = AWAITING_CLIENT_CONNECTION;
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


    Player player = new Player();
    public TestProtocol() throws IOException {
        System.out.println("Protocol initialized");
    }

    List<Question> qList = new ArrayList<>();
    int questions = 1;
    public Object process(Object inObj) {
        int categories = 0;
        Object p1OutObj = null;
        Object p2OutObj = null;

        List<String> tempList = new ArrayList<>();
        String tempString;
        int tempInt;

        QuestionManager qm = new QuestionManager();

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
                    p1OutObj = tempList;
                }
                categories++;
                state = PLAYER_ONE_QUESTION;
            }
            else {
                //player 2 pick category
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
                System.out.println("Questions " + questions);
                p1OutObj = qList.get(questions-1);

            } else if (questions == inputQuestions) {
                System.out.println("Setting state = PLAYER_TWO_QUESTION");
                state = PLAYER_TWO_QUESTION;
                questions = 1;
            }

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
                    System.out.println("Questions " + questions);

                }
                System.out.println("Questions " + questions);
                p2OutObj = qList.get(questions-1);

            } else {
                state = PLAYER_TWO_CHOOSE_CATEGORY;
                questions = 1;
            }

        }
        return p1OutObj;
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
