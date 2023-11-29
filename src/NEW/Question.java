package NEW;

import java.io.Serializable;

public class Question implements Serializable
{
    private String question;
    private String[] options;
    private int correctOptionIndex;

    public Question(String question, String[] options, int correctOptionIndex)
    {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public Question() {
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