package QuestionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class QuestionDatabase
{

    private HashMap<String, List<Question>> categories;

    public QuestionDatabase()
    {
        this.init();
    }

    private void init()
    {
        // Kategori: Film och underhållning
        this.addQuestion("Film och underhållning", new Question("Vem spelar rollen som Tony Stark/Iron Man i Marvel Cinematic Universe?",
                new String[]{"Robert Downey Jr.", "Chris Hemsworth", "Chris Evans", "Mark Ruffalo"}, 0));

        this.addQuestion("Film och underhållning", new Question("Vem regisserade filmen 'Inception'?",
                new String[]{"Christopher Nolan", "Steven Spielberg", "Quentin Tarantino", "Martin Scorsese"}, 0));

        this.addQuestion("Sport", new Question("Vilket lag vann fotbolls-VM 2018?",
                new String[]{"Frankrike", "Kroatien", "Belgien", "England"}, 0));

        this.addQuestion("Sport", new Question("Vem vann herrsingeln i Wimbledon 2021?",
                new String[]{"Novak Djokovic", "Roger Federer", "Rafael Nadal", "Andy Murray"}, 0));

        this.addQuestion("Musik", new Question("Vem sjunger låten 'Someone Like You'?",
                new String[]{"Adele", "Taylor Swift", "Beyoncé", "Rihanna"}, 0));

        this.addQuestion("Musik", new Question("Vilket band hade albumet 'The Dark Side of the Moon'?",
                new String[]{"Pink Floyd", "The Beatles", "Led Zeppelin", "Queen"}, 0));

        this.addQuestion("Vetenskap och natur", new Question("Vilken planet är närmast solen?",
                new String[]{"Merkurius", "Venus", "Jorden", "Mars"}, 0));

        this.addQuestion("Vetenskap och natur", new Question("Vad kallas studiet av växter?",
                new String[]{"Botanik", "Zoologi", "Geologi", "Ekologi"}, 0));

        this.addQuestion("Historia", new Question("Vilket år inträffade den amerikanska självständighetsförklaringen?",
                new String[]{"1776", "1789", "1812", "1848"}, 0));

        this.addQuestion("Historia", new Question("Vilken person var den första kvinnan att flyga över Atlanten?",
                new String[]{"Amelia Earhart", "Beryl Markham", "Valentina Tereshkova", "Sally Ride"}, 0));

        this.addQuestion("Teknologi", new Question("Vilket år grundades OpenAI?",
                new String[]{"2017", "2015", "2016", "2014"}, 0));

        this.addQuestion("Böcker", new Question("Vem skrev boken '1984'?",
                new String[]{"George Orwell", "Aldous Huxley", "Ray Bradbury", "H.G. Wells"}, 0));

        this.addQuestion("Geografi", new Question("Vad är huvudstaden i Sverige?",
                new String[]{"Stockholm", "Oslo", "Helsingfors", "Köpenhamn"}, 0));

        this.addQuestion("Historia", new Question("Vilket år inträffade den franska revolutionen?",
                new String[]{"1789", "1815", "1871", "1799"}, 0));

        this.addQuestion("Vetenskap och natur", new Question("Vilket element har kemiskt tecken 'H'?",
                new String[]{"Helium", "Kväve", "Väte", "Kol"}, 2));

        this.addQuestion("Vetenskap och natur", new Question("Vem spelar rollen som Tony Stark/Iron Man i Marvel Cinematic Universe?",
                new String[]{"Robert Downey Jr.", "Chris Hemsworth", "Chris Evans", "Mark Ruffalo"}, 0));

        this.addQuestion("Film och underhållning", new Question("Vilket lag vann fotbolls-VM år 2018?",
                new String[]{"Frankrike", "Kroatien", "Argentina", "Brasilien"}, 0));
    }

    public void addQuestion(String category, Question question)
    {
        if (this.categories.containsKey(category))
        {
            this.categories.get(category).add(question);
        }
        else
        {
            List<Question> questionList = new ArrayList<>();
            questionList.add(question);
            this.categories.put(category, questionList);
        }
    }

    public List<Question> getQuestions (String category, int amount)
    {
        List<Question> questionList = new ArrayList<>();

        //TODO: Logic for returning these questions added in random order based on category and amount

        return this.categories.get(category);
    }

}
