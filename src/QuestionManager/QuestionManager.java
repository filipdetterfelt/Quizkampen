package QuestionManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
/**
    QuestionManager-class
    QuestionManager functions as a ViewModel/Controller for the QuestionDatabase,
    with methods to retrieve/return a set of categories/questions based on the MyProperties.properties-file
 */
public class QuestionManager

{
    //Method for returning a List<Questions> with Questions
    public List<Question> getQuestions(String category)
    {
        QuestionDatabase qdb = new QuestionDatabase();
        List<Question> questionList = qdb.getQuestions(category, 2);
        System.out.println("QuestionManager - getQuestions() -> Fething " + questionList.size() + " randomized questions");

        return questionList;
    }

    //Method for returning a List<String> with categories
    public List<String> getCategories()
    {
        QuestionDatabase qdb = new QuestionDatabase();
        List<String> categoryList = qdb.getCategories(4);
        System.out.println("QuestionManager - getCategories() -> Fething " + categoryList.size() + " randomized categories");

        return categoryList;
    }
}