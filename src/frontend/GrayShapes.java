package frontend;

import backend.TangramShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GrayShapes is a class that generates the gray shapes for the Tangram game.
 * It contains a list of TangramShape objects, and provides methods to multiply a polygon and get the gray shapes.
 */
public class GrayShapes{
    /**
     * The list of TangramShape objects that make up the gray shapes.
     */
    public final List<TangramShape> grayShapes= new ArrayList<>();

    /**
     * Constructor for the GrayShapes class.
     * It initializes the gray shapes for the game.
     */
    public GrayShapes(){
        int multiplier = 2;
        Polygon D1 = multiplyPolygon(new Polygon(new int[]{0, 100, 50}, new int[]{0, 0, 50}, 3), multiplier);
        Polygon D2 = multiplyPolygon(new Polygon(new int[]{100, 50, 100}, new int[]{0, 50, 100}, 3), multiplier);
        Polygon D3 = multiplyPolygon(new Polygon(new int[]{0, 25, 0}, new int[]{0, 25, 50}, 3), multiplier);
        Polygon D4 = multiplyPolygon(new Polygon(new int[]{50, 25, 75}, new int[]{50, 75, 75}, 3), multiplier);
        Polygon D5 = multiplyPolygon(new Polygon(new int[]{0, 0, 50}, new int[]{50, 100, 100}, 3), multiplier);
        Polygon Q1 = multiplyPolygon(new Polygon(new int[]{25, 50, 25, 0}, new int[]{25, 50, 75, 50}, 4), multiplier);
        Polygon P1 = multiplyPolygon(new Polygon(new int[]{25, 75, 100, 50}, new int[]{75, 75, 100, 100}, 4), multiplier);

        grayShapes.add(new TangramShape(D1, Color.GRAY));
        grayShapes.add(new TangramShape(D2, Color.GRAY));
        grayShapes.add(new TangramShape(D3, Color.GRAY));
        grayShapes.add(new TangramShape(D4, Color.GRAY));
        grayShapes.add(new TangramShape(D5, Color.GRAY));
        grayShapes.add(new TangramShape(Q1, Color.GRAY));
        grayShapes.add(new TangramShape(P1, Color.GRAY));
    }

    /**
     * This method multiplies the coordinates of a polygon by a multiplier.
     * It returns a new Polygon object with the multiplied coordinates.
     *
     * @param polygon The Polygon object to be multiplied.
     * @param multiplier The multiplier to multiply the coordinates of the polygon.
     *
     * @return A new Polygon object with the multiplied coordinates.
     *
     * @see Polygon
     */
    public static Polygon multiplyPolygon(Polygon polygon, int multiplier) {
        int[] xpoints = new int[polygon.npoints];
        int[] ypoints = new int[polygon.npoints];

        for (int i = 0; i < polygon.npoints; i++) {
            xpoints[i] = polygon.xpoints[i] * multiplier;
            ypoints[i] = polygon.ypoints[i] * multiplier;
        }

        return new Polygon(xpoints, ypoints, polygon.npoints);
    }

    /**
     * This method returns the list of TangramShape objects that make up the gray shapes.
     *
     * @return The list of TangramShape objects that make up the gray shapes.
     *
     * @see TangramShape
     */
    public List<TangramShape> getGrayShapes(){
        return grayShapes;
    }
}