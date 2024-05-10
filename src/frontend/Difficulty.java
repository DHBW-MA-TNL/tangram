package frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Difficulty extends JFrame implements ActionListener {

    private JFrame frame = new JFrame("Tangram - ein klassisches Puzzle neu gedacht");
    private JButton btn_unerfahren;
    private JButton btn_anfaenger;
    private JButton btn_fortgeschritten;
    private JButton btn_experte;
    private JButton btn_profi;
    public enum Level {
        UNERFAHREN,
        ANFAENGER,
        FORTGESCHRITTEN,
        EXPERTE,
        PROFI
    }

    public Difficulty() {
        super("Tangram - ein klassisches Puzzle neu gedacht");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
    }

    private void showWindow(){
        frame.setSize(1366, 768); // Breite x Höhe
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        var difficulty = new Difficulty();
        difficulty.showWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
