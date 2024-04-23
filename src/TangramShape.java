import java.awt.*;

public class TangramShape {
    public Polygon shape;
    private Color color;

    public TangramShape(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
    }



    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(shape);
    }

    public void shuffle() {

    }

    // TODO: Implement methods to rotate, flip, and drag shapes

    public void rotate(double angle) {

    }

    public void move(Point p) {
        shape.translate(p.x, p.y);
    }
}