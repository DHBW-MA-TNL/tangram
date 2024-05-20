package frontend;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import backend.Level;

public class Difficulty extends JFrame implements ActionListener {

    private final JFrame frame = new JFrame("Tangram - ein klassisches Puzzle neu gedacht");
    public Level.Difficulty level;


    public Difficulty() {
        super("Tangram - ein klassisches Puzzle neu gedacht");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        addTitle("Tangram - ein klassisches", 275,10);
        addTitle("Puzzle neu gedacht", 275,70);

        addButton("Unerfahren", 100, 100, Level.Difficulty.UNERFAHREN);
        addButton("Anfänger", 200, 200, Level.Difficulty.ANFAENGER);

        frame.setResizable(false);
    }

    private void addTitle(String text, int x, int y) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title);
        title.setBounds(x, y, 800, 60);
    }

    private void addButton(String text, int x, int y, Level.Difficulty selectedLevel) {
        JButton button = new JButton(text);
        frame.add(button);
        button.setBounds(x, y, 200, 50);
        button.addActionListener(e -> {
                    level = selectedLevel;
                    System.out.println(level);
                }
        );
    }

    public Level.Difficulty getLevel() {
        return this.level;
    }

    private void showWindow() {
        frame.setSize(1350, 770); // Breite x Höhe
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        var difficulty = new Difficulty();
        difficulty.showWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
