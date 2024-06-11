package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class DifficultyScalePanel extends JPanel {
    private final int type;
    private Color startColor;
    private Color endColor;

    public DifficultyScalePanel(Color startColor, Color endColor, int type) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.type = type;
    }

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

        if(type == 1) {

            LinearGradientPaint gradient = new LinearGradientPaint(
                    0, 0, width, height, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE
            );

            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, width, height, 20, 20);  // Fülle das Button-Rechteck mit dem Farbverlauf

        }
        else if(type == 2) {
            RadialGradientPaint gradient = new RadialGradientPaint(
                    width / 2, height / 2, width / 2, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE
            );

            // Create a GeneralPath object
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
            path.lineTo(0, height );

// Close the path
            path.closePath();

// Set the paint for the Graphics2D context
            g2d.setPaint(gradient);

// Fill the shape
            g2d.fill(path);

             }

    }
}
