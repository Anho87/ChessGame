import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    //Tar emot 2 sockets och skapar ett nytt server objekt och startar sedan tråden i servern.
    public ServerListener() {
        try(ServerSocket listener = new ServerSocket(22222)){
            while(true){
                Socket player1 = listener.accept();
                Socket player2 = listener.accept();
                Server game = new Server(player1, player2);
                game.start();
            }
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        new ServerListener();
    }
}

