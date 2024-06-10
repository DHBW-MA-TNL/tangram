package frontend;

import javax.swing.*;
import java.awt.*;

public class DifficultyScalePanel extends JPanel {
    private Color startColor;
    private Color endColor;

    public DifficultyScalePanel(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
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

        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0, width, height, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE
        );

        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, width, height, 20, 20);  // Fülle das Button-Rechteck mit dem Farbverlauf
    }
}
