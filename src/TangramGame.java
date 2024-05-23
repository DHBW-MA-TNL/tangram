import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TangramGame extends JFrame{
    public static List<TangramShape> shapes = new ArrayList<>();

    public  TangramGame(){

        defaultShapes();
        PositionRandomizer.positionAllPolygons(shapes, 800, 800);

        add(new TangramPanel(shapes.toArray(new TangramShape[0])));
        setTitle("Tangram Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new TangramGame();
            game.setVisible(true);
        });
    }

    public static java.util.List<Line2D> getEdges(Polygon polygon) {
        List<Line2D> edges = new ArrayList<>();

        for (int i = 0; i < polygon.npoints; i++) {
            int nextIndex = (i + 1) % polygon.npoints; // Get the next index (loop back to 0 if at the end)
            edges.add(new Line2D.Double(polygon.xpoints[i], polygon.ypoints[i], polygon.xpoints[nextIndex], polygon.ypoints[nextIndex]));
        }

        return edges;
    }

    public static void touches(){

        List<Line2D> edgesA = getEdges(shapes.get(0).shape);
        List<Line2D> edgesB = getEdges(shapes.get(1).shape);

        for (int i = 0; i < edgesA.size(); i++) {
            for (int j = 0; j < edgesB.size(); j++) {
                if (edgesA.get(i).intersectsLine(edgesB.get(j))) {
                    System.out.println("Intersecting");
                }
            }
        }
    }

    public Polygon multiplyPolygon(Polygon polygon, int multiplier) {
        int[] xpoints = new int[polygon.npoints];
        int[] ypoints = new int[polygon.npoints];

        for (int i = 0; i < polygon.npoints; i++) {
            xpoints[i] = polygon.xpoints[i] * multiplier;
            ypoints[i] = polygon.ypoints[i] * multiplier;
        }

        return new Polygon(xpoints, ypoints, polygon.npoints);
    }

    public void defaultShapes(){
        int multiplier = 2; // Change this to your desired multiplier
        Polygon D1 = multiplyPolygon(new Polygon(new int[]{0, 100, 50}, new int[]{0, 0, 50}, 3), multiplier);
        Polygon D2 = multiplyPolygon(new Polygon(new int[]{100, 50, 100}, new int[]{0, 50, 100}, 3), multiplier);
        Polygon D3 = multiplyPolygon(new Polygon(new int[]{0, 25, 0}, new int[]{0, 25, 50}, 3), multiplier);
        Polygon D4 = multiplyPolygon(new Polygon(new int[]{50, 25, 75}, new int[]{50, 75, 75}, 3), multiplier);
        Polygon D5 = multiplyPolygon(new Polygon(new int[]{0, 0, 50}, new int[]{50, 100, 100}, 3), multiplier);
        Polygon Q1 = multiplyPolygon(new Polygon(new int[]{25, 50, 25, 0}, new int[]{25, 50, 75, 50}, 4), multiplier);
        Polygon P1 = multiplyPolygon(new Polygon(new int[]{25, 75, 100, 50}, new int[]{75, 75, 100, 100}, 4), multiplier);

        //shapes
        shapes.add(new TangramShape(D1, Color.GRAY));
        shapes.add(new TangramShape(D2, Color.GRAY));
        shapes.add(new TangramShape(D3, Color.GRAY));
        shapes.add(new TangramShape(D4, Color.GRAY));
        shapes.add(new TangramShape(D5, Color.GRAY));
        shapes.add(new TangramShape(Q1, Color.GRAY));
        shapes.add(new TangramShape(P1, Color.GRAY));

    }
}