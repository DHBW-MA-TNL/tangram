package frontend;

import backend.TangramShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PuzzleSource is a class that generates the shapes for the Tangram puzzle.
 * It contains a list of TangramShape objects, and provides methods to multiply a polygon and get the puzzle shapes.
 */
public class PuzzleSource{
    /**
     * The vertical movement of the shapes.
     */
    final int vMover = 300;

    /**
     * The horizontal movement of the shapes.
     */
    final int xMover = 1090;

    /**
     * The list of TangramShape objects that make up the puzzle.
     */
    public final List<TangramShape> puzzleSrc= new ArrayList<>();

    /**
     * Constructor for the PuzzleSource class.
     * It initializes the shapes for the puzzle.
     */
    public PuzzleSource(){

        int multiplier= 2;
        Polygon SD1 =multiplyPolygon(new Polygon(new int[]{0, 100, 50}, new int[]{0, 0, 50}, 3), multiplier);
        Polygon SD2 =multiplyPolygon(new Polygon(new int[]{100, 50, 100}, new int[]{0, 50, 100}, 3), multiplier);
        Polygon SD3 =multiplyPolygon(new Polygon(new int[]{0, 25, 0}, new int[]{0, 25, 50}, 3), multiplier);
        Polygon SD4 =multiplyPolygon(new Polygon(new int[]{50, 25, 75}, new int[]{50, 75, 75}, 3), multiplier);
        Polygon SD5 =multiplyPolygon(new Polygon(new int[]{0, 0, 50}, new int[]{50, 100, 100}, 3), multiplier);
        Polygon SQ1 =multiplyPolygon(new Polygon(new int[]{25, 50, 25, 0}, new int[]{25, 50, 75, 50}, 4), multiplier);
        Polygon SP1 =multiplyPolygon(new Polygon(new int[]{25, 75, 100, 50}, new int[]{75, 75, 100, 100}, 4), multiplier);

        puzzleSrc.add(new TangramShape(SD1, Color.BLUE));
        puzzleSrc.add(new TangramShape(SD2, Color.RED));
        puzzleSrc.add(new TangramShape(SD3, Color.GREEN));
        puzzleSrc.add(new TangramShape(SD4, Color.MAGENTA));
        puzzleSrc.add(new TangramShape(SD5, Color.ORANGE));
        puzzleSrc.add(new TangramShape(SQ1, Color.YELLOW));
        puzzleSrc.add(new TangramShape(SP1, Color.CYAN));
    }

    /**
     * This method multiplies the coordinates of a polygon by a multiplier, and moves the polygon by a specified amount in the x and y directions.
     * It returns a new Polygon object with the multiplied and moved coordinates.
     *
     * @param polygon The Polygon object to be multiplied and moved.
     * @param multiplier The multiplier to multiply the coordinates of the polygon.
     *
     * @return A new Polygon object with the multiplied and moved coordinates.
     *
     * @see Polygon
     */
    public  Polygon multiplyPolygon(Polygon polygon, int multiplier) {
        int[] xpoints = new int[polygon.npoints];
        int[] ypoints = new int[polygon.npoints];

        for (int i = 0; i < polygon.npoints; i++) {
            xpoints[i] = polygon.xpoints[i] * multiplier+xMover;
            ypoints[i] = polygon.ypoints[i] * multiplier+vMover;
        }

        return new Polygon(xpoints, ypoints, polygon.npoints);
    }

    /**
     * This method returns the list of TangramShape objects that make up the puzzle.
     *
     * @return The list of TangramShape objects that make up the puzzle.
     *
     * @see TangramShape
     */
    public List<TangramShape> getPuzzleShapes(){
        return puzzleSrc;
    }
}