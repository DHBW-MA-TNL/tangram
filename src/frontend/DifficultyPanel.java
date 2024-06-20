package frontend;

import cfg.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * DifficultyPanel is a class that extends JPanel and implements ActionListener.
 * It represents a panel with difficulty levels for the Tangram game.
 * It contains a list of UiElement objects and a level of difficulty, and provides methods to add a title, add a button with a scale, interpolate a color, show a window, and set UI elements.
 */
public class DifficultyPanel extends JPanel implements ActionListener {

    /**
     * The list of UiElement objects that make up the panel.
     */
    public static final List<UiElement> uiElements = new ArrayList<>();

    /**
     * The level of difficulty of the game.
     */
    public Commons.Difficulty level;

    /**
     * Constructor for the DifficultyPanel class.
     * It initializes the layout, label, and buttons of the panel.
     */
    public DifficultyPanel() {
        setLayout(null);
        //setBackground(Color.lightGray);
        add(new JLabel("Panel three"));

        //setBackgroundImage("/img/tangram_background_2.png");

        // Füge die Schwierigkeitsskala hinzu


        // The middle point X for the area in which the buttons should be placed
        int middleX = 675;
        addTitle("Tangram - ein klassisches", middleX, 10);
        addTitle("Puzzle neu gedacht", middleX, 70);

        var green = Color.GREEN;
        var yellow = Color.YELLOW;
        var orange = Color.ORANGE;
        var red = Color.RED;
        var green_yellow = interpolateColor(green, yellow);
        var orange_red = interpolateColor(orange, red);

        // The middle point Y for the area in which the buttons should be placed
        int middleY = 430;
        // The difference between the single buttons at the X line
        int differX = 238;
        // The difference between the single buttons at the Y line
        int differY = 98;
        addButtonWithScale("Unerfahren", middleX - 2 * differX, middleY + 2 * differY,
                Commons.Difficulty.UNERFAHREN, green, green_yellow, 0);
        addButtonWithScale("Anfänger", middleX - differX, middleY + differY,
                Commons.Difficulty.ANFAENGER, green_yellow, yellow, 1);
        addButtonWithScale("Fortgeschrittener", middleX, middleY,
                Commons.Difficulty.FORTGESCHRITTEN, yellow, orange, 2);
        addButtonWithScale("Experte", middleX + differX, middleY - differY,
                Commons.Difficulty.EXPERTE, orange, orange_red, 3);
        addButtonWithScale("Profi", middleX + 2 * differX, middleY - 2 * differY,
                Commons.Difficulty.PROFI, orange_red, red, 4);


        JLabel test = new JLabel();
        add(test);
        test.setBounds(50, 180, 1230, 500);
    }

    /**
     * This method interpolates a color between two colors.
     *
     * @param c1 The first color.
     * @param c2 The second color.
     * @return The interpolated color.
     */
    private static Color interpolateColor(Color c1, Color c2) {
        float ratio = 0.5f;
        int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(r, g, b);
    }

    /**
     * The main method of the DifficultyPanel class.
     * It creates a new DifficultyPanel object and shows its window.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        var difficulty = new DifficultyPanel();
        difficulty.showWindow();
    }

    /**
     * This method adds a title to the panel.
     *
     * @param text The text of the title.
     * @param x    The x-coordinate of the title.
     * @param y    The y-coordinate of the title.
     */
    private void addTitle(String text, int x, int y) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
        title.setBounds(x - 400, y, 800, 60);
    }

    /**
     * This method adds a button with a scale to the panel.
     *
     * @param text          The text of the button.
     * @param x             The x-coordinate of the button.
     * @param y             The y-coordinate of the button.
     * @param selectedLevel The level of difficulty associated with the button.
     * @param startColor    The start color of the scale.
     * @param endColor      The end color of the scale.
     * @param lvl           The level of the scale.
     */
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
                    getParent().add(new TangramPanel(uiElements.toArray(new frontend.UiElement[0]), lvl));
                    repaint();
                }
        );
        var scalePanel = new DifficultyScalePanel(startColor, endColor, 1);
        scalePanel.setBounds(x - 100, y - 80, 200, 50);
        add(scalePanel);
        repaint();
    }

    /**
     * This method shows the window of the panel.
     */
    private void showWindow() {
        setSize(1350, 770); // Breite x Höhe
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     * This method sets the UI elements of the panel.
     */
    public void setUiElements() {
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int subWindowWidth = windowWidth / 4;
        Polygon sidebarRight = new Polygon(new int[]{windowWidth - subWindowWidth, windowWidth, windowWidth, windowWidth - subWindowWidth}, new int[]{0, 0, windowHeight, windowHeight}, 4);
        uiElements.add(new UiElement(sidebarRight, Color.gray));
    }

    /**
     * This method returns the preferred size of the panel.
     *
     * @return The preferred size of the panel.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }
}