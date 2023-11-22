import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Protocol {
    private static final int START = 0;
    private static final int QUESTION = 1;
    private static final int ANSWER = 2;
    private static final int RESULT = 3;
    private static final int END = 4;

    private int state = START;

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

    Player player = new Player();
    int inputQuestions = Integer.parseInt(p.getProperty("questions"));
    int inputCategories = Integer.parseInt(p.getProperty("categories"));
    ServerListener s = new ServerListener(); // serverListener så vi kan se om det är två spelare anslutna


    public String gameProtocol(int input) {
        // input är en int som berättar hur många frågor vi har kvar
        int round = 0;
        String returnString = null;
        if (state == START) { // start där man väljer kategori och ev väntar på spelare 2
            if (s.clientCounter == 2) { // om antal anslutna spelare i server listener är två går vi vidare
                // metod för att välja kategori här

                inputCategories--; // räknar ner kategorierna
                state = QUESTION;
            }

        } else if (state == QUESTION) { // här ska man svara på en fråga
            // metod för att visa fråga här

            if (input == 0) { // om frågan är besvarad
                if (true) { // metod eller nåt som kollar om det var rätt svar?
                    player.setPointsPerGame(player.getPointsPerGame() + 1);
                }
                inputQuestions--; // räknar ner frågorna
                state = ANSWER;
            }

        } else if (state == ANSWER) { // Här kommer poängen upp när båda har svarat på frågan
            returnString = player.getPointsPerGame() + " poäng";
            if (inputQuestions != 0) { // om det finns fler frågor i kategorin att svara på
                state = QUESTION;
            } else { // om kategorin är klar går vi vidare till nästa steg
                state = RESULT;
            }

        } else if (state == RESULT) { // här kommer totalen upp med vem som vann omgången
            if (inputCategories != 0) {// om vi ska ha en ny kategori så går vi tillbaka till start
                if (bothPlayerHasPlayed) { // metod för att kolla om båda spelare har gjort färdigt omgången
                    player.listOfPoints.add(round, player.getPointsPerGame()); // sparar resultatet i en lista i playerklassen
                    round++;
                    player.setTotalPoints(player.getTotalPoints() + player.getPointsPerGame()); // sparar poängen till totalen
                    player.setPointsPerGame(0); // sätter om poängen per spel till 0
                    inputQuestions = Integer.parseInt(p.getProperty("questions")); // reset frågeräknare
                    returnString = String.valueOf(player.showListOfPoints(player.listOfPoints)); // skriver ut listan med resultat i alla ronder hittils
                    state = START;
                }
            } else {
                // om alla kategorier är klara hoppar vi till sista steget
                player.setTotalPoints(player.getTotalPoints() + player.getPointsPerGame()); // sparar poängen till totalen
                state = END;
            }

        } else if (state == END) {
            // här visar vi upp totalen genom alla kategorier vem som vann
            inputCategories = Integer.parseInt(p.getProperty("categories")); // reset kategorier
            returnString = player.showListOfPoints(player.listOfPoints) + "\nTotalt " + player.getTotalPoints() + " poäng."; // skriver ut listan med resultat i alla ronder
        }
        return returnString;
    }
}

