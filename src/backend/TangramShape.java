package backend;

import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * TangramShape is a class that represents a shape in the Tangram game.
 * It contains a Polygon object that represents the shape, a Color object that represents the color of the shape, a list of points that make up the shape, a list of edges of the shape, a boolean that indicates whether the shape is in the solved position, a boolean that indicates whether the shape is movable, and a Polygon object that represents the original shape.
 * It provides methods to reset the shape, set the solved position, check whether the shape is not solved, get the edges of the shape, set the points of the shape, draw the shape, rotate the shape, check whether the shape is close to another shape, move the shape, rotate the shape around a point, align a point of the shape to another point, set the edges of the shape, check whether the shape is outside the visible area, apply gravity to the shape, and check whether the shape is solved.
 */
public class TangramShape {
    /**
     * The Polygon object that represents the shape.
     */
    public final Polygon shape;

    /**
     * The Color object that represents the color of the shape.
     */
    public final Color color;

    /**
     * The list of points that make up the shape.
     */
    private final List<Point> points = new Vector<>();
    /**
     * The Polygon object that represents the original shape.
     */
    private final Polygon originalShape;
    /**
     * A boolean that indicates whether the shape is movable.
     */
    public boolean isMoveable = true;
    /**
     * A boolean that indicates whether the shape is in the solved position.
     */
    private boolean solvedPos = false;

    /**
     * Constructor for the TangramShape class.
     * It initializes the shape, color, points, edges, and original shape.
     *
     * @param shape The Polygon object that represents the shape.
     * @param color The Color object that represents the color of the shape.
     */
    public TangramShape(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
        setPoints();
        //safe original shape
        originalShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

    }

    /**
     * This method resets the shape to its original shape.
     */
    public void resetShape() {
        for (int i = 0; i < originalShape.npoints; i++) {
            shape.xpoints[i] = originalShape.xpoints[i];
            shape.ypoints[i] = originalShape.ypoints[i];
        }
        setPoints();
        isMoveable = true;
        this.shape.invalidate();
    }

    /**
     * This method sets the solved position of the shape.
     *
     * @param b The boolean value to set the solved position to.
     */
    public void setSolvedPos(boolean b) {
        solvedPos = b;
    }

    /**
     * This method checks whether the shape is not in the solved position.
     *
     * @return A boolean that indicates whether the shape is not in the solved position.
     */
    public boolean isNotSolved() {
        return !solvedPos;
    }

    /**
     * This method sets the points of the shape.
     */
    public void setPoints() {
        points.clear();
        for (int i = 0; i < shape.npoints; i++) {
            points.add(new Point(shape.xpoints[i], shape.ypoints[i]));
        }
    }

    /**
     * This method returns a string representation of the shape.
     *
     * @return A string representation of the shape.
     */
    public String toString() {
        return "backend.TangramShape{" +
                "shape=" + shape +
                ", color=" + color +
                "coordinates=" + shape.getBounds() +
                '}';
    }

    /**
     * This method draws the shape.
     *
     * @param g The Graphics object to draw on.
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(shape);
    }

    /**
     * This method rotates the shape by a given angle.
     *
     * @param j The angle to rotate the shape by.
     */
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
        this.shape.invalidate();
    }

    /**
     * This method checks whether the shape is close to another shape.
     *
     * @param that The other TangramShape object to check against.
     * @return A boolean that indicates whether the shape is close to the other shape.
     */
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
            if (count >= this.shape.npoints) {
                close = true;
            }
        }
        return close;
    }

    /**
     * This method moves the shape by a given distance in the x and y directions.
     *
     * @param dx The distance to move the shape in the x direction.
     * @param dy The distance to move the shape in the y direction.
     */
    public void move(int dx, int dy) {
        shape.translate(dx, dy);
        this.shape.invalidate();
    }

    /**
     * This method rotates the shape around a point by a given angle.
     *
     * @param p     The point to rotate the shape around.
     * @param angle The angle to rotate the shape by.
     */
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
        this.shape.invalidate();
    }


    /**
     * This method aligns a point of the shape to another point.
     *
     * @param n The index of the point of the shape to align.
     * @param p The point to align to.
     */
    public void alignNPointToPoint(int n, Point p) {
        int dx = p.x - this.shape.xpoints[n];
        int dy = p.y - this.shape.ypoints[n];

        for (int i = 0; i < this.shape.npoints; i++) {
            this.shape.xpoints[i] += dx;
            this.shape.ypoints[i] += dy;
        }
        this.shape.invalidate();
    }


    /**
     * This method checks whether the shape is outside the visible area.
     *
     * @param width  The width of the visible area.
     * @param height The height of the visible area.
     * @return A boolean that indicates whether the shape is outside the visible area.
     */
    // Check if a TangramShape is outside the visible area
    public boolean isOutsideVisibleArea(int width, int height) {
        Rectangle visibleArea = new Rectangle(80, 80, width, height);
        System.out.println(visibleArea);
        return !visibleArea.contains(this.shape.getBounds());
    }


    /**
     * This method applies gravity to the shape.
     *
     * @param that The other TangramShape object to apply gravity with.
     */
    public void gravity(TangramShape that) {
        if (this.isCloseTo(that)) {
            System.out.println("Close to shape2");
            this.shape.xpoints = that.shape.xpoints;
            this.shape.ypoints = that.shape.ypoints;
            that.setPoints();
            this.setPoints();
            that.setSolvedPos(true);
            this.setSolvedPos(true);
            this.isMoveable = false;
            that.isMoveable = false;
        }

    }

    /**
     * This method checks whether the shape is in the solved position.
     *
     * @return A boolean that indicates whether the shape is in the solved position.
     */
    public boolean isSolved() {
        return solvedPos;
    }
}
