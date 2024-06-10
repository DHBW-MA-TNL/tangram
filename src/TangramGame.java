import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class TangramGame extends JFrame{
    public static List<TangramShape> shapes = new ArrayList<>();
    public static List<TangramShape> shapes2 = new ArrayList<>();
    public static List<UiElement> uiElements = new ArrayList<>();
    public static List<TangramShape> tmp = new ArrayList<>();
    public static Random random = new Random();

    public  TangramGame(){
        setTitle("Tangram Game");
        setSize(1000, 1000);
        setMinimumSize(new Dimension(800, 800));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        defaultShapes();
        setUiElements();
        //TangramShape
        List<TangramShape> rndPos = PositionRandomizer.shufflePolygons(shapes, new ArrayList<>(), 300, 500);





        add(new TangramPanel(rndPos, uiElements.toArray(new UiElement[0]), shapes2.toArray(new TangramShape[0])));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                uiElements.clear();
                setUiElements();

                repaint();
            }
        }
        );
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new TangramGame();
            game.setVisible(true);
        });
    }

    public static List<Line2D> getEdges(Polygon polygon) {
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

        Polygon SD1 = multiplyPolygon(new Polygon(new int[]{0, 100, 50}, new int[]{0, 0, 50}, 3), multiplier);
        Polygon SD2 = multiplyPolygon(new Polygon(new int[]{100, 50, 100}, new int[]{0, 50, 100}, 3), multiplier);
/*        Polygon SD3 = multiplyPolygon(new Polygon(new int[]{0, 25, 0}, new int[]{0, 25, 50}, 3), multiplier);
        Polygon SD4 = multiplyPolygon(new Polygon(new int[]{50, 25, 75}, new int[]{50, 75, 75}, 3), multiplier);
        Polygon SD5 = multiplyPolygon(new Polygon(new int[]{0, 0, 50}, new int[]{50, 100, 100}, 3), multiplier);
        Polygon SQ1 = multiplyPolygon(new Polygon(new int[]{25, 50, 25, 0}, new int[]{25, 50, 75, 50}, 4), multiplier);
        Polygon SP1 = multiplyPolygon(new Polygon(new int[]{25, 75, 100, 50}, new int[]{75, 75, 100, 100}, 4), multiplier);*/
        //Polygon P2 = multiplyPolygon(new Polygon(new int[]{25, 75, 100, 50}, new int[]{75, 75, 100, 100}, 4), multiplier);


        //shapes
/*        shapes.add(new TangramShape(D1, Color.BLUE));
        shapes.add(new TangramShape(D2, Color.RED));
         shapes.add(new TangramShape(D3, Color.GREEN));
        shapes.add(new TangramShape(D4, Color.MAGENTA));
      shapes.add(new TangramShape(D5, Color.ORANGE));
       shapes.add(new TangramShape(Q1, Color.YELLOW));
        shapes.add(new TangramShape(P1, Color.CYAN));*/

        shapes.add(new TangramShape(D1, Color.GRAY));
        shapes.add(new TangramShape(D2, Color.GRAY));
        shapes.add(new TangramShape(D3, Color.GRAY));
        shapes.add(new TangramShape(D4, Color.GRAY));
        shapes.add(new TangramShape(D5, Color.GRAY));
        shapes.add(new TangramShape(Q1, Color.GRAY));
        shapes.add(new TangramShape(P1, Color.GRAY));
    /*

        //shapes2
        shapes2.add(new TangramShape(SD1, Color.GRAY));
        shapes2.add(new TangramShape(SD2, Color.GRAY));
     shapes2.add(new TangramShape(SD3, Color.GRAY));
        shapes2.add(new TangramShape(SD4, Color.GRAY));
        shapes2.add(new TangramShape(SD5, Color.GRAY));
        shapes2.add(new TangramShape(SQ1, Color.GRAY));
        shapes2.add(new TangramShape(SP1, Color.GRAY));*/

        //move shapes 2 aside
        for (TangramShape shape : shapes2) {
            shape.move(new Point(0, 500));
        }




    }

    public void setUiElements(){
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int subWindowWidth = windowWidth / 4;
        int subWindowHeight = windowHeight / 8;
        Polygon sidebarRight = new Polygon(new int[]{windowWidth - subWindowWidth, windowWidth, windowWidth, windowWidth - subWindowWidth}, new int[]{0, 0, windowHeight, windowHeight}, 4);
        uiElements.add(new UiElement(sidebarRight, Color.lightGray));
    }

    public Polygon generateRegularPolygon(int numVertices, int radius, int centerX, int centerY) {
        int[] xpoints = new int[numVertices];
        int[] ypoints = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            double angle = 2 * Math.PI * i / numVertices;
            xpoints[i] = centerX + (int) (radius * Math.cos(angle));
            ypoints[i] = centerY + (int) (radius * Math.sin(angle));
        }

        return new Polygon(xpoints, ypoints, numVertices);
    }

    public List<Polygon> breakIntoTriangles(Polygon polygon) {
        List<Polygon> triangles = new ArrayList<>();

        for (int i = 0; i < polygon.npoints; i++) {
            int nextIndex = (i + 1) % polygon.npoints;
            int prevIndex = (i - 1 + polygon.npoints) % polygon.npoints;

            for (int j = 0; j < polygon.npoints; j++) {
                if (j != i && j != nextIndex && j != prevIndex) {
                    triangles.add(new Polygon(
                            new int[]{polygon.xpoints[i], polygon.xpoints[nextIndex], polygon.xpoints[j]},
                            new int[]{polygon.ypoints[i], polygon.ypoints[nextIndex], polygon.ypoints[j]},
                            3));
                }
            }
        }

        return triangles;
    }

    public List<Polygon> breakIntoQuadrilaterals(Polygon polygon) {
        List<Polygon> quadrilaterals = new ArrayList<>();

        for (int i = 0; i < polygon.npoints; i++) {
            int nextIndex = (i + 1) % polygon.npoints;
            int prevIndex = (i - 1 + polygon.npoints) % polygon.npoints;

            for (int j = 0; j < polygon.npoints; j++) {
                if (j != i && j != nextIndex && j != prevIndex) {
                    for (int k = 0; k < polygon.npoints; k++) {
                        if (k != i && k != j && k != nextIndex && k != prevIndex) {
                            for (int l = 0; l < polygon.npoints; l++) {
                                if (l != i && l != j && l != k && l != nextIndex && l != prevIndex) {
                                    quadrilaterals.add(new Polygon(
                                            new int[]{polygon.xpoints[i], polygon.xpoints[nextIndex], polygon.xpoints[j], polygon.xpoints[k], polygon.xpoints[l]},
                                            new int[]{polygon.ypoints[i], polygon.ypoints[nextIndex], polygon.ypoints[j], polygon.ypoints[k], polygon.ypoints[l]},
                                            5));
                                }
                            }
                        }
                    }
                }
            }
        }

        return quadrilaterals;
    }

    public List<Point> crossPoints(Polygon polygon1, Line2D line) {
        List<Point> crossPoints = new ArrayList<>();
        List<Line2D> edges = getEdges(polygon1);
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).intersectsLine(line)) {
                crossPoints.add(new Point((int) edges.get(i).getX1(), (int) edges.get(i).getY1()));
                crossPoints.add(new Point((int) edges.get(i).getX2(), (int) edges.get(i).getY2()));
            }
        }
        return crossPoints;
    }

    public  List<Point> polygonPointsAboveLine(Polygon polygon, Line2D line) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < polygon.npoints; i++) {
            if (line.relativeCCW(polygon.xpoints[i], polygon.ypoints[i]) == 1) {
                points.add(new Point(polygon.xpoints[i], polygon.ypoints[i]));
            }
        }
        return points;
    }

    public List<Point> polygonPointsBelowLine(Polygon polygon, Line2D line) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < polygon.npoints; i++) {
            if (line.relativeCCW(polygon.xpoints[i], polygon.ypoints[i]) == -1) {
                points.add(new Point(polygon.xpoints[i], polygon.ypoints[i]));
            }
        }
        return points;
    }

    public static void isSolved() {
        boolean solved = false;
        for (TangramShape shape : shapes) {
            for (TangramShape shape2 : shapes2) {
                if (shape.samePosition(shape2)) {
                    solved = true;
                } else {
                    solved = false;
                    break;
                }
            }
        }
        if (solved) {
            System.out.println("Solved");
        }
    }

    }

