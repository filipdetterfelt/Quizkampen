package QuestionManager;

import java.util.*;

public class QuestionManager
{

    public QuestionManager() {
        this.init();
    }

    private void init()
    {
        //TODO: Logic for initializing QuestManager
    }

    public List<Question> getQuestions(String category, int amount)
    {
        QuestionDatabase qdb = new QuestionDatabase();

        return qdb.getQuestions(category, amount);
    }

    public static void main(String[] args) {
        QuestionDatabase qdb = new QuestionDatabase();
        List<Question> listOfQuestions = qdb.getQuestions("geografi", 2);

        for (Question question : listOfQuestions)
        {
            System.out.println(question.getQuestion());
        }


    }

    public List<String> getCategories() {

        QuestionDatabase qdb = new QuestionDatabase();

        return qdb.getCategories();

    }


}