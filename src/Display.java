import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    public static void display() {
        JFrame f = new JFrame();
        Display d = new Display();
        f.add(d);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paint(Graphics g) {
        // Draw stuff
    }
}
