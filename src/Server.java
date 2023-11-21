import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{ //Ärver från Thread klassen
    private Socket socket; //Skapar upp socket
    QuestionDatabase q = new QuestionDatabase(); //Skapar upp instans av questiondatabse
    public Server(Socket socket) { //Konstruktor med socket som inparameter är de in eller ut i paranttes?
        this.socket = socket;
    }
    @Override //Overridar run metoden
    public void run(){
        try( //I try blocket lägger jag in de som kan gå snett
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true); //writer me autoflush
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//Reader
        ){
            writer.println("Welcome to Quizkampen write something: ");//Skriver ut meddealnde
            String clientConnect =""; //String

            while((clientConnect = reader.readLine()) != null){ //Loop sålänge den inte r null
                String questionAnswer= clientConnect.trim(); //String som får clientCOnnect och sen trim
                writer.println(clientConnect); //Skriver de som finns i clientConnect


            }

        }
        catch (IOException e){ //Felhantering
            e.printStackTrace();
        }
    }

    //public static void main(String[] args) {Server s = new Server();}
}
