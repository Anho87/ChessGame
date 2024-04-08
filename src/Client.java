import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class Client extends JFrame implements ActionListener {
    JPanel northPanel = new JPanel();
    JPanel boardJPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JButton exitButton = new JButton("Exit");
    ArrayList<JButton> buttonList = new ArrayList<>();
    private static JButton sourceButton = null;
    int indexOfFirstClickedButton = 0;
    int indexOfSecondClickedButton = 0;
    BoardPanel bp;
    String ip = "127.0.0.1";
    int inPort = 22222;
    Socket sock = new Socket(ip, inPort);
    PrintWriter outToServer = new PrintWriter(sock.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

    public Client() throws IOException {
        setup();
        for (JButton button : bp.buttonList) {
            button.setEnabled(false);
        }
    }

    public void game() throws IOException {
        String fromServer;
        while (true){
            fromServer = in.readLine();
            if (fromServer.equalsIgnoreCase("waiting")){
                for (JButton button : bp.buttonList) {
                    button.setEnabled(false);
                }
            } else if (fromServer.equalsIgnoreCase("active")) {
                for (JButton button : bp.buttonList) {
                    button.setEnabled(true);
                }
            } else if (fromServer.equalsIgnoreCase("ready")) {
                outToServer.println("Player ready");
            } else if (fromServer.equalsIgnoreCase("player move")) {
                bp.movePiece(in.readLine());
            }
        }
    }
    public void setup() {
        setLayout(new BorderLayout());
        northPanel.add(newGameButton);
        northPanel.add(exitButton);

        bp = new BoardPanel(boardJPanel, buttonList);

        for (JButton b : buttonList) {
            b.addActionListener(this);
        }
        add(boardJPanel);
        exitButton.addActionListener(e -> System.exit(0));
        newGameButton.addActionListener(e -> outToServer.println("NewGame"));
        add(northPanel, BorderLayout.NORTH);
        setTitle("Chess");
        setLocationRelativeTo(null);
        pack();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (sourceButton == null) {
            if (!clickedButton.getText().isEmpty()) {
                sourceButton = clickedButton;
            }
        } else {
            indexOfFirstClickedButton = buttonList.indexOf(clickedButton);
            indexOfSecondClickedButton = buttonList.indexOf(sourceButton);
            bp.movePiece(indexOfFirstClickedButton + " " + indexOfSecondClickedButton);
            outToServer.println(indexOfFirstClickedButton + " " + indexOfSecondClickedButton);
            sourceButton = null;
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.game();
    }

}

