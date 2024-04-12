package PieceFactory;

import javax.swing.*;
import java.util.ArrayList;

public interface Movement {

    ArrayList<int[]> move(JButton[][] buttons, int rowPosition, int colPosition);
    
}
