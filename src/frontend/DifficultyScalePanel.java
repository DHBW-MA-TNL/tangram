package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * DifficultyScalePanel is a class that extends JPanel.
 * It represents a panel with a difficulty scale in the UI.
 * It contains a start color, an end color, and a type, and overrides the paintComponent method of JPanel to draw the difficulty scale.
 */
public class DifficultyScalePanel extends JPanel {
    /**
     * The type of the difficulty scale.
     */
    private final int type;

    /**
     * The start color of the difficulty scale.
     */
    private final Color startColor;

    /**
     * The end color of the difficulty scale.
     */
    private final Color endColor;

    /**
     * Constructor for the DifficultyScalePanel class.
     * It initializes the start color, end color, and type of the difficulty scale.
     *
     * @param startColor The start color of the difficulty scale.
     * @param endColor   The end color of the difficulty scale.
     * @param type       The type of the difficulty scale.
     */
    public DifficultyScalePanel(Color startColor, Color endColor, int type) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.type = type;
    }

    /**
     * This method returns a GeneralPath object that represents a rounded rectangle.
     * It is used to draw the difficulty scale on the panel.
     *
     * @param width  The width of the rounded rectangle.
     * @param height The height of the rounded rectangle.
     * @return A GeneralPath object that represents a rounded rectangle.
     * @see GeneralPath
     */
    private static GeneralPath getGeneralPath(int width, int height) {
        GeneralPath path = new GeneralPath();

        // Move to the first point (top-left corner)
        path.moveTo(0, 20);

        // Draw a curve from the current point to (20, 0) with a control point at (0, 0)
        path.quadTo(0, 0, 20, 0);

        // Draw a line to the top-right corner
        path.quadTo(width, 0, width, 20);

        // Draw a line to the bottom-right corner
        path.lineTo(width, height);

        // Draw a line to the bottom-left corner
        path.lineTo(20, height);

        // Draw a curve back to the starting point with a control point at (0, height)
        path.lineTo(0, height);

        // Close the path
        path.closePath();
        return path;
    }

    /**
     * This method is called when the panel is to be painted.
     * It creates a new Graphics2D object from the Graphics object passed as an argument, and sets the rendering hint for antialiasing.
     * It then paints the difficulty scale on the panel with a linear or radial gradient, depending on the type of the difficulty scale.
     *
     * @param g The Graphics object to paint on.
     * @see Graphics
     * @see Graphics2D
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Startpunkt (unten links) und Endpunkt (oben rechts) des Farbverlaufs
        float[] fractions = {0.0f, 1.0f};
        Color[] colors = {startColor, endColor};

        if (type == 1) {

            LinearGradientPaint gradient = new LinearGradientPaint(
                    0, 0, width, height, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE
            );

            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, width, height, 20, 20);  // Fülle das Button-Rechteck mit dem Farbverlauf

        } else if (type == 2) {
            RadialGradientPaint gradient = new RadialGradientPaint(
                    (float) width / 2, (float) height / 2, (float) width / 2, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE
            );

            // Create a GeneralPath object
            GeneralPath path = getGeneralPath(width, height);

            // Set the paint for the Graphics2D context
            g2d.setPaint(gradient);

            // Fill the shape
            g2d.fill(path);

        }

    }
}
