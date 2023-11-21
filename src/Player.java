public class Player {
    private String name;
    private int pointsPerGame;
    private int totalPoints;

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
}
