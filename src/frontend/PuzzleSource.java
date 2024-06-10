package frontend;

import backend.TangramShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PuzzleSource{
    int vMover = 100;
     int xMover = 700;
    public List<TangramShape> puzzleSrc= new ArrayList<>();
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

    public  Polygon multiplyPolygon(Polygon polygon, int multiplier) {
        int[] xpoints = new int[polygon.npoints];
        int[] ypoints = new int[polygon.npoints];

        for (int i = 0; i < polygon.npoints; i++) {
            xpoints[i] = polygon.xpoints[i] * multiplier+xMover;
            ypoints[i] = polygon.ypoints[i] * multiplier+vMover;
        }

        return new Polygon(xpoints, ypoints, polygon.npoints);
    }


    public List<TangramShape> getPuzzleShapes(){
        return puzzleSrc;
    }
}