package frontend;

import javax.swing.*;
import java.awt.*;

/**
 * RoundButton is a class that extends JButton.
 * It represents a rounded button in the UI.
 * It overrides the paintComponent and paintBorder methods of JButton to draw a round button.
 */
public class RoundButton extends JButton {
    /**
     * Constructor for the RoundButton class.
     * It initializes the button with the specified label, and sets the button to be transparent and not painted.
     *
     * @param label The label of the button.
     */
    public RoundButton(String label) {
        super(label);
        setOpaque(false); // Important for transparency
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    /**
     * This method is called when the button is to be painted.
     * It creates a new Graphics2D object from the Graphics object passed as an argument, and sets the rendering hint for antialiasing.
     * It sets the color of the Graphics2D object to the background color of the button, and fills a round rectangle with this color.
     * It then calls the paintComponent method of the superclass with the Graphics2D object, and disposes the Graphics2D object.
     *
     * @param g The Graphics object to paint on.
     *
     * @see Graphics
     * @see Graphics2D
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Adjust 30, 30 to change the roundness of the corners
        super.paintComponent(g2);
        g2.dispose();
    }

    /**
     * This method is called when the border of the button is to be painted.
     * It creates a new Graphics2D object from the Graphics object passed as an argument, and sets the rendering hint for antialiasing.
     * It sets the color of the Graphics2D object to the foreground color of the button, and draws a round rectangle with this color.
     * It then disposes the Graphics2D object.
     *
     * @param g The Graphics object to paint on.
     *
     * @see Graphics
     * @see Graphics2D
     */
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Adjust 30, 30 to change the roundness of the corners
        g2.dispose();
    }
}