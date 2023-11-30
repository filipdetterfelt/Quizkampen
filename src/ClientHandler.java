import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{

    public static List<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String clientUsername;
    private int score;
    int pointsPerGame;

    public void setPointsPerGame(int pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    int totalPoints;

    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            clientHandlers.add(this);
            this.clientUsername = (String) inputStream.readObject();
            System.out.println(clientUsername + " has connected");

        } catch (IOException e){
            closeEverything(socket,inputStream,outputStream);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public ClientHandler() {
    }

    @Override
    public void run() {
        
    }
    public Socket getSocket() {
        return socket;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void broadcastMessage(Object messageToSend){
        try {
            outputStream.writeObject(messageToSend);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("BLÄ");
        }


    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat");
    }

    public void closeEverything(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream){
        removeClientHandler();
        try {
            if (inputStream != null){
                inputStream.close();
            }
            if (outputStream != null){
                outputStream.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
