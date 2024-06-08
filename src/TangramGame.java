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
        List<TangramShape> rndPos = PositionRandomizer.shufflePolygons(shapes, new ArrayList<>(), 300, 300);
        //List<TangramShape> rndPos =shapes;




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




    public static Polygon multiplyPolygon(Polygon polygon, int multiplier) {
        int[] xpoints = new int[polygon.npoints];
        int[] ypoints = new int[polygon.npoints];

        for (int i = 0; i < polygon.npoints; i++) {
            xpoints[i] = polygon.xpoints[i] * multiplier;
            ypoints[i] = polygon.ypoints[i] * multiplier;
        }

        return new Polygon(xpoints, ypoints, polygon.npoints);
    }

    public static void defaultShapes(){
        int multiplier = 2; // Change this to your desired multiplier

        //TEST SHAPES
        Polygon TDL = multiplyPolygon(new Polygon(new int[]{200, 300, 400}, new int[]{200, 100, 200}, 3), 1);
        Polygon TQL = multiplyPolygon(new Polygon(new int[]{250, 300, 350,300}, new int[]{150, 100, 150,200}, 4), 1);
        //
        Polygon D1 = multiplyPolygon(new Polygon(new int[]{100, 200, 150}, new int[]{100, 100, 150}, 3), 1);

        //Polygon D1 = multiplyPolygon(new Polygon(new int[]{200, 400, 300}, new int[]{200, 200, 300}, 3), 1);
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

      //  shapes.add(new TangramShape(D0,Color.BLUE));
        //shapes.add(new TangramShape(D1,Color.RED));


         shapes.add(new TangramShape(TDL, Color.BLUE));
        shapes.add(new TangramShape(D1, Color.YELLOW));
    /*    //shapes
            shapes.add(new TangramShape(D1, Color.BLUE));
        shapes.add(new TangramShape(D2, Color.RED));
        shapes.add(new TangramShape(D3, Color.GREEN));
        shapes.add(new TangramShape(D4, Color.MAGENTA));
        shapes.add(new TangramShape(D5, Color.ORANGE));
        shapes.add(new TangramShape(Q1, Color.YELLOW));
        shapes.add(new TangramShape(P1, Color.CYAN));

     */


 /*       shapes.add(new TangramShape(D1, Color.GRAY));
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

