import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
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

    public GameWindow(){}

    //TODO

    /*
    Saknas funktion för att rätta ett svar och få tillbaka informationen från servern, och sätta
    knappen färg beroende på om svaret är rätt eller fel.

    Saknar funktion för när en ny fråga ska laddas (från server), så ska knapparnas färg ställas tillbaka till null.

     */

    /*
    Startskärm V
    Gamestate 1 skärm, välja kategori V
    Gamestate 2 skärm svara på frågor V
    Gamestate 3 skärm, väntar på motståndaren V
    Gamestate 4 skärm, visa resultat V
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

    public void drawCategoryScreen(){
        add(categoryScreenPanel);
        categoryScreenPanel.add(categoryCenteringPanel);
        categoryCenteringPanel.add(categoryLabel);
        categoryScreenPanel.add(category1Btn);
        categoryScreenPanel.add(category2Btn);

        category1Btn.addActionListener(e -> {
            System.out.println("Valde kategori 1");
            drawQuestionsScreen();
            remove(categoryScreenPanel);
        });
        category2Btn.addActionListener(e -> {
            System.out.println("Valde kategori 2");
            drawQuestionsScreen();
            remove(categoryScreenPanel);
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
    }

    public void drawQuestionsScreen(){
        add(questionsScreenPanel);
        questionsScreenPanel.add(questionCenteringPanel);
        questionCenteringPanel.add(questionLabel);
        questionsScreenPanel.add(questionsPanel);
        questionsPanel.add(answer1Btn);
        questionsPanel.add(answer2Btn);
        questionsPanel.add(answer3Btn);
        questionsPanel.add(answer4Btn);

        answer1Btn.addActionListener(e -> {
            System.out.println(answer1Btn.getText());
            /*
            if (correctAnswer){
                setBackground(Color.GREEN);
            } else {
                setBackground(Color.RED);
            }

             */
        });
        answer2Btn.addActionListener(e -> {
            System.out.println(answer2Btn.getText());
            /*
            if (correctAnswer){
                setBackground(Color.GREEN);
            } else {
                setBackground(Color.RED);
            }

             */
        });
        answer3Btn.addActionListener(e -> {
            System.out.println(answer3Btn.getText());
            /*
            if (correctAnswer){
                setBackground(Color.GREEN);
            } else {
                setBackground(Color.RED);
            }

             */
        });
        answer4Btn.addActionListener(e -> {
            System.out.println(answer4Btn.getText());
              /*
            if (correctAnswer){
                setBackground(Color.GREEN);
            } else {
                setBackground(Color.RED);
            }

             */
        });

        questionsScreenPanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

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

    public void drawResultScreen(){
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

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        //gameWindow.drawStartScreen();
        //gameWindow.drawCategoryScreen();
        //gameWindow.drawWaitingForOpponentScreen();
        gameWindow.drawResultScreen();
    }
}
