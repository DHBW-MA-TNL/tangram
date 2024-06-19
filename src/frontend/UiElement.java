package frontend;

import java.awt.*;

public class UiElement {
    private final Polygon shape;
    private final Color color;

    public UiElement(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(shape);
    }
}
