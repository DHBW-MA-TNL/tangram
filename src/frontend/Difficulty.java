package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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

        //setBackgroundImage("/img/tangram_background_2.png");

        // Füge die Schwierigkeitsskala hinzu
        /*ÜDifficultyScalePanel scalePanel = new DifficultyScalePanel(Color.BLACK, Color.BLUE, Color.RED);
        scalePanel.setBounds(0, 150, 200, 50);
        frame.add(scalePanel);*/


        addTitle("Tangram - ein klassisches", middleX, 10);
        addTitle("Puzzle neu gedacht", middleX, 70);

        var green = Color.GREEN;
        var yellow = Color.YELLOW;
        var orange = Color.ORANGE;
        var red = Color.RED;
        var green_yellow = interpolateColor(green, yellow, 0.5f);
        var orange_red = interpolateColor(orange, red, 0.5f);

        addButtonWithScale("Unerfahren", middleX - 2 * differX, middleY + 2 * differY,
                Level.Difficulty.UNERFAHREN, green, green_yellow);
        addButtonWithScale("Anfänger", middleX - differX, middleY + differY,
                Level.Difficulty.ANFAENGER, green_yellow, yellow);
        addButtonWithScale("Fortgeschrittener", middleX, middleY,
                Level.Difficulty.FORTGESCHRITTEN, yellow, orange);
        addButtonWithScale("Experte", middleX + differX, middleY - differY,
                Level.Difficulty.EXPERTE, orange, orange_red);
        addButtonWithScale("Profi", middleX + 2 * differX, middleY - 2 * differY,
                Level.Difficulty.PROFI, orange_red, red);


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
        title.setBounds(x - 400, y, 800, 60);
    }

    private void addButtonWithScale(String text, int x, int y, Level.Difficulty selectedLevel,
                                    Color startColor, Color endColor) {
        var button = new RoundButton(text);
        button.setBackground(Color.LIGHT_GRAY); // Setze die Hintergrundfarbe des Buttons
        button.setForeground(Color.BLACK); // Setze die Schriftfarbe des Buttons
        frame.add(button);
        button.setBounds(x - 100, y - 25, 200, 50);
        button.addActionListener(e -> {
                    level = selectedLevel;
                    System.out.println(level);
                }
        );
        var scalePanel = new DifficultyScalePanel(startColor, endColor);
        scalePanel.setBounds(x-100, y-80, 200, 50);
        frame.add(scalePanel);
    }

    private static Color interpolateColor(Color c1, Color c2, float ratio) {
        int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(r, g, b);
    }

    // Methode zum Setzen des Hintergrundbildes
    private void setBackgroundImage(String imagePath) {
        try {
            System.out.println("0");
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            System.out.println("1");
            JLabel backgroundLabel = new JLabel(icon);
            System.out.println("2");
            frame.setContentPane(backgroundLabel);
            System.out.println("3");
            frame.getContentPane().setLayout(new BorderLayout());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
