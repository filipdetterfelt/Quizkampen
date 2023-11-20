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
        Question question3 = new Question("Vad är huvudstaden i Sverige?",
                new String[]{"Stockholm", "Oslo", "Helsingfors", "Köpenhamn"}, 0);

        Question question4 = new Question("Vilket år inträffade den franska revolutionen?",
                new String[]{"1789", "1815", "1871", "1799"}, 0);

        Question question5 = new Question("Vilket element har kemiskt tecken 'H'?",
                new String[]{"Helium", "Kväve", "Väte", "Kol"}, 2);

        Question question6 = new Question("Vem spelar rollen som Tony Stark/Iron Man i Marvel Cinematic Universe?",
                new String[]{"Robert Downey Jr.", "Chris Hemsworth", "Chris Evans", "Mark Ruffalo"}, 0);

        Question question7 = new Question("Vilket lag vann fotbolls-VM år 2018?",
                new String[]{"Frankrike", "Kroatien", "Argentina", "Brasilien"}, 0);

        //TODO: Move to QuestDatabase and find better structure for adding questions
        this.addQuestion("Teknologi", question1);
        this.addQuestion("Böcker", question2);
        this.addQuestion("Geografi", question3);
        this.addQuestion("Historia", question4);
        this.addQuestion("Vetenskap och natur", question6);
        this.addQuestion("Film och underhållning", question7);
        this.addQuestion("Sport", question5);
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