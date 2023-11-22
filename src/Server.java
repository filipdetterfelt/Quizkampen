import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{ //Ärver från Thread klassen
    private Socket socket; //Skapar upp socket
   // QuestionDatabase q = new QuestionDatabase(); //Skapar upp instans av questiondatabse
    public Server(Socket socket) { //Konstruktor med socket som inparameter är de in eller ut i paranttes?
        this.socket = socket;
    }
    @Override //Overridar run metoden
    public void run(){
        try( //I try blocket lägger jag in de som kan gå snett
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true); //writer me autoflush
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//Reader
        ){

            writer.println("Vilket Språk talar man i Sverige: ");//Skriver ut meddealnde



            writer.println("Vilket Språk talar man i Finland: ");

            String clientConnect =""; //String
            String clientConnect2= "";

            //TODO
            //Kolla while loopen
            //Gör 2 läsningar
            while((clientConnect = reader.readLine()) != null){ //Loop sålänge den inte r null
                String questionAnswer= clientConnect.trim(); //String som får clientCOnnect och sen trim
                String questionAnswer2 =clientConnect2.trim();
                if(questionAnswer.equalsIgnoreCase("SVENSKA")){
                    writer.println("Du svarade rätt"); //Skriver de som finns i clientConnect

                }
                else writer.println("Du svarade fel");


                if(questionAnswer2.equalsIgnoreCase("FINSKA")){
                    writer.println("Du svarade rätt"); //Skriver de som finns i clientConnect

                }
                else writer.println("Du svarade fel");




            }

        }
        catch (IOException e){ //Felhantering
            e.printStackTrace();
        }
    }

    //public static void main(String[] args) {Server s = new Server();}
}
