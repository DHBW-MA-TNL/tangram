package backend;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TangramShape {
    public Polygon shape;
    public Color color;
    public List<Point> points = new Vector<>();
    public List<Line2D> edges = new ArrayList<>();
    private boolean solvedPos=false;
    public boolean isMoveable = true;
    public Polygon originalShape;

    public TangramShape(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
        setPoints();
        this.edges = getEdges();
        //safe original shape
        originalShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

    }

    public void resetShape(){
        for (int i = 0; i < originalShape.npoints; i++) {
            shape.xpoints[i] = originalShape.xpoints[i];
            shape.ypoints[i] = originalShape.ypoints[i];
        }
        setPoints();
        setEdges();
        isMoveable = true;
        this.shape.invalidate();
    }

    public void setSolvedPos(boolean b){
        solvedPos=b;
    }

    public boolean isNotSolved(){
        return !solvedPos;
    }

    public List<Line2D> getEdges() {
        List<Line2D> edges = new ArrayList<>();

        for (int i = 0; i < this.shape.npoints; i++) {
            int nextIndex = (i + 1) % this.shape.npoints; // Get the next index (loop back to 0 if at the end)
            edges.add(new Line2D.Double(this.shape.xpoints[i], this.shape.ypoints[i], this.shape.xpoints[nextIndex], this.shape.ypoints[nextIndex]));
        }

        return edges;
    }


    public void setPoints() {
        points.clear();
        for (int i = 0; i < shape.npoints; i++) {
            points.add(new Point(shape.xpoints[i], shape.ypoints[i]));
        }
    }

    public String toString() {
        return "backend.TangramShape{" +
                "shape=" + shape +
                ", color=" + color +
                "coordinates=" + shape.getBounds() +
                '}';
    }


    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(shape);
    }

    public void rotate(int j) {
        double angle = Math.toRadians(j);
        Point center = new Point(shape.getBounds().x + shape.getBounds().width / 2, shape.getBounds().y + shape.getBounds().height / 2);
        for (int i = 0; i < shape.npoints; i++) {
            double x = shape.xpoints[i];
            double y = shape.ypoints[i];
            double newX = center.x + (x - center.x) * Math.cos(angle) - (y - center.y) * Math.sin(angle);
            double newY = center.y + (x - center.x) * Math.sin(angle) + (y - center.y) * Math.cos(angle);
            shape.xpoints[i] = (int) newX;
            shape.ypoints[i] = (int) newY;
        }
        setPoints();
        setEdges();
        this.shape.invalidate();
    }

    public boolean isCloseTo(TangramShape that) {
        boolean close = false;
        int count = 0;
        if (this.shape.npoints == that.shape.npoints) {
            this.setPoints();
            that.setPoints();
            for (Point point : this.points) {

                for (Point thatPoint : that.points) {
                    if (Math.abs(point.x - thatPoint.x) < 10 && Math.abs(point.y - thatPoint.y) < 10) {
                        count++;
                    }
                }
            }
            System.out.println("count = " + count);
            if(count >= this.shape.npoints){
                close = true;
            }
        }
        return close;
    }

    public void move(int dx, int dy) {
        shape.translate(dx, dy);
        this.shape.invalidate();
    }

    public void rotateAroundPoint(Point p, double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        for (int i = 0; i < this.shape.npoints; i++) {
            int dx = this.shape.xpoints[i] - p.x;
            int dy = this.shape.ypoints[i] - p.y;

            this.shape.xpoints[i] = p.x + (int) Math.round(dx * cos - dy * sin);
            this.shape.ypoints[i] = p.y + (int) Math.round(dx * sin + dy * cos);
        }
        setEdges();
        this.shape.invalidate();
    }


    public void alignNPointToPoint(int n, Point p) {
        int dx = p.x - this.shape.xpoints[n];
        int dy = p.y - this.shape.ypoints[n];

        for (int i = 0; i < this.shape.npoints; i++) {
            this.shape.xpoints[i] += dx;
            this.shape.ypoints[i] += dy;
        }
        setEdges();
        this.shape.invalidate();
    }


    public void setEdges() {
            this.edges = getEdges();
    }

    // Check if a TangramShape is outside the visible area
    public boolean isOutsideVisibleArea( int width, int height) {
        Rectangle visibleArea = new Rectangle(80, 80, width, height);
        System.out.println(visibleArea);
        return !visibleArea.contains(this.shape.getBounds());
    }

    public void gravity(TangramShape that){
        if (this.isCloseTo(that)) {
            System.out.println("Close to shape2");
            this.shape.xpoints = that.shape.xpoints;
            this.shape.ypoints = that.shape.ypoints;
            that.setEdges();
            this.setEdges();
            that.setPoints();
            this.setPoints();
            that.setSolvedPos(true);
            this.setSolvedPos(true);
            this.isMoveable = false;
            that.isMoveable = false;
        }

    }






}
