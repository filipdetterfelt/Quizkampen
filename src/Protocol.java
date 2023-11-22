public class Protocol {
    private static final int START = 0;
    private static final int QUESTION = 1;
    private static final int ANSWER = 2;
    private static final int RESULT = 3;
    private static final int END = 4;

    private int state = START;

    Player player = new Player();

    public int gameProtocol (int input) {
        // input är en int som berättar hur många frågor/kategorier/spelare osv vi har kvar
        int result = 0;

        if (state == START) {
            // här ska vi vänta på att två spelare ansluter, isåfall går vi vidare.
            if (input == 2) // om antal anslutna spelare är två går vi vidare
                // välj kategori här
                state = QUESTION;

        } else if (state == QUESTION) {
            // här ska man svara på en fråga
            if (input == 0) { // om frågan är besvarad
                if (true) { // metod eller nåt som kollar om det var rätt svar?
                    player.setPointsPerGame(player.getPointsPerGame() + 1);
                }
                state = ANSWER;
            }

        } else if (state == ANSWER) {
            // Här kommer poängen upp när båda har svarat på frågan
            if (input != 0) { // om det finns fler frågor i kategorin att svara på
                result = player.getPointsPerGame();
                state = QUESTION;
            } else {
                // om kategorin är klar går vi vidare till nästa steg
                state = RESULT;
            }

        } else if (state == RESULT) {
            // här kommer totalen upp med vem som vann,
            if (input == 0) {
                // om vi ska ha en ny kategori så väljer vi kategori och hoppar tillbaka till question state
                result = player.getPointsPerGame(); // returnerar totalen för denna omgången
                player.setTotalPoints(player.getTotalPoints() + player.getPointsPerGame()); // sparar poängen till totalen
                player.setPointsPerGame(0); // sätter om poängen per spel till 0
                state = QUESTION;
            } else {
                // om alla kategorier är klara hoppar vi till sista steget
                player.setTotalPoints(player.getTotalPoints() + player.getPointsPerGame()); // sparar poängen till totalen
                state = END;
            }

        } else if (state == END) {
            // här visar vi upp totalen genom alla kategorier vem som vann
            result = player.getTotalPoints();
        }
        return result;
    }
}

