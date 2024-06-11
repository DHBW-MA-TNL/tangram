package main;

import backend.PositionRandomizer;
import backend.TangramShape;
import frontend.Difficulty;
import frontend.UiElement;

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
    public static int score = 0;

    public TangramGame() {
        setTitle("Tangram Game");
        setSize(1350, 800);
        setMinimumSize(new Dimension(800, 800));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUiElements();
        setResizable(false);

        //backend.TangramShape
        List<TangramShape> rndPos = PositionRandomizer.shufflePolygons(shapes, new ArrayList<>(), 300, 300);
        //List<backend.TangramShape> rndPos =shapes;


        //add(new frontend.TangramPanel(uiElements.toArray(new frontend.UiElement[0])));

        add(new Difficulty());

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

    public static int addScore(int i){
        score =score+i;
        return score;
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





    public void setUiElements() {
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int subWindowWidth = windowWidth / 4;
        int subWindowHeight = windowHeight / 8;
        Polygon sidebarRight = new Polygon(new int[]{windowWidth - subWindowWidth, windowWidth, windowWidth, windowWidth - subWindowWidth}, new int[]{0, 0, windowHeight, windowHeight}, 4);
        uiElements.add(new UiElement(sidebarRight, Color.lightGray));
    }



}

