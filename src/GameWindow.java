import QuestionManager.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class GameWindow extends JFrame implements ActionListener{
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

    String userInput = null;
    Client c = new Client();
    ObjectOutputStream out;
    public GameWindow (ObjectOutputStream o, Client c){
        this.out = o;
        this.c = c;
        answer1Btn.addActionListener(this);
        answer2Btn.addActionListener(this);
        answer3Btn.addActionListener(this);
        answer4Btn.addActionListener(this);
        category1Btn.addActionListener(this);
        category2Btn.addActionListener(this);
    }

    public void drawStartScreen(String username){
        add(startScreenPanel);
        startScreenPanel.setLayout(new GridBagLayout());
        waitingForOpponentLabel.setText("Välkommen " + username + ".");
        startScreenPanel.add(waitingForOpponentLabel);

        setTitle("Quizkampen: ");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void drawCategoryScreen(String category1, String category2){
        clearFrame(startScreenPanel);
        clearFrame(questionsScreenPanel);

        add(categoryScreenPanel);
        categoryScreenPanel.add(categoryCenteringPanel);
        categoryCenteringPanel.add(categoryLabel);
        categoryScreenPanel.add(category1Btn);
        categoryScreenPanel.add(category2Btn);

        //Ge knapparna varsin kategori
        category1Btn.setText(category1);
        category2Btn.setText(category2);

        revalidate();
        repaint();
        category1Btn.setFocusable(false);
        category2Btn.setFocusable(false);
        categoryScreenPanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));

        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    public void drawQuestionsScreen(Question recievedQuestion){
        System.out.println("Drawing questionsScreen");
        clearFrame(categoryScreenPanel);
        clearFrame(startScreenPanel);
        clearFrame(waitingForOpponentPanel);
        resetBackgrounds();
        add(questionsScreenPanel);
        questionsScreenPanel.add(questionCenteringPanel);
        questionCenteringPanel.add(questionLabel);
        questionsScreenPanel.add(questionsPanel);
        questionsPanel.add(answer1Btn);
        questionsPanel.add(answer2Btn);
        questionsPanel.add(answer3Btn);
        questionsPanel.add(answer4Btn);

        revalidate();
        repaint();

        //Hämta & lagra svarets index från frågan
        int correctAnswer = recievedQuestion.getCorrectOptionIndex();

        //Hämta & sätt ut frågan och svarsalternativen från frågan
        questionLabel.setText(recievedQuestion.getQuestion());
        answer1Btn.setText(Arrays.asList(recievedQuestion.getOptions()).get(0));
        answer2Btn.setText(Arrays.asList(recievedQuestion.getOptions()).get(1));
        answer3Btn.setText(Arrays.asList(recievedQuestion.getOptions()).get(2));
        answer4Btn.setText(Arrays.asList(recievedQuestion.getOptions()).get(3));

        questionsScreenPanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
    }

    public void drawWaitingForOpponentScreen(int tempInt){
        clearFrame(questionsScreenPanel);

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

        playerScoreLabel.setText(String.valueOf(tempInt));
        revalidate();
        repaint();
        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        revalidate();
        repaint();
        setTitle("Quizkampen");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == answer1Btn){
            System.out.println("Click registered for answerbtn1");
            if (checkAnswer(0,c.tempQ.getCorrectOptionIndex(),answer1Btn)){
                try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}
            } else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}
        } else if (e.getSource() == answer2Btn){
            System.out.println("Click registered for answer2Btn");
            if (checkAnswer(1,c.tempQ.getCorrectOptionIndex(),answer2Btn)){
                try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}
            } else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}
        } else if (e.getSource() == answer3Btn){
            System.out.println("Click registered for answer3Btn");
            if (checkAnswer(2,c.tempQ.getCorrectOptionIndex(),answer3Btn)){
                try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}
            } else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}
        } else if (e.getSource() == answer4Btn){
            System.out.println("Click registered for answer4Btn");
            if (checkAnswer(3,c.tempQ.getCorrectOptionIndex(),answer4Btn)){
                try {out.writeObject(1);} catch (IOException ex) {throw new RuntimeException(ex);}
            } else {try {out.writeObject(0);} catch (IOException ex) {throw new RuntimeException(ex);}}
        } else if(e.getSource() == category1Btn){
            System.out.println("Click registered for category1Btn");
            String temp = category1Btn.getText();
            System.out.println("Vald kategori: " + temp);
            try {out.writeObject(temp);} catch (IOException ex) {throw new RuntimeException(ex);}
        } else if(e.getSource() == category2Btn){
            System.out.println("Click registered for category2Btn");
            String temp = category2Btn.getText();
            System.out.println("Vald kategori: " + temp);
            try {out.writeObject(temp);} catch (IOException ex) {throw new RuntimeException(ex);}
        }
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
        answer1Btn.setBackground(Color.GRAY);
        answer2Btn.setBackground(Color.GRAY);
        answer3Btn.setBackground(Color.GRAY);
        answer4Btn.setBackground(Color.GRAY);
    }
    private void clearFrame(JPanel panel){
        this.remove(panel);
        this.validate();
        this.repaint();
    }

    public static void main(String[] args) {}

    public String getUserInput() {
        return userInput;
    }


}
