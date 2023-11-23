/*

package OLD;

import OLD.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
    //TODO
    //Bättre låta 2 spelare ansluta så kan de splea mot varandra
    //Starta upp fler trådar
    int clientCounter =1;
    public ServerListener(){
        try(ServerSocket serverSocket = new ServerSocket(11224)) { //Serversokcet med rätt portnr

            while(true){
                Socket socket = serverSocket.accept(); //skapar socket och kopplar på den till ssocket
                Server server = new Server(socket); //Skapar upp instans av server
                Thread thread = new Thread(server); //Skapar upp instans av Thread
                thread.start(); //Startar tråde
                System.out.println("OLD.Client"+clientCounter+" Connected");
                clientCounter++;
            }
        } catch (IOException e){ //Felhantering
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {ServerListener sl = new ServerListener();} //Main skapar upp instans

}


 */
