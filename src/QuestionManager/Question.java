package QuestionManager;

public class Question
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
}