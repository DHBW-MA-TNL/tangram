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




        Point next = new Point(current.shape.xpoints[randomNPoint], current.shape.ypoints[randomNPoint]);

        boolean intersects = false;
        if (!used.isEmpty()) {
            for (Point vertex : vertices) {
                for (TangramShape shape : used) {
                    for (int i = 0; i < 8; i++) {
                        if (TangramGame.getEdges(shape.shape).stream().anyMatch(edge -> edge.intersectsLine(vertex.x, vertex.y, next.x, next.y))) {
                            for(int j = 0; j < current.shape.npoints; j++){
                                if(j!=randomNPoint){
                                    vertices.add(new Point(current.shape.xpoints[j], current.shape.ypoints[j]));
                                }
                            }
                            break;
                        } else {
                            current.rotateAroundPoint(start, 45);
                        }
                    }
                }
                System.out.println("aligning to point: " + vertex);
                current.alignNPointToPoint(randomNPoint, vertex);
            }

        }
        used.add(unused.remove(0));
        System.out.println("used size: " + used.size());
        return shufflePolygons(unused, used, next.x, next.y, vertices);
    }
}