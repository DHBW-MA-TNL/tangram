import java.awt.*;
import java.util.Vector;
import java.util.Arrays;

public class TangramShape {
    public Polygon shape;
    private Color color;

    public TangramShape(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;

    }

    public String toString() {
        return "TangramShape{" +
                "shape=" + shape +
                ", color=" + color +
                "coordinates=" + shape.getBounds() +
                '}';
    }



    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(shape);
    }

    public void shuffle() {

    }

    // TODO: Implement methods to rotate, flip, and drag shapes

    public void rotate(double angle) {
        Point center = new Point(shape.getBounds().x + shape.getBounds().width / 2, shape.getBounds().y + shape.getBounds().height / 2);
        for (int i = 0; i < shape.npoints; i++) {
            double x = shape.xpoints[i];
            double y = shape.ypoints[i];
            double newX = center.x + (x - center.x) * Math.cos(angle) - (y - center.y) * Math.sin(angle);
            double newY = center.y + (x - center.x) * Math.sin(angle) + (y - center.y) * Math.cos(angle);
            shape.xpoints[i] = (int) newX;
            shape.ypoints[i] = (int) newY;
        }

    }

    public boolean isCloseTo(TangramShape that) {
        if (this.shape.npoints== that.shape.npoints){
            for (int i = 0; i < this.shape.npoints; i++) {
                if (Math.abs(this.shape.xpoints[i] - that.shape.xpoints[i]) < 5 && Math.abs( this.shape.ypoints[i] - that.shape.ypoints[i]) < 5) {
                    return true;
                }
            }
        }
       return false;
    }



    public void move(Point p) {
        shape.translate(p.x, p.y);
    }

    public void flip() {
        
    }
}