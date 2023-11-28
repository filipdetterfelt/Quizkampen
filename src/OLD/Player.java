package OLD;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;

    protected List<Integer> listOfPoints = new ArrayList<>();
    Socket sock;

    public Player(Socket sock, String username){
        this.sock = sock;
        this.username = username;
    }

    public String getName() {
        return username;
    }

}

