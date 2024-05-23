import java.awt.*;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class PositionRandomizer {


    public static void positionAllPolygons(List<TangramShape> shapes, int maxX, int maxY) {
        Random random = new Random();
        for (TangramShape shape : shapes) {
            positionPolygon(shape, shapes, random, maxX, maxY);
        }
    }

    public static void positionPolygon(TangramShape shape, List<TangramShape> shapes, Random random, int maxX, int maxY) {
        List<Line2D> edges = new ArrayList<>();
        TangramShape firstShape = shapes.get(0);
        edges.addAll(getEdges(firstShape.shape));

        boolean placed = false;

        for (int attempts = 0; attempts < 1000; attempts++) {
            Line2D edge = edges.get(random.nextInt(edges.size()));
            List<Line2D> shapeEdges = getEdges(shape.shape);
            Line2D shapeEdge = shapeEdges.get(random.nextInt(shapeEdges.size()));

            double dx = edge.getX1() - shapeEdge.getX1();
            double dy = edge.getY1() - shapeEdge.getY1();

            Polygon translated = new Polygon(shape.shape.xpoints, shape.shape.ypoints, shape.shape.npoints);
            translated.translate((int) dx, (int) dy);

            if (Arrays.stream(translated.xpoints).anyMatch(x -> x < 0 || x > maxX) ||
                    Arrays.stream(translated.ypoints).anyMatch(y -> y < 0 || y > maxY)) {
                continue;
            }

            if (shapes.stream().noneMatch(s -> s.shape.intersects(translated.getBounds2D()))) {
                shape.shape = translated;
                edges.addAll(shapeEdges);
                placed = true;
                break;
            }
        }

        if (!placed) {
            System.out.println("Failed to place shape after 100 attempts");
        }
    }

    private static List<Line2D> getEdges(Polygon polygon) {
        List<Line2D> edges = new ArrayList<>();

        for (int i = 0; i < polygon.npoints; i++) {
            int nextIndex = (i + 1) % polygon.npoints;
            edges.add(new Line2D.Double(polygon.xpoints[i], polygon.ypoints[i], polygon.xpoints[nextIndex], polygon.ypoints[nextIndex]));
        }

        return edges;
    }
}