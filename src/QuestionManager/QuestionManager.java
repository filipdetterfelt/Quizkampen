package QuestionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class QuestionManager
{
    private HashMap<String, List<Question>> categories;

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


}