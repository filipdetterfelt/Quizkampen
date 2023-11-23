import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int pointsPerGame = 0;
    private int totalPoints = 0;
    protected List<Integer> listOfPoints = new ArrayList<>();
    Socket sock;

    public Player(Socket sock, String name){
        this.sock = sock;
        this.name = name;
    }


    public Player() {
    }

    public Player(String name, int pointsPerGame, int totalPoints) {
        this.name = name;
        this.pointsPerGame = pointsPerGame;
        this.totalPoints = totalPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(int pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public StringBuilder showListOfPoints(List <Integer> listOfPoints){ // skriver ut listan med poäng efter varje omgång
        StringBuilder sb = new StringBuilder(name + "\n");
        int round = 1;
        for (Integer i : listOfPoints) {
            String message = "Runda " + round + ", " + i + " poäng\n";
            sb.append(message);
            round++;
        }
        return sb;
    }

}

