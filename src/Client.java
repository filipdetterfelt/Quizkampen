import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    String userInput;

    public Client(){
        try(Socket socket = new Socket("127.0.0.1",11224); //Socket med ip o portnr
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true); //Printwriter skriva
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Kunna läsa
        //BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in)); //Läsa från användare
        //ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());

        ){

            GameWindow g = new GameWindow(userInput);

            Object input;
            List<String> templist = new ArrayList<>();

            while ((input = in.readObject()) != null){
                System.out.println("inne i loopen");
                if (input instanceof List<?>){
                    templist.add((String) ((List<?>) input).get(0));
                    templist.add((String) ((List<?>) input).get(1));
                }
            }
            String cat1 = templist.get(0);
            String cat2 = templist.get(1);
            System.out.println(cat1);
            System.out.println(cat2);

            //g.drawCategoryScreen(cat1,cat2);
            g.category1Btn.addActionListener(e -> {
                System.out.println("Test " + g.category1Btn.getText());
                userInput = g.category1Btn.getText();
                out.println(userInput);
                userInput = null;
            });
            g.category2Btn.addActionListener(e -> {
                System.out.println("Test " + g.category2Btn.getText());
                userInput = g.category2Btn.getText();
                out.println(userInput);
                userInput = null;
            });

            //2 strings
            String serverListener="";
            String userListener="";

            serverListener = in.readLine(); //Serverlistener läser från in
            System.out.println(serverListener); //Skriver ut

            while(g.userInput != null){ //Loop om den inte är null
                out.println(g.userInput); //Skriver de användaren skrev
                System.out.println("Sent to Server: "+userListener); //Skriver ut de användaren skrev

                serverListener = in.readLine(); //Läser från serverlistener
                System.out.println(serverListener); //Skriver ut det som finns i serverListener


            }
        }
        catch (IOException e){ //Felhantering
            e.printStackTrace(); //Skriver ut stack trace vid fel
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {Client c = new Client();} //Anropar Client med hjälp av c
}
