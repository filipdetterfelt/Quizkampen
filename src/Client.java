import java.io.*;
import java.net.Socket;

public class Client {
    public Client(){
        try(Socket socket = new Socket("127.0.0.1",11223); //Socket med ip o portnr
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true); //Printwriter skriva
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Kunna läsa
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in)); //Läsa från användare

        ){
            //2 strings
            String serverListener="";
            String userListener="";

            serverListener = in.readLine(); //Serverlistener läser från in
            System.out.println(serverListener); //Skriver ut

            while((userListener = inputReader.readLine()) != null){ //Loop om den inte är null
                out.println(userListener); //Skriver de användaren skrev
                System.out.println("Sent to Server: "+userListener); //Skriver ut de användaren skrev

                serverListener = in.readLine(); //Läser från serverlistener
                System.out.println(serverListener); //Skriver ut det som finns i serverListener
            }
        }
        catch (IOException e){ //Felhantering
            e.printStackTrace(); //Skriver ut stack trace vid fel
        }
    }
    public static void main(String[] args) {Client c = new Client();} //Anropar Client med hjälp av c
}
