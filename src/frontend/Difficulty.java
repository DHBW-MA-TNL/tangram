package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import backend.Level;

public class Difficulty extends JFrame implements ActionListener {

    private final JFrame frame = new JFrame("Tangram - ein klassisches Puzzle neu gedacht");
    public Level.Difficulty level;

    private int middleX = 675;  // The middle point X for the area in which the buttons should be placed
    private int middleY = 430;  // The middle point Y for the area in which the buttons should be placed
    private int differX = 238;  // The difference between the single buttons at the X line
    private int differY = 98;   // The difference between the single buttons at the Y line


    public Difficulty() {
        super("Tangram - ein klassisches Puzzle neu gedacht");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        addTitle("Tangram - ein klassisches", middleX, 10);
        addTitle("Puzzle neu gedacht", middleX, 70);

        addButton("Unerfahren", middleX - 2 * differX, middleY + 2 * differY, Level.Difficulty.UNERFAHREN);
        addButton("Anfänger", middleX - differX, middleY + differY, Level.Difficulty.ANFAENGER);
        addButton("Fortgeschrittener", middleX, middleY, Level.Difficulty.FORTGESCHRITTEN);
        addButton("Experte", middleX + differX, middleY - differY, Level.Difficulty.EXPERTE);
        addButton("Profi", middleX + 2 * differX, middleY - 2 * differY, Level.Difficulty.PROFI);

        JLabel test = new JLabel();
        frame.add(test);
        test.setBounds(50, 180, 1230, 500);
        //test.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        frame.setResizable(false);
    }

    private void addTitle(String text, int x, int y) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title);
        title.setBounds(x-400, y, 800, 60);
    }

    private void addButton(String text, int x, int y, Level.Difficulty selectedLevel) {
        JButton button = new JButton(text);
        frame.add(button);
        button.setBounds(x - 100, y - 25, 200, 50);
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
