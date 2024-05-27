package frontend;

import javax.swing.*;
import java.awt.*;

public class DifficultyScalePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Erzeuge einen Farbverlauf in einem 45-Grad-Winkel
        int width = getWidth();
        int height = getHeight();

        // Startpunkt (unten links) und Endpunkt (oben rechts) des Farbverlaufs
        float[] fractions = {0.0f, 0.5f, 1.0f};
        Color[] colors = {Color.GREEN, Color.YELLOW, Color.RED};

        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0, width, height, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE
        );


        g2d.setPaint(gradient);
        g2d.fillRoundRect(10, getHeight() / 2 - 20, getWidth() - 20, 40, 20, 20);  // Zeichne ein abgerundetes Rechteck
    }
}
