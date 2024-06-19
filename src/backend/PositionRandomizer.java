package backend;

import java.awt.*;
import java.awt.geom.Area;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PositionRandomizer {
    private static final Random random = new Random();

    public static List<TangramShape> shufflePolygons(List<TangramShape> unused, List<TangramShape> used, int px, int py) {

        return shufflePolygons(unused, used, px, py, new ArrayList<>());
    }

    public static boolean polygonsIntersect(TangramShape current, List<TangramShape> used) {
        Area area1 = new Area(current.shape);
        for (TangramShape usedShape : used) {
            Area area2 = new Area(usedShape.shape);
            if (area1.intersects(area2.getBounds2D())) {
                return true; // Wenn sich die Bereiche überschneiden, gibt es eine Überlappung
            }
        }
        return false; // Keine Überlappung gefunden
    }

    public static List<TangramShape> shufflePolygons(List<TangramShape> unusedList, List<TangramShape> usedList, int px, int py, List<Point> vertices) {
        //Rekursion soll enden, wenn die Liste der bisher noch nicht platzierten Figuren leer ist
        if (unusedList.isEmpty()) {
            return usedList;
        }

        //Für mehr Variation soll die Liste der bisher noch nicht platzierten Elemente und die der verwendeten Punkte bei jedem Aufruf gemischt werden
        Collections.shuffle(unusedList);
        Collections.shuffle(vertices);

        //Setze Werte für die aktuelle Figur
        Point start = new Point(px, py);
        TangramShape current = unusedList.get(0);
        current.alignNPointToPoint(0, start);
        int rdmNPoint = random.nextInt(current.shape.npoints);

        //Im allerersten Durchlauf gibt es noch keine Punkte an die angesetzt werden kann
        if (!usedList.isEmpty()) {
            boolean overlaps = true;

            // Iteriere über alle Punkte und versuche dort anzulegen
            for (Point point : vertices) {
                // Platziere die aktuelle Figur mit einer zufälligen Ecke an diesen Punkt
                current.alignNPointToPoint(rdmNPoint, point);

                // Sollte die Figur an die aktuelle Stelle nicht passen, da sie sich mit anderen überlappen würde, so drehe sie so lange im Kreis, bis sie um 360° gedreht wurde
                for (int i = 0; i < 8; i++) { // 8 Versuche mit 45°
                    if (polygonsIntersect(current, usedList)) {
                        current.rotateAroundPoint(point, 45);
                    } else {
                        overlaps = false;
                        break;
                    }
                }

                if (!overlaps) {
                    break;
                }
            }
        }
        for (int i = 0; i < current.shape.npoints; i++) {
            vertices.add(new Point(current.shape.xpoints[i], current.shape.ypoints[i]));
        }

        Point next = vertices.get(random.nextInt(vertices.size()));

        usedList.add(current);
        unusedList.remove(current);

        return shufflePolygons(unusedList, usedList, next.x, next.y, vertices);
    }
}