import QuestionManager.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import javax.swing.UIManager;

/**
 * GameWindow-class:
 * This class represents the graphical user interface (GUI) for a quiz game.
 * It extends JFrame and implements ActionListener for handling user interactions.
 * It contains different panels and components to display the game screens and manage gameplay.
 */
public class GameWindow extends JFrame implements ActionListener {

    // Panels and Components for various screens of the game
    // (Start screen, Category selection, Questions, Waiting, Results, End)
    JPanel startScreenPanel = new JPanel();
    JLabel waitingForOpponentLabel = new JLabel("Väntar på att en motståndare ska koppla upp sig...");

    //categoryScreen
    JPanel categoryScreenPanel = new JPanel(new GridLayout(4, 1, 10, 10));
    JPanel categoryCenteringPanel = new JPanel(new GridBagLayout());
    JLabel categoryLabel = new JLabel("Välj en kategori");
    JButton category1Btn = new JButton("Kategori 1");
    JButton category2Btn = new JButton("Kategori 1");
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    //questionsScreen
    JPanel questionsScreenPanel = new JPanel(new GridLayout(3, 1, 10, 10));
    JPanel questionCenteringPanel = new JPanel(new GridBagLayout());
    JPanel questionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    JLabel questionLabel = new JLabel("Frågan visas här");
    JButton answer1Btn = new JButton("Svar 1");
    JButton answer2Btn = new JButton("Svar 2");
    JButton answer3Btn = new JButton("Svar 3");
    JButton answer4Btn = new JButton("Svar 4");

    //waitingForOpponentScreen
    JPanel waitingForOpponentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
    JPanel waitingPanel = new JPanel(new GridBagLayout());
    JLabel waitingForOpponentAnswerLabel = new JLabel("Väntar på att motståndaren ska spela..");

    //resultPanel
    JPanel scorePanel = new JPanel(new GridLayout(1, 2, 10, 10));
    JPanel playerCenterPanel = new JPanel(new GridBagLayout());
    JPanel playerScorePanel = new JPanel(new GridLayout(2, 1, 10, 10));
    JPanel opponentScorePanel = new JPanel(new GridLayout(2, 1, 10, 10));
    JPanel opponentCenterPanel = new JPanel(new GridBagLayout());
    JLabel playerLabel = new JLabel("Dina poäng:");
    JLabel playerScoreLabel = new JLabel("2");
    JLabel opponentLabel = new JLabel("Motståndarens poäng:");
    JLabel opponentScoreLabel = new JLabel("0");

    //Endscreen
    JPanel endScreen = new JPanel();
    JPanel northPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel southPanel = new JPanel();
    JButton playAgain = new JButton("Spela igen   ");
    JButton exitGame = new JButton("Avsluta spelet");
    JLabel exit = new JLabel("Spelet är avslutat");
    JLabel player1NameAndScore = new JLabel();
    JLabel player2NameAndScore = new JLabel();

    // Variables related to game state and networking
    String userInput = null;
    JLabel userName;
    Client c = new Client();
    ObjectOutputStream out;

    /*
        Constructor:
        Initializes the GameWindow object with ObjectOutputStream and Client.
        Sets up action listeners for buttons.
     */
    public GameWindow(ObjectOutputStream o, Client c) {
        // Initialization of ObjectOutputStream and Client
        this.out = o;
        this.c = c;

        // Setting up action listeners for various buttons
        answer1Btn.addActionListener(this);
        answer2Btn.addActionListener(this);
        answer3Btn.addActionListener(this);
        answer4Btn.addActionListener(this);
        category1Btn.addActionListener(this);
        category2Btn.addActionListener(this);
        userName = new JLabel(c.username + " ");
        exitGame.addActionListener(this);
        playAgain.addActionListener(this);

        // Setting look for the UI to be compatible with macOS
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }

        // Opaque settings for buttons (also needed for macOS)
        answer1Btn.setOpaque(true);
        answer2Btn.setOpaque(true);
        answer3Btn.setOpaque(true);
        answer4Btn.setOpaque(true);
    }

    // Method to draw the start screen with a welcome message for the user
    public void drawStartScreen(String username) {
        // Drawing the start screen with a welcome message for the user
        add(startScreenPanel);
        startScreenPanel.setLayout(new GridBagLayout());
        waitingForOpponentLabel.setText("Välkommen " + username + ".");
        startScreenPanel.add(waitingForOpponentLabel);

        // Setting up UI components, layout, and appearance
        setTitle("Quizkampen: ");
        startScreenPanel.setBackground(Color.CYAN);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // Method to draw the category selection screen with two buttons for categories
    public void drawCategoryScreen(String category1, String category2) {

        // Drawing the category selection screen with category buttons
        clearFrame(startScreenPanel);
        clearFrame(questionsScreenPanel);
        add(categoryScreenPanel);
        categoryScreenPanel.add(categoryCenteringPanel);
        categoryCenteringPanel.add(userName);
        categoryCenteringPanel.add(categoryLabel);
        categoryScreenPanel.add(category1Btn);
        categoryScreenPanel.add(category2Btn);

        //Category buttons
        category1Btn.setText(category1);
        category2Btn.setText(category2);

        revalidate();
        repaint();
        category1Btn.setFocusable(false);
        category2Btn.setFocusable(false);
        categoryScreenPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        // Setting up UI components, layout, and appearance
        setTitle("Quizkampen");
        categoryScreenPanel.setBackground(Color.CYAN);
        categoryCenteringPanel.setBackground(Color.CYAN);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    // Method to draw the questions screen with a received question and answer buttons
    public void drawQuestionsScreen(Question recievedQuestion) {

        // Drawing the questions screen with the received question and answer buttons
        System.out.println("Drawing questionsScreen");
        clearFrame(categoryScreenPanel);
        clearFrame(startScreenPanel);
        clearFrame(waitingForOpponentPanel);
        resetBackgrounds();

        add(questionsScreenPanel);
        questionsScreenPanel.add(topPanel);
        topPanel.add(userName);
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

        // Setting up UI components, layout, and appearance
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));
        questionsScreenPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        setTitle("Quizkampen");
        questionsScreenPanel.setBackground(Color.CYAN);
        topPanel.setBackground(Color.CYAN);
        questionCenteringPanel.setBackground(Color.CYAN);
        questionsPanel.setBackground(Color.CYAN);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
    }

    // Method to draw the waiting screen while waiting for the opponent's turn
    public void drawWaitingForOpponentScreen(List<Integer> scoreList) {

        clearFrame(questionsScreenPanel);

        // Drawing the waiting screen while displaying the scores
        add(waitingForOpponentPanel);
        waitingForOpponentPanel.add(topPanel);
        topPanel.add(userName);
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

        // Setting up UI components, layout, and appearance
        playerScoreLabel.setText(String.valueOf(scoreList.get(0)));
        opponentScoreLabel.setText(String.valueOf(scoreList.get(1)));
        revalidate();
        repaint();
        setTitle("Quizkampen");
        waitingForOpponentPanel.setBackground(Color.CYAN);
        topPanel.setBackground(Color.CYAN);
        waitingPanel.setBackground(Color.CYAN);
        playerScorePanel.setBackground(Color.CYAN);
        opponentCenterPanel.setBackground(Color.CYAN);
        opponentScorePanel.setBackground(Color.CYAN);
        playerScorePanel.setBackground(Color.CYAN);
        scorePanel.setBackground(Color.CYAN);
        playerCenterPanel.setBackground(Color.CYAN);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        setResizable(false);
    }

    // Method to draw the result screen displaying final scores
    public void drawResultScreen(List<Integer> scoreBoard) {

        // Drawing the result screen displaying final scores
        add(scorePanel);

        scorePanel.add(playerCenterPanel);
        playerCenterPanel.add(playerScorePanel);
        playerScorePanel.add(playerLabel);
        playerScorePanel.add(playerScoreLabel);
        scorePanel.add(opponentCenterPanel);
        opponentCenterPanel.add(opponentScorePanel);
        opponentScorePanel.add(opponentLabel);
        opponentScorePanel.add(opponentScoreLabel);

        playerScoreLabel.setText(String.valueOf(scoreBoard.get(0)));
        opponentScoreLabel.setText(String.valueOf(scoreBoard.get(1)));

        // Setting up UI components, layout, and appearance
        revalidate();
        repaint();
        setTitle("Quizkampen");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    // Method to draw the end screen displaying game results and options to play again or exit
    public void drawEndScreen(ClientHandler p1, ClientHandler p2) {
        clearFrame(scorePanel);
        clearFrame(questionsPanel);
        clearFrame(waitingForOpponentPanel);

        // Drawing the end screen displaying game results and options
        setLayout(new BorderLayout());
        add(endScreen);

        endScreen.setLayout(new BorderLayout());
        endScreen.add(centerPanel, BorderLayout.CENTER);
        endScreen.add(northPanel, BorderLayout.NORTH);
        endScreen.add(southPanel, BorderLayout.SOUTH);

        southPanel.add(exitGame);
        centerPanel.add(exit);
        player1NameAndScore.setText("Spelare: "+p1.getClientUsername() + " " + p1.getScore() + " poäng");
        player2NameAndScore.setText("Spelare: "+p2.getClientUsername() + " " + p2.getScore() + " poäng");
        centerPanel.add(player1NameAndScore);
        centerPanel.add(player2NameAndScore);


        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,50));
        exit.setFont(new Font("Arial",Font.BOLD,40));
        player1NameAndScore.setFont(new Font("Arial",Font.BOLD,25));
        player2NameAndScore.setFont(new Font("Arial",Font.BOLD,25));

        centerPanel.add(exit);

        // Setting up UI components, layout, and appearance
        revalidate();
        repaint();
        setTitle("Quizkampen");
        centerPanel.setBackground(Color.CYAN);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Action handling for category selection buttons and answer buttons (returns 1 if correct answer, otherwise returns 0)
        if (e.getSource() == answer1Btn) {
            System.out.println("Click registered for answerbtn1");
            if (checkAnswer(0, c.tempQ.getCorrectOptionIndex(), answer1Btn)) {
                try {
                    out.writeObject(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    out.writeObject(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource() == answer2Btn) {
            System.out.println("Click registered for answer2Btn");
            if (checkAnswer(1, c.tempQ.getCorrectOptionIndex(), answer2Btn)) {
                try {
                    out.writeObject(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    out.writeObject(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource() == answer3Btn) {
            System.out.println("Click registered for answer3Btn");
            if (checkAnswer(2, c.tempQ.getCorrectOptionIndex(), answer3Btn)) {
                try {
                    out.writeObject(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    out.writeObject(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource() == answer4Btn) {
            System.out.println("Click registered for answer4Btn");
            if (checkAnswer(3, c.tempQ.getCorrectOptionIndex(), answer4Btn)) {
                try {
                    out.writeObject(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    out.writeObject(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource() == category1Btn) {
            System.out.println("Click registered for category1Btn");
            String temp = category1Btn.getText();
            System.out.println("Vald kategori: " + temp);
            try {
                out.writeObject(temp);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == category2Btn) {
            System.out.println("Click registered for category2Btn");
            String temp = category2Btn.getText();
            System.out.println("Vald kategori: " + temp);
            try {
                out.writeObject(temp);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == exitGame) {
            System.out.println("Spelet avslutas");
            System.exit(0);

    }


    /*
     * Method to check the selected answer against the correct answer and update button colors accordingly.
     * Returns true if the answer is correct, else false.
     */
    public boolean checkAnswer(int answeredIndex, int correctIndex, JButton button) {

        // Checking the selected answer against the correct answer and updating button colors
        // Returning true if the answer is correct, else false
        if (answeredIndex == correctIndex) {
            button.setBackground(Color.GREEN);
            return true;
        } else {
            button.setBackground(Color.red);
            return false;
        }
    }

    // Method to reset the background colors of answer buttons to white
    public void resetBackgrounds() {

        // Resetting the background colors of answer buttons to white
        answer1Btn.setBackground(Color.WHITE);
        answer2Btn.setBackground(Color.WHITE);
        answer3Btn.setBackground(Color.WHITE);
        answer4Btn.setBackground(Color.WHITE);
    }

    // Method to clear a specific panel from the frame
    private void clearFrame(JPanel panel) {

        // Removing a specific panel from the frame and refreshing the frame
        this.remove(panel);
        this.validate();
        this.repaint();
    }
}
