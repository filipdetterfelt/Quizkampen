import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientHandler implements Runnable, Serializable {

    /*
    ClientHandler är både Runnable och Serializable.
    Den är Runnable så våra instanser kan köras på varsin tråd när vi skapar upp
    dom i ServerListener.
    Den är Serializable så vi kan skicka och ta emot en ClientHandler via våra
    input & output streams.

    Notera att våran Socket samt streams är satta som "transient", som gör att dessa fält hoppas över
    när vi ska serialisera detta objekt. Annars kan vi inte skicka objektet då de inte kan serialiseras.
     */
    private transient Socket socket;
    private transient ObjectInputStream inputStream;
    private transient ObjectOutputStream outputStream;
    private String clientUsername;
    private int score;

    /*
    Här initierar vi våra fält i en try sats.
    Samt att vi här läser in clientUsername, som klienten skickar när det skapas upp.
     */
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.clientUsername = (String) inputStream.readObject();
        } catch (IOException e){
            System.out.println("IO EXCEPTION at ClientHandler constructor");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {}

    /*
    Sedan följer getters och setters för åtkomst från Server, Protocol samt GameWindow klasserna
     */
    public String getClientUsername() {
        return clientUsername;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
