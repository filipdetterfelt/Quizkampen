import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable, Serializable {

    public List<ClientHandler> clientHandlers = new ArrayList<>();
    private transient Socket socket;
    private transient ObjectInputStream inputStream;
    private transient ObjectOutputStream outputStream;
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
            //closeEverything(socket,inputStream,outputStream);
            System.out.println("IOEXCEPTION");
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
    /*
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

     */
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
