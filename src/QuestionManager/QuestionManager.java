package QuestionManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class QuestionManager

{

    public Properties loadProperties() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("src/MyProperties.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
    Properties p = loadProperties();

    int inputQuestions = Integer.parseInt(p.getProperty("questions"));
    int inputCategories = Integer.parseInt(p.getProperty("categories"));




    public QuestionManager() {
        this.init();
    }

    private void init()
    {
        //TODO: Logic for initializing QuestManager
    }

    public List<Question> getQuestions(String category)
    {
        QuestionDatabase qdb = new QuestionDatabase();

        return qdb.getQuestions(category, inputQuestions);
    }

    public List<String> getCategories()
    {
        QuestionDatabase qdb = new QuestionDatabase();

        return qdb.getCategories(inputCategories);
    }


}