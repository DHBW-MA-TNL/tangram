import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PositionRandomizer {
    private static final Random random = new Random();

    private static List<Point> getVertices(Polygon polygon) {
        List<Point> vertices = new ArrayList<>();

        for (int i = 0; i < polygon.npoints; i++) {
            vertices.add(new Point(polygon.xpoints[i], polygon.ypoints[i]));
        }

        return vertices;
    }

    public static List<TangramShape> shufflePolygons(List<TangramShape> unused, List<TangramShape> used, int px, int py) {
        return shufflePolygons(unused, used, px, py, new ArrayList<>());
    }

    public static List<TangramShape> shufflePolygons(List<TangramShape> unused, List<TangramShape> used, int px, int py, List<Point> vertices) {
        if (unused.isEmpty()) {
            return used;
        }
        Point start = new Point(px, py);
        TangramShape current = unused.get(0);
        current.alignNPointToPoint(0, start);
        int randomNPoint = random.nextInt(current.shape.npoints);

        System.out.println("In der Methode");


        Point next = new Point(current.shape.xpoints[randomNPoint], current.shape.ypoints[randomNPoint]);

        List<Point> newVertices = new ArrayList<>();
        List<Shape> newUsedList = new ArrayList<>();
        if (vertices.size() == 0) {
            for (int j = 0; j < current.shape.npoints; j++) {
                if (j != randomNPoint) {
                    vertices.add(new Point(current.shape.xpoints[j], current.shape.ypoints[j]));
                }
            }
            newVertices = vertices;
        } else {


            boolean intersects = false;
            if (!used.isEmpty()) {
                System.out.println("In der If");
                System.out.println("---------------" + vertices.size());
                for (Point vertex : vertices) {
                    System.out.println("In der For-1");
                    for (TangramShape shape : used) {
                        System.out.println("In der For-2");
                        for (int i = 0; i < 8; i++) {
                            System.out.println("In der For-3");
                            if (TangramGame.getEdges(shape.shape).stream().anyMatch(edge -> edge.intersectsLine(vertex.x, vertex.y, next.x, next.y))) {
                                System.out.println("----------" + i + " mal gedreht----------------");
                                for (int j = 0; j < current.shape.npoints; j++) {
                                    if (j != randomNPoint) {
                                        newVertices.add(new Point(current.shape.xpoints[j], current.shape.ypoints[j]));
                                    }
                                }
                                System.out.println("Ja");
                                break;
                            } else {
                                current.rotateAroundPoint(start, 45);
                                System.out.println(i + " mal rotiert");
                            }
                        }
                    }
                    System.out.println("aligning to point: " + vertex);
                    current.alignNPointToPoint(randomNPoint, vertex);
                }

            }
        }
        used.add(unused.remove(0));
        System.out.println("used size: " + used.size());
        return shufflePolygons(unused, used, next.x, next.y, newVertices);
    }
}