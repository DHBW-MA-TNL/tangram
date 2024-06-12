package backend;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Console;
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
    public boolean isSolved(){
        return solvedPos;
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
        this.shape.invalidate();
    }

    public void move(int dx, int dy) {
        shape.translate(dx, dy);
        this.shape.invalidate();
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
        for (int i = 0; i < this.shape.npoints-1; i++) {
            distance += Math.sqrt(Math.pow(this.shape.xpoints[i] - that.shape.xpoints[i], 2) + Math.pow(this.shape.ypoints[i] - that.shape.ypoints[i], 2));
        }
        System.out.println("distance = " + distance);
        return distance;
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
        this.setEdges();
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

    public int  sharedEdges(TangramShape that) {
        int count = 0;
        for (Line2D edge : this.getEdges()) {
            for (Line2D thatEdge : that.getEdges()) {
                if (edge.intersectsLine(thatEdge)) {
                    count++;
                }
            }
        }
        return count;
    }

    public int sameEdges(TangramShape that) {
        int count = 0;
        for (Line2D edge : this.getEdges()) {
            for (Line2D thatEdge : that.getEdges()) {
                if (edge.getP1().equals(thatEdge.getP1()) && edge.getP2().equals(thatEdge.getP2())) {
                    count++;
                }
            }
        }
        return count;
    }


    public List<Point> insidePoints() {
        // Get all points inside the shape
        List<Point> insidePoints = new ArrayList<>();
        for (int x = shape.getBounds().x; x < shape.getBounds().x + shape.getBounds().width; x++) {
            for (int y = shape.getBounds().y; y < shape.getBounds().y + shape.getBounds().height; y++) {
                if (shape.contains(x, y)) {
                    insidePoints.add(new Point(x, y));
                }
            }
        }
        return insidePoints;
    }

    public boolean collidesWith(TangramShape that) {
        for (int i = 0; i < this.shape.npoints; i++) {
            if (that.shape.contains(this.shape.xpoints[i], this.shape.ypoints[i])) {
                return true;
            }
        }
        return false;
    }

    public void setEdges() {
            this.edges = getEdges();
    }

        public int pointsOnEdges(TangramShape that) {
            int count = 0;
            setEdges();
            that.setEdges();
            for (Point point : this.points) {
                for (Line2D edge : that.edges) {
                    if (edge.ptLineDist(point) < 1) {
                        count++;
                    }
                }

            }
            return count;
        }

        public boolean crossesEdges(TangramShape that) {
        setEdges();
        int counter =0;
            for (Line2D edge : this.edges) {
                for (Line2D thatEdge : that.edges) {
                    if (edge.intersectsLine(thatEdge)) {
                        counter++;
                    }
                }
            }
            System.out.println(counter);
            if(counter == 2 || counter >=3){
                return  true;
            }else{
                return false;
            }

        }

        public boolean pointsOnOtherLines(TangramShape that){
            setEdges();
            int counter =0;
            setPoints();
            for (Point point : this.points){
                for (Line2D line: that.getEdges()){
                    System.out.println(line.ptLineDist(point));
                    if(line.ptLineDist(point) ==0.0){
                        System.out.println(line.toString());
                        counter++;
                    }
                }
            }
            System.out.println(counter);
            if(counter >=3){
                return  true;
            }else{
                return false;
            }
        }


    // Check if a TangramShape is outside the visible area
    public boolean isOutsideVisibleArea( int width, int height) {
        Rectangle visibleArea = new Rectangle(80, 80, width, height);
        System.out.println(visibleArea);
        return !visibleArea.contains(this.shape.getBounds());
    }






}
