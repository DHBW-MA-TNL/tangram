import java.awt.*;
import java.util.List;
import java.util.Vector;

public class TangramShape {
    public Polygon shape;
    public Color color;
    public List<Point> points = new Vector<>();

    public TangramShape(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
        setPoints();

    }


    void setPoints() {
        points.clear();
        for (int i = 0; i < shape.npoints; i++) {
            points.add(new Point(shape.xpoints[i], shape.ypoints[i]));
        }
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
        int[] x = shape.xpoints;
        int[] y = shape.ypoints;
        Vector<Integer> xVector = new Vector<>();
        Vector<Integer> yVector = new Vector<>();
        for (int i = 0; i < x.length; i++) {
            xVector.add(x[i]);
            yVector.add(y[i]);
        }
        for (int i = 0; i < x.length; i++) {
            x[i] = xVector.get((i + 2) % x.length);
            y[i] = yVector.get((i + 2) % y.length);
        }


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
        if (this.shape.npoints == that.shape.npoints) {
            for (int i = 0; i < this.shape.npoints; i++) {
                if (Math.abs(this.distanceTo(that)) < 15) {
                    if (this.getSize() == that.getSize()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean samePosition(TangramShape that) {
        boolean same = true;
        for (int i = 0; i < this.shape.npoints - 1; i++) {
            if (this.shape.xpoints[i] != that.shape.xpoints[i] || this.shape.ypoints[i] != that.shape.ypoints[i]) {
                same = false;
            }
        }
        return same;
    }


    public void move(Point p) {
        shape.translate(p.x, p.y);
        setPoints();
    }

    public void move(int dx, int dy) {
        shape.translate(dx, dy);
    }

    public void alignToPoint(Point p) {
        //move shape that a corner point is at the given point
        Point corner = points.get(2);
        int dx = p.x - corner.x;
        int dy = p.y - corner.y;
        move(new Point(dx, dy));

    }

    public void flip() {

    }

    public int getSize() {
        return shape.getBounds().width * shape.getBounds().height;
    }

    public double distanceTo(TangramShape that) {
        double distance = 0;
        for (int i = 0; i < this.shape.npoints; i++) {
            distance += Math.sqrt(Math.pow(this.shape.xpoints[i] - that.shape.xpoints[i], 2) + Math.pow(this.shape.ypoints[i] - that.shape.ypoints[i], 2));
        }
        System.out.println("distance = " + distance);
        return distance;
    }

    public void rotateAroundPoint(Point point, double angle) {
        double rad = Math.toRadians(angle);
        int centerX = point.x;
        int centerY = point.y;
        for (int i = 0; i < shape.npoints; i++) {
            int x = shape.xpoints[i];
            int y = shape.ypoints[i];
            shape.xpoints[i] = (int) (centerX + (x - centerX) * Math.cos(rad) - (y - centerY) * Math.sin(rad));
            shape.ypoints[i] = (int) (centerY + (x - centerX) * Math.sin(rad) + (y - centerY) * Math.cos(rad));
        }
    }

    public boolean overlaps(TangramShape that) {
        int count = 0;
        for (int i = 0; i < this.shape.npoints; i++) {
            if (that.shape.contains(this.shape.xpoints[i], this.shape.ypoints[i])) {
                count++;
            }
        }
        return count > 1;
    }

    public void movePolygonToPoint(int npoint, Point targetPoint) {
        if (npoint < 0 || npoint >= this.shape.npoints) {
            throw new IllegalArgumentException("Invalid npoint");
        }

        int dx = targetPoint.x - this.shape.xpoints[npoint];
        int dy = targetPoint.y - this.shape.ypoints[npoint];

        this.shape.translate(dx, dy);
    }

    public void alignNPointToPoint(int n, Point point) {
        int dx = point.x - shape.xpoints[n];
        int dy = point.y - shape.ypoints[n];
        for (int i = 0; i < shape.npoints; i++) {
            shape.xpoints[i] += dx;
            shape.ypoints[i] += dy;
        }
    }

    public boolean onlyVertexInCommon(TangramShape that) {
        int count = 0;
        for (int i = 0; i < this.shape.npoints; i++) {
            for (int j = 0; j < that.shape.npoints; j++) {
                if (this.shape.xpoints[i] == that.shape.xpoints[j] && this.shape.ypoints[i] == that.shape.ypoints[j]) {
                    count++;
                }
            }
        }
        System.out.println("count = " + count);
        return count == 1;
    }

    public int vertexInCommon(TangramShape that) {
        for (int i = 0; i < this.shape.npoints; i++) {
            for (int j = 0; j < that.shape.npoints; j++) {
                if (this.shape.xpoints[i] == that.shape.xpoints[j] && this.shape.ypoints[i] == that.shape.ypoints[j]) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean isInside(TangramShape that) {
        for (int i = 0; i < this.shape.npoints; i++) {
            if (!that.shape.contains(this.shape.xpoints[i], this.shape.ypoints[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean touches(TangramShape that) {
        for (int i = 0; i < this.shape.npoints; i++) {
            for (int j = 0; j < that.shape.npoints; j++) {
                if (Math.abs(this.shape.xpoints[i] - that.shape.xpoints[j]) < 10 && Math.abs(this.shape.ypoints[i] - that.shape.ypoints[j]) < 10) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean centerPointInside(TangramShape that) {
        int x = this.shape.getBounds().x + this.shape.getBounds().width / 2;
        int y = this.shape.getBounds().y + this.shape.getBounds().height / 2;
        return that.shape.contains(x, y);
    }





}