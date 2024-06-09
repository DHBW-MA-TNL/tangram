import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TangramGame extends JFrame {
    public static List<TangramShape> shapes = new ArrayList<>();
    public static List<TangramShape> shapes2 = new ArrayList<>();
    public static List<UiElement> uiElements = new ArrayList<>();
    public static List<TangramShape> coloredShapes = new ArrayList<>();
    public static List<TangramShape> tmp = new ArrayList<>();
    public static Random random = new Random();

    public TangramGame() {
        setTitle("Tangram Game");
        setSize(1000, 1000);
        setMinimumSize(new Dimension(800, 800));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUiElements();
        defaultShapes();

        //TangramShape
        List<TangramShape> rndPos = PositionRandomizer.shufflePolygons(shapes, new ArrayList<>(), 300, 300);
        //List<TangramShape> rndPos =shapes;


        add(new TangramPanel(coloredShapes, uiElements.toArray(new UiElement[0]), rndPos));

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


    public static Polygon multiplyPolygon(Polygon polygon, int multiplier) {
        int[] xpoints = new int[polygon.npoints];
        int[] ypoints = new int[polygon.npoints];

        for (int i = 0; i < polygon.npoints; i++) {
            xpoints[i] = polygon.xpoints[i] * multiplier;
            ypoints[i] = polygon.ypoints[i] * multiplier;
        }

        return new Polygon(xpoints, ypoints, polygon.npoints);
    }

    public static void defaultShapes() {
        int multiplier = 2; // Change this to your desired multiplier

        //TEST SHAPES
        Polygon TDL = multiplyPolygon(new Polygon(new int[]{200, 300, 400}, new int[]{200, 100, 200}, 3), 1);
        Polygon TQL = multiplyPolygon(new Polygon(new int[]{250, 300, 350, 300}, new int[]{150, 100, 150, 200}, 4), 1);
        //
       // Polygon D1 = multiplyPolygon(new Polygon(new int[]{100, 200, 150}, new int[]{100, 100, 150}, 3), 1);

        Polygon D1 = multiplyPolygon(new Polygon(new int[]{0, 100, 50}, new int[]{0, 0, 50}, 3), multiplier);
        Polygon D2 = multiplyPolygon(new Polygon(new int[]{100, 50, 100}, new int[]{0, 50, 100}, 3), multiplier);
        Polygon D3 = multiplyPolygon(new Polygon(new int[]{0, 25, 0}, new int[]{0, 25, 50}, 3), multiplier);
        Polygon D4 = multiplyPolygon(new Polygon(new int[]{50, 25, 75}, new int[]{50, 75, 75}, 3), multiplier);
        Polygon D5 = multiplyPolygon(new Polygon(new int[]{0, 0, 50}, new int[]{50, 100, 100}, 3), multiplier);
        Polygon Q1 = multiplyPolygon(new Polygon(new int[]{25, 50, 25, 0}, new int[]{25, 50, 75, 50}, 4), multiplier);
        Polygon P1 = multiplyPolygon(new Polygon(new int[]{25, 75, 100, 50}, new int[]{75, 75, 100, 100}, 4), multiplier);


        //Polygon SD1 = multiplyPolygon(new Polygon(new int[]{0, 100, 50}, new int[]{0, 0, 50}, 3), multiplier);
        //Polygon SD2 = multiplyPolygon(new Polygon(new int[]{100, 50, 100}, new int[]{0, 50, 100}, 3), multiplier);
/*        Polygon SD3 = multiplyPolygon(new Polygon(new int[]{0, 25, 0}, new int[]{0, 25, 50}, 3), multiplier);
        Polygon SD4 = multiplyPolygon(new Polygon(new int[]{50, 25, 75}, new int[]{50, 75, 75}, 3), multiplier);
        Polygon SD5 = multiplyPolygon(new Polygon(new int[]{0, 0, 50}, new int[]{50, 100, 100}, 3), multiplier);
        Polygon SQ1 = multiplyPolygon(new Polygon(new int[]{25, 50, 25, 0}, new int[]{25, 50, 75, 50}, 4), multiplier);
        Polygon SP1 = multiplyPolygon(new Polygon(new int[]{25, 75, 100, 50}, new int[]{75, 75, 100, 100}, 4), multiplier);*/
        //Polygon P2 = multiplyPolygon(new Polygon(new int[]{25, 75, 100, 50}, new int[]{75, 75, 100, 100}, 4), multiplier);

        //  shapes.add(new TangramShape(D0,Color.BLUE));
        //shapes.add(new TangramShape(D1,Color.RED));


        //Solver Shapes


        int vMover = 100;
        Polygon SD1 = new Polygon(new int[]{780, 880, 980}, new int[]{250 + vMover, 350 + vMover, 250 + vMover}, 3);
        Polygon SD2 = new Polygon(new int[]{920, 820, 920}, new int[]{30 + vMover, 120 + vMover, 220 + vMover}, 3);
        Polygon SD3 = new Polygon(new int[]{860, 910, 860}, new int[]{630 + vMover, 680 + vMover, 730 + vMover}, 3);
        Polygon SD4 = new Polygon(new int[]{920, 970, 970}, new int[]{380 + vMover, 430 + vMover, 330 + vMover}, 3);
        Polygon SD5 = new Polygon(new int[]{780, 780, 880}, new int[]{480 + vMover, 580 + vMover, 580 + vMover}, 3);
        Polygon SQ1 = new Polygon(new int[]{920, 970, 920, 870}, new int[]{480 + vMover, 530 + vMover, 580 + vMover, 530 + vMover}, 4);
        Polygon SP1 = new Polygon(new int[]{770, 870, 920, 820}, new int[]{380 + vMover, 380 + vMover, 430 + vMover, 430 + vMover}, 4);


        coloredShapes.add(new TangramShape(SD1, Color.BLUE));
        coloredShapes.add(new TangramShape(SD2, Color.RED));
        coloredShapes.add(new TangramShape(SD3, Color.GREEN));
        coloredShapes.add(new TangramShape(SD4, Color.MAGENTA));
        coloredShapes.add(new TangramShape(SD5, Color.ORANGE));
        coloredShapes.add(new TangramShape(SQ1, Color.YELLOW));
        coloredShapes.add(new TangramShape(SP1, Color.CYAN));


        shapes.add(new TangramShape(D1, Color.GRAY));
        shapes.add(new TangramShape(D2, Color.GRAY));
        shapes.add(new TangramShape(D3, Color.GRAY));
        shapes.add(new TangramShape(D4, Color.GRAY));
        shapes.add(new TangramShape(D5, Color.GRAY));
        shapes.add(new TangramShape(Q1, Color.GRAY));
        shapes.add(new TangramShape(P1, Color.GRAY));


        //shapes2
  /*      shapes2.add(new TangramShape(SD1, Color.GRAY));
        shapes2.add(new TangramShape(SD2, Color.GRAY));
     shapes2.add(new TangramShape(SD3, Color.GRAY));
        shapes2.add(new TangramShape(SD4, Color.GRAY));
        shapes2.add(new TangramShape(SD5, Color.GRAY));
        shapes2.add(new TangramShape(SQ1, Color.GRAY));
        shapes2.add(new TangramShape(SP1, Color.GRAY));
        */


        //move shapes 2 aside
        for (TangramShape shape : shapes2) {
            shape.move(new Point(0, 500));
        }


    }

    public static void isSolved(List<TangramShape> that) {
        boolean solved = false;
        for (TangramShape shape : that) {
            for (TangramShape shape2 : coloredShapes) {
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

    public void setUiElements() {
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int subWindowWidth = windowWidth / 4;
        int subWindowHeight = windowHeight / 8;
        Polygon sidebarRight = new Polygon(new int[]{windowWidth - subWindowWidth, windowWidth, windowWidth, windowWidth - subWindowWidth}, new int[]{0, 0, windowHeight, windowHeight}, 4);
        uiElements.add(new UiElement(sidebarRight, Color.lightGray));
    }

}

