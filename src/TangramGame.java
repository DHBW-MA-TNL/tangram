import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class TangramGame extends JFrame{
    public static List<TangramShape> shapes = new ArrayList<>();

    public  TangramGame(){
        Polygon a = new Polygon (new int[]{0, 100, 100 , 0}, new int[]{0, 0, 100 , 100}, 4);
        Polygon b = new Polygon(new int[]{0, 100, 300}, new int[]{101, 300, 150}, 3);

        //Polygon b = new Polygon(new int[]{100, 200, 300}, new int[]{150, 300, 150}, 3);
        List<Line2D> edgesA = getEdges(a);
        List<Line2D> edgesB = getEdges(b);

        for (int i = 0; i < edgesA.size(); i++) {
            for (int j = 0; j < edgesB.size(); j++) {
                if (edgesA.get(i).intersectsLine(edgesB.get(j))) {
                    System.out.println("Intersecting");
                }
            }
        }




        //shapes
        shapes.add(new TangramShape(a, Color.RED));
        shapes.add(new TangramShape(b, Color.RED));



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
}