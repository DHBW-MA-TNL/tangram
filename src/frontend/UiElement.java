package frontend;

import java.awt.*;

public class UiElement {
    private Polygon shape;
    private Color color;

    public UiElement(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(shape);
    }
}
