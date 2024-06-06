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
        int rdmNPoint = random.nextInt(current.shape.npoints);

// Check if there are already used polygons
        if (!used.isEmpty()) {
            boolean overlaps = true;
            for (Point point : vertices) {

                current.alignNPointToPoint(rdmNPoint, point);
                for (TangramShape shape : used) {


                    for (int i=0; i<=8; i++) {
                        if (current.overlaps(shape) ||  current.vertexInCommon(shape) >1 || current.isInside(shape) || current.touches(shape) || current.centerPointInside(shape)){
                            System.out.println(current.color.toString() + " overlaps with " + shape.color.toString() );
                            current.rotateAroundPoint(point, 45);
                        }else {
                            System.out.println(current.color.toString()  +" " +current.vertexInCommon(shape)+"  with " + shape.color.toString() +" ");
                            overlaps = false;
                            break;
                        }
                    }
                    if (!overlaps) {
                        System.out.println("Does not overlap");
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

        used.add(unused.remove(0));
        return shufflePolygons(unused, used, next.x, next.y, vertices);
    }
}
