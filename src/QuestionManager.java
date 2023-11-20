import java.util.ArrayList;
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
        Question question1 = new Question("Vilket år grundades OpenAI?",
                new String[]{"2017", "2015", "2016", "2014"}, 0);
        Question question2 = new Question("Vem skrev boken '1984'?",
                new String[]{"George Orwell", "Aldous Huxley", "Ray Bradbury", "H.G. Wells"}, 0);

        this.addQuestion("Teknologi", question1);
    }

    public void addQuestion(String category, Question question)
    {
        if (this.categories.containsKey(category)) {
            this.categories.get(category).add(question);
        } else {
            List<Question> questionList = new ArrayList<>();
            questionList.add(question);
            this.categories.put(category, questionList);
        }
    }
}