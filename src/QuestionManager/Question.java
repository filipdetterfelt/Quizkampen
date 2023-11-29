package QuestionManager;

import java.io.Serializable;
/**
    Question-class
    Question object that contains a question, a String-array with answer-options and a correct answer index.
 */
public class Question implements Serializable
{
    private final String question;
    private final String[] options;
    private final int correctOptionIndex;

    public Question(String question, String[] options, int correctOptionIndex)
    {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String[] getOptions() {
        return options;
    }

    public String getQuestion() {
        return question;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

}