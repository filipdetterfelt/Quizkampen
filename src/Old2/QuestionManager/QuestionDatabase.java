package Old2.QuestionManager;

import java.util.*;

public class QuestionDatabase
{

    private final HashMap<String, List<Question>> categories;

    public QuestionDatabase()
    {
        this.categories = new HashMap<>(); // Initialize the HashMap

        this.init();
    }

    private void init()
    {
        // Kategori: Film och underhållning
        this.addQuestion("film och underhållning", new Question("Vem spelar rollen som Tony Stark/Iron Man i Marvel Cinematic Universe?",
                new String[]{"Robert Downey Jr.", "Chris Hemsworth", "Chris Evans", "Mark Ruffalo"}, 0));

        this.addQuestion("film och underhållning", new Question("Vem regisserade filmen 'Inception'?",
                new String[]{"Steven Spielberg", "Christopher Nolan", "Quentin Tarantino", "Martin Scorsese"}, 1));

        this.addQuestion("film och underhållning", new Question("Vem regisserade filmen 'Pulp Fiction'?",
                new String[]{"Steven Spielberg", "Christopher Nolan", "Quentin Tarantino", "Martin Scorsese"}, 2));

        this.addQuestion("film och underhållning", new Question("Vem spelade rollen som Neo i filmen 'The Matrix'?",
                new String[]{"Brad Pitt", "Tom Cruise", "Keanu Reeves", "Leonardo DiCaprio"}, 2));

        this.addQuestion("film och underhållning", new Question("Vilken film vann Oscar för Bästa Film år 2021?",
                new String[]{"The Trial of the Chicago 7", "Minari", "Mank", "Nomadland"}, 3));

        // Kategori: Sport
        this.addQuestion("sport", new Question("Vilket lag vann fotbolls-VM år 2018?",
                new String[]{"Kroatien", "Argentina", "Frankrike", "Brasilien"}, 2));

        this.addQuestion("sport", new Question("Vem vann herrsingeln i Wimbledon 2021?",
                new String[]{"Roger Federer", "Rafael Nadal", "Andy Murray", "Novak Djokovic"}, 3));

        this.addQuestion("sport", new Question("Vem vann guld i herrarnas singel i tennis vid OS 2020?",
                new String[]{"Novak Djokovic", "Rafael Nadal", "Roger Federer", "Alexander Zverev"}, 3));

        this.addQuestion("sport", new Question("Vilket lag vann Super Bowl LV (55) år 2021?",
                new String[]{"Kansas City Chiefs", "Tampa Bay Buccaneers", "New England Patriots", "Los Angeles Rams"}, 1));

        this.addQuestion("sport", new Question("Vilket land vann flest medaljer totalt vid OS 2020 i Tokyo?",
                new String[]{"USA", "Kina", "Japan", "Storbritannien"}, 0));

        // Kategori: Musik
        this.addQuestion("musik", new Question("Vem sjunger låten 'Someone Like You'?",
                new String[]{"Adele", "Taylor Swift", "Beyoncé", "Rihanna"}, 0));

        this.addQuestion("musik", new Question("Vilket band hade albumet 'The Dark Side of the Moon'?",
                new String[]{"The Beatles", "Pink Floyd", "Led Zeppelin", "Queen"}, 1));

        this.addQuestion("musik", new Question("Vem var huvudsångaren i bandet Queen?",
                new String[]{"Freddie Mercury", "John Lennon", "David Bowie", "Mick Jagger"}, 0));

        this.addQuestion("musik", new Question("Vilken sångerska är känd för låten 'Hello'?",
                new String[]{"Adele", "Taylor Swift", "Beyoncé", "Rihanna"}, 0));

        this.addQuestion("musik", new Question("Vilket instrument spelade Jimi Hendrix mest känd för att spela?",
                new String[]{"Bas", "Trummor", "Gitarr", "Piano"}, 2));

        // Kategori: Vetenskap och natur
        this.addQuestion("vetenskap och natur", new Question("Vilken planet är närmast solen?",
                new String[]{"Merkurius", "Venus", "Jorden", "Mars"}, 0));

        this.addQuestion("vetenskap och natur", new Question("Vad kallas det område inom biologin som studerar växtriket?",
                new String[]{"Botanik", "Zoologi", "Geologi", "Ekologi"}, 0));

        this.addQuestion("vetenskap och natur", new Question("Vilket element har kemiskt tecken 'H'?",
                new String[]{"Helium", "Kväve", "Väte", "Kol"}, 2));

        this.addQuestion("vetenskap och natur", new Question("Vilket djur klassificeras som ett däggdjur och kan flyga?",
                new String[]{"Orangutang", "Papegoja", "Fladdermus", "Krokodil"}, 2));

        this.addQuestion("vetenskap och natur", new Question("Vad är den kemiska formeln för vatten?",
                new String[]{"H2O2", "CO2", "H2O", "CH4"}, 2));

        // Kategori: Historia
        this.addQuestion("historia", new Question("Vilket år inträffade den amerikanska självständighetsförklaringen?",
                new String[]{"1776", "1789", "1812", "1848"}, 0));

        this.addQuestion("historia", new Question("Vilken person var den första kvinnan att flyga över Atlanten?",
                new String[]{"Amelia Earhart", "Beryl Markham", "Valentina Tereshkova", "Sally Ride"}, 0));

        this.addQuestion("historia", new Question("Vilket år inträffade den franska revolutionen?",
                new String[]{"1789", "1815", "1871", "1799"}, 0));

        this.addQuestion("historia", new Question("Vilket år bröt första världskriget ut?",
                new String[]{"1914", "1917", "1919", "1918"}, 0));

        this.addQuestion("historia", new Question("Vilket år skedde den amerikanska inbördeskriget?",
                new String[]{"1861", "1850", "1870", "1865"}, 0));

        // Kategori: Teknologi
        this.addQuestion("teknologi", new Question("Vilket år grundades OpenAI?",
                new String[]{"2017", "2015", "2016", "2014"}, 0));

        this.addQuestion("teknologi", new Question("Vilket år lanserades den första iPhone?",
                new String[]{"2006", "2007", "2008", "2005"}, 1));

        this.addQuestion("teknologi", new Question("Vilket företag skapade det första operativsystemet Windows?",
                new String[]{"Apple", "Microsoft", "IBM", "Google"}, 1));

        this.addQuestion("teknologi", new Question("Vilken teknologigrundare grundade företaget SpaceX?",
                new String[]{"Jeff Bezos", "Elon Musk", "Mark Zuckerberg", "Larry Page"}, 1));

        this.addQuestion("teknologi", new Question("Vilket år släpptes det första PlayStation-konsolen?",
                new String[]{"1992", "1994", "1996", "1998"}, 1));

        // Kategori: Böcker
        this.addQuestion("böcker", new Question("Vem skrev boken '1984'?",
                new String[]{"George Orwell", "Aldous Huxley", "Ray Bradbury", "H.G. Wells"}, 0));

        this.addQuestion("böcker", new Question("Vilken författare skapade karaktären Sherlock Holmes?",
                new String[]{"Charles Dickens", "Agatha Christie", "Arthur Conan Doyle", "Edgar Allan Poe"}, 2));

        this.addQuestion("böcker", new Question("Vilken bok är känd för att vara J.R.R. Tolkiens första del i trilogin 'The Lord of the Rings'?",
                new String[]{"The Two Towers", "The Return of the King", "The Fellowship of the Ring", "The Hobbit"}, 2));

        this.addQuestion("böcker", new Question("Vilken författare är känd för boken 'Pride and Prejudice'?",
                new String[]{"Emily Brontë", "Jane Austen", "Charlotte Brontë", "Mary Shelley"}, 1));

        this.addQuestion("böcker", new Question("Vilken författare är känd för att ha skrivit boken 'To Kill a Mockingbird'?",
                new String[]{"John Steinbeck", "Harper Lee", "F. Scott Fitzgerald", "Mark Twain"}, 1));

        // Kategori: Geografi
        this.addQuestion("geografi", new Question("Vad är huvudstaden i Sverige?",
                new String[]{"Stockholm", "Oslo", "Helsingfors", "Köpenhamn"}, 0));

        this.addQuestion("geografi", new Question("Vilket land är världens största till ytan?",
                new String[]{"Ryssland", "Kanada", "Kina", "USA"}, 0));

        this.addQuestion("geografi", new Question("Vilken flod är världens längsta?",
                new String[]{"Amazonfloden", "Nilen", "Mississippifloden", "Yangtzefloden"}, 1));

        this.addQuestion("geografi", new Question("Vilket land har Eiffeltornet?",
                new String[]{"Italien", "Spanien", "Frankrike", "Tyskland"}, 2));

        this.addQuestion("geografi", new Question("Vilket hav är det största i världen?",
                new String[]{"Södra Ishavet", "Atlanten", "Stilla Havet", "Indiska Oceanen"}, 2));

        // Kategori: Programmering(Java)
        this.addQuestion("programmering", new Question("Vad är skillnaden mellan '==' och '.equals()' i Java för att jämföra strängar?",
                new String[]{"De gör samma sak", "==' används för att jämföra objektens referenser medan '.equals()' jämför objektens innehåll", "Det finns ingen skillnad", "'.equals()' används för att jämföra objektens referenser medan '==' jämför objektens innehåll"}, 1));

        this.addQuestion("programmering", new Question("Vilket nyckelord används för att ärva en klass i Java?",
                new String[]{"extends", "implements", "inherit", "inherits"}, 0));

        this.addQuestion("programmering", new Question("Vilket är det grundläggande syntaxen för en for-loop i Java?",
                new String[]{"for (int i = 0; i < 5; i++) {}", "loop (int i = 0; i < 5; i++) {}", "for (i = 0; i < 5) {}", "repeat (int i = 0; i < 5; i++) {}"}, 0));

        this.addQuestion("programmering", new Question("Vad är skillnaden mellan en 'Array' och en 'ArrayList' i Java?",
                new String[]{"Ingen skillnad", "En 'Array' är dynamisk medan en 'ArrayList' är statisk", "En 'Array' är statisk medan en 'ArrayList' är dynamisk", "En 'Array' är enkel att använda medan en 'ArrayList' är komplicerad"}, 2));

        this.addQuestion("programmering", new Question("Vad betyder 'Java' i Java-programmeringsspråket?",
                new String[]{"En förkortning av 'JavaScript Virtual Application'", "En referens till kaffebönan", "En förkortning av 'Just Another Very Advanced'", "Det har ingen specifik betydelse"}, 1));

        this.addQuestion("programmering", new Question("Vilken typ av loop används för att iterera över element i en samling?",
                new String[]{"for", "while", "foreach", "if"}, 2));

        this.addQuestion("programmering", new Question("Vad kallas processen att omdefiniera en metod i en subklass?",
                new String[]{"Överskuggning", "Överbelastning", "Nedärvning", "Abstraktion"}, 0));

        this.addQuestion("programmering", new Question("Vad används 'int', 'String', och 'boolean' som exempel på?",
                new String[]{"Datatyper", "Metoder", "Variabler", "Klasser"}, 0));

        this.addQuestion("programmering", new Question("Vad innebär begreppet 'null' i Java?",
                new String[]{"Ett ogiltigt värde", "En odefinierad variabel", "Ett tomt objekt", "En typ av loop"}, 2));

        this.addQuestion("programmering", new Question("Vilket nyckelord används för att undvika att ett objekt kan ärvas?",
                new String[]{"final", "static", "abstract", "private"}, 0));

        this.addQuestion("astronomi", new Question("Vilken planet är närmast solen i vårt solsystem?",
                new String[]{"Mars", "Jupiter", "Venus", "Jorden"}, 2));

        //Kategori: Astronomi
        this.addQuestion("astronomi", new Question("Vad kallas den fas av månen när hela dess yta är upplyst?",
                new String[]{"Nymåne", "Halvmåne", "Fullmåne", "Månförmörkelse"}, 2));

        this.addQuestion("astronomi", new Question("Vad är en samling av stjärnor som hålls ihop av gravitationen?",
                new String[]{"Planetsystem", "Galax", "Komet", "Nebulosa"}, 1));

        this.addQuestion("astronomi", new Question("Vad är den ljusstarka punkten vid centrum av de flesta galaxer?",
                new String[]{"Svart hål", "Komet", "Supernova", "Galaxkärna"}, 3));

        this.addQuestion("astronomi", new Question("Vad kallas det fenomen där en mindre himlakropp passerar framför en större, skymmer dess ljus och skapar en skugga på jorden?",
                new String[]{"Asteroidbana", "Månförmörkelse", "Solsken", "Ockultation"}, 3));
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
