/*

package OLD;

import QuestionManager.QuestionManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{ //Ärver från Thread klassen
    private Socket socket; //Skapar upp socket
   // QuestionDatabase q = new QuestionDatabase(); //Skapar upp instans av questiondatabse
    public Server(Socket socket) { //Konstruktor med socket som inparameter är de in eller ut i paranttes?
        this.socket = socket;
    }
    @Override //Overridar run metoden
    public void run(){
        QuestionManager qm = new QuestionManager();

        List<String> listOfCategories = qm.getCategories();
        try( //I try blocket lägger jag in de som kan gå snett
             ObjectOutputStream objectWriter = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true); //writer me autoflush
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//Reader
             ObjectInputStream objectReader = new ObjectInputStream(socket.getInputStream());

        ){

            objectWriter.writeObject(listOfCategories);


            //writer.println("Vilket Språk talar man i Finland: ");

            String clientConnect =""; //String
            String clientConnect2= "";
            String temp;

            //TODO
            //Kolla while loopen
            //Gör 2 läsningar
            while((temp = reader.readLine()) != null){ //Loop sålänge den inte r null
                writer.println("Klienten valde: " + temp);
            }

        }
        catch (IOException e){ //Felhantering
            e.printStackTrace();
        }
    }

    //public static void main(String[] args) {OLD.Server s = new OLD.Server();}
}


 */
