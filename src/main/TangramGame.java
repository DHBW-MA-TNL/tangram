package main;
import frontend.DifficultyPanel;
import javax.swing.*;
import java.awt.*;

/**
 * TangramGame is a class that extends JFrame and represents the main game window.
 * It contains a static score variable and provides methods to create the game window, add score, and run the game.
 */
public class TangramGame extends JFrame {

    /**
     * The score of the game.
     */
    private static int score = 0;

    /**
     * Constructor for the TangramGame class.
     * It initializes the game window with a title, size, minimum size, default close operation, location, resizability, and a new DifficultyPanel.
     */
    public TangramGame() {
        setTitle("Tangram Game");
        setSize(1350, 800);
        setMinimumSize(new Dimension(800, 800));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new DifficultyPanel());
    }

    /**
     * The main method of the TangramGame class.
     * It creates a new TangramGame object and makes it visible.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new TangramGame();
            game.setVisible(true);
        });
    }

    /**
     * This method adds a given score to the current score.
     *
     * @param i The score to add.
     */
    public static void addScore(int i) {
        score = score + i;
    }

    /**
     * This method returns the current score.
     *
     * @return The current score.
     */
    public  static int getScore(){
        return score;
    }
}