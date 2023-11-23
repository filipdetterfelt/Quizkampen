import QuestionManager.Question;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class GameWindow extends JFrame {
    //test
    //startScreen
    JPanel startScreenPanel = new JPanel();
    JLabel waitingForOpponentLabel = new JLabel("Väntar på att en motståndare ska koppla upp sig...");

    //categoryScreen
    JPanel categoryScreenPanel = new JPanel(new GridLayout(3,1,10,10));
    JPanel categoryCenteringPanel = new JPanel(new GridBagLayout());
    JLabel categoryLabel = new JLabel("Välj en kategori");
    JButton category1Btn = new JButton("Kategori 1");
    JButton category2Btn = new JButton("Kategori 1");

    //questionsScreen
    JPanel questionsScreenPanel = new JPanel(new GridLayout(2,1,10,10));
    JPanel questionCenteringPanel = new JPanel(new GridBagLayout());
    JPanel questionsPanel = new JPanel(new GridLayout(2,2,10,10));
    JLabel questionLabel = new JLabel("Frågan visas här");
    JButton answer1Btn = new JButton("Svar 1");
    JButton answer2Btn = new JButton("Svar 2");
    JButton answer3Btn = new JButton("Svar 3");
    JButton answer4Btn = new JButton("Svar 4");

    //waitingForOpponentScreen
    JPanel waitingForOpponentPanel = new JPanel(new GridLayout(2,1,10,10));
    JPanel waitingPanel = new JPanel(new GridBagLayout());
    JLabel waitingForOpponentAnswerLabel = new JLabel("Väntar på att motståndaren ska spela..");

    //resultPanel
    JPanel scorePanel = new JPanel(new GridLayout(1,2,10,10));
    JPanel playerCenterPanel = new JPanel(new GridBagLayout());
    JPanel playerScorePanel = new JPanel(new GridLayout(2,1,10,10));
    JPanel opponentScorePanel = new JPanel(new GridLayout(2,1,10,10));
    JPanel opponentCenterPanel = new JPanel(new GridBagLayout());
    JLabel playerLabel = new JLabel("Dina poäng:");
    JLabel playerScoreLabel = new JLabel("2");
    JLabel opponentLabel = new JLabel("Motståndarens poäng:");
    JLabel opponentScoreLabel = new JLabel("0");
    Question question = new Question();
    String userInput = null;
    public GameWindow(String userInput){this.userInput = userInput;}
    public GameWindow(){}

    //TODO
    /*
    Saknas funktion för att rätta ett svar och få tillbaka informationen från servern.
     */

    public void drawStartScreen(){
        add(startScreenPanel);
        startScreenPanel.setLayout(new GridBagLayout());
        startScreenPanel.add(waitingForOpponentLabel);

        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void drawCategoryScreen(String category1, String category2){

        AtomicReference<String> mess = null;
        startScreenPanel.setVisible(false);
        add(categoryScreenPanel);
        categoryScreenPanel.add(categoryCenteringPanel);
        categoryCenteringPanel.add(categoryLabel);
        categoryScreenPanel.add(category1Btn);
        categoryScreenPanel.add(category2Btn);

        //Ge knapparna varsin kategori
        category1Btn.setText(category1);
        category2Btn.setText(category2);

        //Ritar upp frågeskärmen med den frågan som servern valt
        category1Btn.addActionListener(e -> {
            System.out.println(category1);
            //mess.set(category1);
        });
        category2Btn.addActionListener(e -> {
            System.out.println(category2);
            //mess = category2;
        });

        category1Btn.setFocusable(false);
        category2Btn.setFocusable(false);
        categoryScreenPanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));

        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

       // return mess;

    }

    public boolean drawQuestionsScreen(Question recievedQuestion){
        AtomicBoolean correctAnswerBool = new AtomicBoolean(false);
        categoryScreenPanel.setVisible(false);
        resetBackgrounds();
        add(questionsScreenPanel);
        questionsScreenPanel.add(questionCenteringPanel);
        questionCenteringPanel.add(questionLabel);
        questionsScreenPanel.add(questionsPanel);
        questionsPanel.add(answer1Btn);
        questionsPanel.add(answer2Btn);
        questionsPanel.add(answer3Btn);
        questionsPanel.add(answer4Btn);

        //Hämta & lagra svarets index från frågan
        int correctAnswer = recievedQuestion.getCorrectOptionIndex();

        //Hämta & sätt ut frågan och svarsalternativen från frågan
        questionLabel.setText(recievedQuestion.getQuestion());
        answer1Btn.setText(Arrays.asList(recievedQuestion.getOptions()).get(0));
        answer2Btn.setText(Arrays.asList(recievedQuestion.getOptions()).get(1));
        answer3Btn.setText(Arrays.asList(recievedQuestion.getOptions()).get(2));
        answer4Btn.setText(Arrays.asList(recievedQuestion.getOptions()).get(3));

        answer1Btn.addActionListener(e -> {
            System.out.println(answer1Btn.getText());
            if (checkAnswer(0,correctAnswer,answer1Btn)){
                System.out.println("Rätt svar!");
                correctAnswerBool.set(true);
            }
        });
        answer2Btn.addActionListener(e -> {
            System.out.println(answer2Btn.getText());
            if (checkAnswer(1,correctAnswer,answer2Btn)){
                System.out.println("Rätt svar!");
                correctAnswerBool.set(true);
            }
        });
        answer3Btn.addActionListener(e -> {
            System.out.println(answer3Btn.getText());
            if (checkAnswer(2,correctAnswer, answer3Btn)){
                System.out.println("Rätt svar!");
                correctAnswerBool.set(true);
            }
        });
        answer4Btn.addActionListener(e -> {
            System.out.println(answer4Btn.getText());
            if (checkAnswer(3,correctAnswer, answer4Btn)){
                System.out.println("Rätt svar!");
                correctAnswerBool.set(true);
            }
        });

        questionsScreenPanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        return correctAnswerBool.get();
    }

    public void drawWaitingForOpponentScreen(){

        add(waitingForOpponentPanel);
        waitingForOpponentPanel.add(scorePanel);
        waitingForOpponentPanel.add(waitingPanel);
        waitingPanel.add(waitingForOpponentAnswerLabel);
        scorePanel.add(playerCenterPanel);
        playerCenterPanel.add(playerScorePanel);
        scorePanel.add(opponentCenterPanel);
        opponentCenterPanel.add(opponentScorePanel);
        playerScorePanel.add(playerLabel);
        playerScorePanel.add(playerScoreLabel);
        opponentScorePanel.add(opponentLabel);
        opponentScorePanel.add(opponentScoreLabel);



        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void drawResultScreen(int playerPoints, int opponentPoints){
        add(scorePanel);
        scorePanel.add(playerCenterPanel);
        playerCenterPanel.add(playerScorePanel);
        playerScorePanel.add(playerLabel);
        playerScorePanel.add(playerScoreLabel);
        scorePanel.add(opponentCenterPanel);
        opponentCenterPanel.add(opponentScorePanel);
        opponentScorePanel.add(opponentLabel);
        opponentScorePanel.add(opponentScoreLabel);



        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //Jämför knappens index mot det korrekta svarets index i frågan
    public boolean checkAnswer(int answeredIndex, int correctIndex, JButton button){
        if (answeredIndex == correctIndex){
            button.setBackground(Color.GREEN);
            return true;
        } else {
            return false;
        }
    }

    public void resetBackgrounds(){
        answer1Btn.setBackground(null);
        answer2Btn.setBackground(null);
        answer3Btn.setBackground(null);
        answer4Btn.setBackground(null);
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        Question testQuestion = new Question("Vilket år inträffade den franska revolutionen?", new String[]{"1789", "1815", "1871", "1799"}, 0);
        //QuestionDatabase database = new QuestionDatabase();
        String testCategory1 = "Historia";
        String testCategory2 = "Sport";

        //gameWindow.drawStartScreen();
        //gameWindow.drawCategoryScreen(testCategory1, testCategory2);
        //gameWindow.drawWaitingForOpponentScreen();
        //gameWindow.drawResultScreen();

        gameWindow.drawQuestionsScreen(testQuestion);

    }

    public String getUserInput() {
        return userInput;
    }
}
