package QuestionManager;

import java.util.*;

/**
    QuestionDatabase-class
    QuestionDatabase contains a HashMap with key-value pair: categories(key) -> List<Question>(value)
    with methods to add a question to the HashMap, return a random set of categories and questions
 */
public class QuestionDatabase
{

    private final HashMap<String, List<Question>> categories;

    //Constructor initializing the HashMap and calls for the init() method
    public QuestionDatabase()
    {
        this.categories = new HashMap<>(); // Initialize the HashMap

        this.init();
    }

    //Initialize method that adds multiple questions to the HashMap with different categories
    private void init()
    {
        // Kategori: Film och underhållning
        this.addQuestion("film och underhållning", new Question("Iron Man-skådespelare?",
                new String[]{"Downey Jr.", "Hemsworth", "Evans", "Ruffalo"}, 0));

        this.addQuestion("film och underhållning", new Question("Inception-regissör?",
                new String[]{"Spielberg", "Nolan", "Tarantino", "Scorsese"}, 1));

        this.addQuestion("film och underhållning", new Question("Pulp Fiction-regissör?",
                new String[]{"Spielberg", "Nolan", "Tarantino", "Scorsese"}, 2));

        this.addQuestion("film och underhållning", new Question("Neo i 'The Matrix'?",
                new String[]{"Pitt", "Cruise", "Reeves", "DiCaprio"}, 2));

        this.addQuestion("film och underhållning", new Question("Bästa film vid Oscar 2021?",
                new String[]{"Chicago 7", "Minari", "Mank", "Nomadland"}, 3));


        // Kategori: Sport
        this.addQuestion("sport", new Question("Fotbolls-VM 2018-vinnare?",
                new String[]{"Kroatien", "Argentina", "Frankrike", "Brasilien"}, 2));

        this.addQuestion("sport", new Question("Wimbledon 2021 herrsingel-vinnare?",
                new String[]{"Federer", "Nadal", "Murray", "Djokovic"}, 3));

        this.addQuestion("sport", new Question("OS 2020 herrsingel tennis-guld?",
                new String[]{"Djokovic", "Nadal", "Federer", "Zverev"}, 3));

        this.addQuestion("sport", new Question("Super Bowl LV (55)-vinnare 2021?",
                new String[]{"Chiefs", "Buccaneers", "Patriots", "Rams"}, 1));

        this.addQuestion("sport", new Question("Flest OS 2020 medaljer?",
                new String[]{"USA", "Kina", "Japan", "Storbritannien"}, 0));


        // Kategori: Musik
        this.addQuestion("musik", new Question("'Someone Like You'-sångare?",
                new String[]{"Adele", "Taylor Swift", "Beyoncé", "Rihanna"}, 0));

        this.addQuestion("musik", new Question("'The Dark Side of the Moon'-album?",
                new String[]{"The Beatles", "Pink Floyd", "Led Zeppelin", "Queen"}, 1));

        this.addQuestion("musik", new Question("Queen-huvudsångare?",
                new String[]{"Freddie Mercury", "John Lennon", "David Bowie", "Mick Jagger"}, 0));

        this.addQuestion("musik", new Question("'Hello'-sångerska?",
                new String[]{"Adele", "Taylor Swift", "Beyoncé", "Rihanna"}, 0));

        this.addQuestion("musik", new Question("Hendrix instrument?",
                new String[]{"Bas", "Trummor", "Gitarr", "Piano"}, 2));


        // Kategori: Vetenskap och natur
        this.addQuestion("vetenskap och natur", new Question("Närmaste planet till solen?",
                new String[]{"Merkurius", "Venus", "Jorden", "Mars"}, 0));

        this.addQuestion("vetenskap och natur", new Question("Studium av växtriket?",
                new String[]{"Botanik", "Zoologi", "Geologi", "Ekologi"}, 0));

        this.addQuestion("vetenskap och natur", new Question("Kemiskt tecken för 'H'?",
                new String[]{"Helium", "Kväve", "Väte", "Kol"}, 2));

        this.addQuestion("vetenskap och natur", new Question("Flygande däggdjur?",
                new String[]{"Orangutang", "Papegoja", "Fladdermus", "Krokodil"}, 2));

        this.addQuestion("vetenskap och natur", new Question("Vatten kemiska formel?",
                new String[]{"H2O2", "CO2", "H2O", "CH4"}, 2));


        // Kategori: Historia
        this.addQuestion("historia", new Question("Amerikanska självständighetsförklaringen?",
                new String[]{"1776", "1789", "1812", "1848"}, 0));

        this.addQuestion("historia", new Question("Första kvinnan över Atlanten?",
                new String[]{"Amelia Earhart", "Beryl Markham", "Valentina Tereshkova", "Sally Ride"}, 0));

        this.addQuestion("historia", new Question("Franska revolutionens år?",
                new String[]{"1789", "1815", "1871", "1799"}, 0));

        this.addQuestion("historia", new Question("Första världskrigets utbrott?",
                new String[]{"1914", "1917", "1919", "1918"}, 0));

        this.addQuestion("historia", new Question("Amerikanska inbördeskrigets år?",
                new String[]{"1861", "1850", "1870", "1865"}, 0));


        // Kategori: Teknologi
        this.addQuestion("teknologi", new Question("OpenAI-grundningsår?",
                new String[]{"2017", "2015", "2016", "2014"}, 0));

        this.addQuestion("teknologi", new Question("Första iPhone-lanseringsår?",
                new String[]{"2006", "2007", "2008", "2005"}, 1));

        this.addQuestion("teknologi", new Question("Första Windows skapat av vilket företag?",
                new String[]{"Apple", "Microsoft", "IBM", "Google"}, 1));

        this.addQuestion("teknologi", new Question("SpaceX grundare?",
                new String[]{"Jeff Bezos", "Elon Musk", "Mark Zuckerberg", "Larry Page"}, 1));

        this.addQuestion("teknologi", new Question("Första PlayStation lanseringsår?",
                new String[]{"1992", "1994", "1996", "1998"}, 1));


        // Kategori: Böcker
        this.addQuestion("böcker", new Question("'1984'-författare?",
                new String[]{"George Orwell", "Aldous Huxley", "Ray Bradbury", "H.G. Wells"}, 0));

        this.addQuestion("böcker", new Question("Skapare av Sherlock Holmes?",
                new String[]{"Charles Dickens", "Agatha Christie", "Arthur Conan Doyle", "Edgar Allan Poe"}, 2));

        this.addQuestion("böcker", new Question("Första delen i 'The Lord of the Rings'?",
                new String[]{"The Two Towers", "The Return of the King", "The Fellowship of the Ring", "The Hobbit"}, 2));

        this.addQuestion("böcker", new Question("'Pride and Prejudice'-författare?",
                new String[]{"Emily Brontë", "Jane Austen", "Charlotte Brontë", "Mary Shelley"}, 1));

        this.addQuestion("böcker", new Question("'To Kill a Mockingbird'-författare?",
                new String[]{"John Steinbeck", "Harper Lee", "F. Scott Fitzgerald", "Mark Twain"}, 1));


        // Kategori: Geografi
        this.addQuestion("geografi", new Question("Sveriges huvudstad?",
                new String[]{"Stockholm", "Oslo", "Helsingfors", "Köpenhamn"}, 0));

        this.addQuestion("geografi", new Question("Största landet till ytan?",
                new String[]{"Ryssland", "Kanada", "Kina", "USA"}, 0));

        this.addQuestion("geografi", new Question("Världens längsta flod?",
                new String[]{"Amazonfloden", "Nilen", "Mississippifloden", "Yangtzefloden"}, 1));

        this.addQuestion("geografi", new Question("Land med Eiffeltornet?",
                new String[]{"Italien", "Spanien", "Frankrike", "Tyskland"}, 2));

        this.addQuestion("geografi", new Question("Största havet i världen?",
                new String[]{"Södra Ishavet", "Atlanten", "Stilla Havet", "Indiska Oceanen"}, 2));


        // Kategori: Programmering(Java)
        this.addQuestion("programmering", new Question("'==' vs '.equals()' för strängjämförelse?",
                new String[]{"Samma", "Referenser", "Ingen", "Innehåll"}, 1));

        this.addQuestion("programmering", new Question("Arvnyckelord i Java?",
                new String[]{"extends", "implements", "inherit", "inherits"}, 0));

        this.addQuestion("programmering", new Question("For-loop syntax i Java?",
                new String[]{"for", "loop", "repeat", "while"}, 0));

        this.addQuestion("programmering", new Question("Skillnad mellan 'Array' och 'ArrayList'?",
                new String[]{"Ingen", "Dynamisk", "Statisk", "Enkel"}, 2));

        this.addQuestion("programmering", new Question("Betydelse av 'Java' i Java-språket?",
                new String[]{"Förkortning", "Kaffe", "Avancerad", "Ingen"}, 1));

        this.addQuestion("programmering", new Question("Loop för samlingar?",
                new String[]{"for", "while", "foreach", "if"}, 2));

        this.addQuestion("programmering", new Question("Metod-omdefiniering i subklass?",
                new String[]{"Överskuggning", "Överbelastning", "Nedärvning", "Abstraktion"}, 0));

        this.addQuestion("programmering", new Question("Exempel på 'int', 'String', 'boolean'?",
                new String[]{"Datatyper", "Metoder", "Variabler", "Klasser"}, 0));

        this.addQuestion("programmering", new Question("Betydelsen av 'null' i Java?",
                new String[]{"Ogiltigt", "Odefinierad", "Tomt", "Loop"}, 2));

        this.addQuestion("programmering", new Question("Arvundvikande nyckelord?",
                new String[]{"final", "static", "abstract", "private"}, 0));


        //Kategori: Astronomi
        this.addQuestion("astronomi", new Question("Närmaste planet till solen?",
                new String[]{"Mars", "Jupiter", "Venus", "Jorden"}, 2));

        this.addQuestion("astronomi", new Question("Fas av månen när hela ytan är upplyst?",
                new String[]{"Nymåne", "Halvmåne", "Fullmåne", "Månförmörkelse"}, 2));

        this.addQuestion("astronomi", new Question("Samling av stjärnor som hålls ihop av gravitationen?",
                new String[]{"Planetsystem", "Galax", "Komet", "Nebulosa"}, 1));

        this.addQuestion("astronomi", new Question("Ljusstark punkt vid galaxers centrum?",
                new String[]{"Svart hål", "Komet", "Supernova", "Galaxkärna"}, 0));

        this.addQuestion("astronomi", new Question("Fenomen där mindre himlakropp passerar framför en större och skapar en skugga på jorden?",
                new String[]{"Asteroidbana", "Månförmörkelse", "Solsken", "Ockultation"}, 3));

    }

    //Method for adding the question, if the key already exists in the HashMap (the category name) it'll add the Question-object to the list, otherwise creates a new key for the category
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

    //A method for retrieving a List<Questions> from a specific category,
    // Then shuffles the list and adds set number of questions (based on the amount parameter) to a temporary list which is then returned
    public List<Question> getQuestions(String category, int amount) {

        List<Question> tempQuestionList = new ArrayList<>();
        List<Question> listOfQuestions = this.categories.get(category.toLowerCase());

        if (listOfQuestions != null && !listOfQuestions.isEmpty())
        {
            Collections.shuffle(listOfQuestions);

            for (int i = 0; i < amount; i++) {
                tempQuestionList.add(listOfQuestions.get(i));
            }
        }

        return tempQuestionList;
    }

    //A method for retrieving a List<String> including all the categories(keys) from the HashMap
    // Then shuffles the list and returns set number of categories (based on the amount parameter)
    public List<String> getCategories(int amount) {

        List<String> listOfCategories = new ArrayList<>();
        for (Map.Entry<String, List<Question>> category : categories.entrySet())
        {
            listOfCategories.add(category.getKey());
        }

        Collections.shuffle(listOfCategories);

        List<String> tempCategoriesList = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            tempCategoriesList.add(listOfCategories.get(i));
        }



        return tempCategoriesList;

    }


}
