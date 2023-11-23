import QuestionManager.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    Player player = new Player();
    public TestProtocol() throws IOException {
        System.out.println("Protocol initialized");
    }

    public Object process(Object inObj) {
        Object outObj = null;
        List<String> tempList = new ArrayList<>();

        if (state == AWAITING_CLIENT_CONNECTION) {
            if (inObj instanceof Integer) {
                if (inObj == (Integer) 2) {
                    state = PLAYER_ONE_CHOOSE_CATEGORY;
                    System.out.println("state set as = PLAYER_ONE_CHOOSE_CATEGORY");
                }
            }
        } else if (state == PLAYER_ONE_CHOOSE_CATEGORY) {
            System.out.println("state == PLAYER_ONE_QUESTION");
            if (inObj instanceof List<?>){
                tempList.add((String) ((List<?>) inObj).get(0));
                tempList.add((String) ((List<?>) inObj).get(1));
                outObj = tempList;
            }
        }
        return outObj;
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
