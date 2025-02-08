import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
    
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

