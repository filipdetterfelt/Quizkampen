import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerListener {

    //Konstruktor
    //Använder try with resourches för att socketen vi skapar ska stängas autmoatiskt
    //While loop där vi skapar upp olika instanser , samt startar 2 trådar (clienth)
    //Server instans samt starta tråd

    //sout för skriva ut
    public ServerListener(){
        try(ServerSocket ss = new ServerSocket(55555)){
            while (true){
                ClientHandler clientHandler = new ClientHandler(ss.accept());
                Thread thread1 = new Thread(clientHandler);
                thread1.start();
                System.out.println("A new client has connected");
                ClientHandler clientHandler2 = new ClientHandler(ss.accept());
                Thread thread2 = new Thread(clientHandler);
                thread2.start();
                System.out.println("A new client has connected");
                Server server = new Server(clientHandler, clientHandler2);
                server.start();
            }
        //Hanterar endoffile och IOE exep..
        } catch (EOFException e){
            System.out.println("Slutet av filen");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

//Instans i main för o köra
    public static void main(String[] args) throws IOException {
        ServerListener serverListener = new ServerListener();
    }


}
