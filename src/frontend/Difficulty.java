package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


import cfg.Commons;

public class Difficulty extends JPanel implements ActionListener {


    public static List<UiElement> uiElements = new ArrayList<>();
    public Commons.Difficulty level;

    private final int middleX = 675;  // The middle point X for the area in which the buttons should be placed
    private final int middleY = 430;  // The middle point Y for the area in which the buttons should be placed
    private final int differX = 238;  // The difference between the single buttons at the X line
    private final int differY = 98;   // The difference between the single buttons at the Y line


    public Difficulty() {
        setLayout(null);
        //setBackground(Color.lightGray);
        add(new JLabel("Panel three"));

        //setBackgroundImage("/img/tangram_background_2.png");

        // Füge die Schwierigkeitsskala hinzu


        addTitle("Tangram - ein klassisches", middleX, 10);
        addTitle("Puzzle neu gedacht", middleX, 70);

        var green = Color.GREEN;
        var yellow = Color.YELLOW;
        var orange = Color.ORANGE;
        var red = Color.RED;
        var green_yellow = interpolateColor(green, yellow, 0.5f);
        var orange_red = interpolateColor(orange, red, 0.5f);

        addButtonWithScale("Unerfahren", middleX - 2 * differX, middleY + 2 * differY,
                Commons.Difficulty.UNERFAHREN, green, green_yellow,0);
        addButtonWithScale("Anfänger", middleX - differX, middleY + differY,
                Commons.Difficulty.ANFAENGER, green_yellow, yellow,1);
        addButtonWithScale("Fortgeschrittener", middleX, middleY,
                Commons.Difficulty.FORTGESCHRITTEN, yellow, orange,2);
        addButtonWithScale("Experte", middleX + differX, middleY - differY,
                Commons.Difficulty.EXPERTE, orange, orange_red,3);
        addButtonWithScale("Profi", middleX + 2 * differX, middleY - 2 * differY,
                Commons.Difficulty.PROFI, orange_red, red,4);


        JLabel test = new JLabel();
        add(test);
        test.setBounds(50, 180, 1230, 500);
    }

    private void addTitle(String text, int x, int y) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
        title.setBounds(x - 400, y, 800, 60);
    }

    private void addButtonWithScale(String text, int x, int y, cfg.Commons.Difficulty selectedLevel,
                                    Color startColor, Color endColor,
                                    int lvl) {
        var button = new RoundButton(text);
        button.setBackground(Color.LIGHT_GRAY); // Setze die Hintergrundfarbe des Buttons
        button.setForeground(Color.BLACK); // Setze die Schriftfarbe des Buttons
        add(button);
        button.setBounds(x - 100, y - 25, 200, 50);
        button.addActionListener(e -> {
                    level = selectedLevel;
                    System.out.println(level);

                    setVisible(false);
                    setUiElements();
                    getParent().add(new TangramPanel(uiElements.toArray(new frontend.UiElement[0]),lvl));
                    repaint();
                }
        );
        var scalePanel = new DifficultyScalePanel(startColor, endColor,1);
        scalePanel.setBounds(x-100, y-80, 200, 50);
        add(scalePanel);
        repaint();
    }

    private static Color interpolateColor(Color c1, Color c2, float ratio) {
        int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(r, g, b);
    }


    private void showWindow() {
        setSize(1350, 770); // Breite x Höhe
        setVisible(true);
    }

    public static void main(String[] args) {
        var difficulty = new Difficulty();
        difficulty.showWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void setUiElements() {
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int subWindowWidth = windowWidth / 4;
        Polygon sidebarRight = new Polygon(new int[]{windowWidth - subWindowWidth, windowWidth, windowWidth, windowWidth - subWindowWidth}, new int[]{0, 0, windowHeight, windowHeight}, 4);
        uiElements.add(new UiElement(sidebarRight, Color.gray));
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }
}