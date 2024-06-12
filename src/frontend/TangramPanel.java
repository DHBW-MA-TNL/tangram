package frontend;

import backend.Level;
import backend.PositionRandomizer;
import backend.TangramShape;
import cfg.Commons;
import main.TangramGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TangramPanel extends JPanel implements MouseListener, MouseMotionListener {
    List<TangramShape> levelShapes;
    private List<TangramShape> puzzleShapes;
    private TangramShape selectedShape = null;
    private UiElement[] uiElements;
    private Point initialMousePos;

    long startTime;
    long endTime;
    long elapsedTime = endTime - startTime;
    double elapsedTimeInSeconds = elapsedTime / 1000.0;
    boolean inLevel = false;
    boolean isSolved = false;
    JLabel solved;
    int lvl;
    int streakUnder1Min = 0;
    int streakOver4Min = 0;


    public TangramPanel(UiElement[]  uiElements, int lvl) {
        this.levelShapes = new LevelShapes().getlevelShapes().subList(0,Commons.removeShapes[lvl]);
        this.puzzleShapes = new PuzzleSource().getPuzzleShapes().subList(0,Commons.removeShapes[lvl]);
        this.uiElements = uiElements;
        this.lvl = lvl;

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(new KeyPress());
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        this.setFocusable(true);
        this.setRequestFocusEnabled(true);
        setLayout(null);

        addButton("Figuren", 1190, 50);
        clickButton("Tangram Puzzle", 120, 50,
                e -> {
                    getParent().add(new Difficulty());
                    setVisible(false);
                    getParent().remove(this);

                    repaint();

                });
        clickButton("Neues Level", 675, 730, e -> init());
        clickButton("Prüfen", 900, 730, e -> {
            if(!isSolved){
                isSolved(levelShapes,puzzleShapes);
            }
        });



        var scalePanel = new DifficultyScalePanel(Color.RED, Color.GREEN,1);
        var scaleCover = new DifficultyScalePanel(Color.LIGHT_GRAY, Color.LIGHT_GRAY,2);
        scaleCover.setBounds(20, 400, 50, ((350)/5)*Commons.diffScaler[lvl]);
        scalePanel.setBounds(20, 400, 50, 350);
        add(scaleCover);
        add(scalePanel);


        List<TangramShape> shuffledShapes = PositionRandomizer.shufflePolygons(levelShapes, new ArrayList<>(), 300, 300);
        levelShapes = shuffledShapes;
        for (TangramShape shape : levelShapes) {
            if (shape.isOutsideVisibleArea(922, 690)) {
                System.out.println("Shape out of bounds");
                init();
            }

        }

        repaint();

    }

    public void init(){
        System.out.println("Init");
        isSolved = false;
        inLevel = false;
        //remove solvedScreen
        if(solved!=null){
            remove(solved);
        }
        this.levelShapes = new LevelShapes().getlevelShapes().subList(0,Commons.removeShapes[lvl]);
        this.puzzleShapes = new PuzzleSource().getPuzzleShapes().subList(0,Commons.removeShapes[lvl]);
        this.uiElements = uiElements;

        List<TangramShape> shuffledShapes = PositionRandomizer.shufflePolygons(levelShapes, new ArrayList<>(), 300, 300);
        levelShapes = shuffledShapes;

        for (TangramShape shape : levelShapes) {
            if (shape.isOutsideVisibleArea(922, 690)) {
                System.out.println("Shape out of bounds");
                init();
            }

        }

        repaint();
    }

    void moveAllShapesInBounds(List<TangramShape> shapes, int dx, int dy) {
        for (TangramShape shape : shapes) {
            shape.move(dx, dy);
        }
    }

    boolean shapesOutOfBounds(List<TangramShape> shapes, int x, int y) {
        for (TangramShape shape : shapes) {
            if (shape.isOutsideVisibleArea(x, y)) {
                return true;
            }
        }
        return false;
    }

    void solvedScreen(double time) {
         //solved = new JLabel("Gelöst nach: " + time + " Sekunden");
        //solved.setBounds(500, 500, 200, 50);


        //JoptionPane with init() on close
        JOptionPane.showMessageDialog(this, "Gelöst nach: " + time + " Sekunden", "Gelöst", JOptionPane.INFORMATION_MESSAGE);
        init();
    }

    void drawStats(Graphics g){
        g.setColor(Color.white);
        int statsX = 1120;
        int statsY = 190;

        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        elapsedTimeInSeconds = elapsedTime / 1000.0;

        //g.drawString("Zeit: "+ elapsedTimeInSeconds, statsX, statsY);
        g.drawString("Schwierigkeitsstufe: "+ lvl, statsX,statsY+15);
        g.drawString("Score: "+ TangramGame.score, statsX, statsY+30);
    }

    private void addButton(String text, int x, int y) {
        var button = new RoundButton(text);
        button.setBackground(Color.LIGHT_GRAY); // Set the background color of the button
        button.setForeground(Color.BLACK); // Set the text color of the button
        add(button);
        int width = 200;
        int height = 50;
        button.setBounds(x - width / 2, y - height / 2, width, height); // Set the position and size of the button
    }

    private void clickButton(String text, int x, int y,   ActionListener actionListener) {
        var button = new RoundButton(text);
        button.setBackground(Color.LIGHT_GRAY); // Setze die Hintergrundfarbe des Buttons
        button.setForeground(Color.BLACK); // Setze die Schriftfarbe des Buttons
        add(button);
        button.setBounds(x - 100, y - 25, 200, 50);
        button.addActionListener(actionListener);

        add(button);
        repaint();
    }

    void resetUnsolvedShapes(List<TangramShape> shapes) {
        for (TangramShape shape : shapes) {
            if (!shape.isSolved()) {
                shape.isMoveable = true;
                shape.resetShape();
            }
        }
        repaint();
    }



    public  void isSolved(List<TangramShape> puzzle, List<TangramShape> sol) {
        boolean solved = true;
        gravity(puzzle, sol);
        for (TangramShape shape : sol) {
            if(!shape.isSolved()){
                solved=false;
                selectedShape = null;
                resetUnsolvedShapes(sol);
                System.out.println("Not solved");
                return;
            }
        }
        for (TangramShape shape : puzzle) {
            if(!shape.isSolved()){
                solved=false;
                    System.out.println("Not solved");
                return;
            }
        }

        if(solved){
            System.out.println("Solved");
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            elapsedTimeInSeconds = elapsedTime / 1000.0;
            System.out.println("Elapsed time: " + elapsedTimeInSeconds + " seconds");
            solvedScreen(elapsedTimeInSeconds);
            TangramGame.addScore(1);
            isSolved = true;
            if (elapsedTimeInSeconds < 60) {
                streakUnder1Min++;
                if (streakUnder1Min == 3 && lvl < 4) {
                    // JOptionPane. dialog button text
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Möchten Sie das nächste Level spielen?", "Weiter?", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        lvl++;
                        init();
                    }
                    streakUnder1Min = 0;
                    streakOver4Min = 0;

                }
            }
            else if (elapsedTime>240){

                streakOver4Min++;
                if (streakOver4Min == 5 && lvl > 0) {
                    // JOptionPane. dialog button text
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Möchten Sie das vorherige Level spielen?", "Zurück?", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        lvl--;
                        init();
                    }
                    streakUnder1Min = 0;
                        streakOver4Min = 0;


                }

            }else {
                streakOver4Min = 0;
                streakUnder1Min = 0;
            }
        }

    }

    public void gravity(List<TangramShape> puzzle, List<TangramShape> sol) {
        for (TangramShape shape : puzzle) {
            if(!shape.isSolved()) {
                for (TangramShape shape2 : sol) {
                    if(!shape2.isSolved()){
                    if (shape2.isCloseTo(shape)) {
                        System.out.println("Close to shape2");
                        shape2.shape.xpoints = shape.shape.xpoints;
                        shape2.shape.ypoints = shape.shape.ypoints;
                        shape.setEdges();
                        shape2.setEdges();
                        shape.setPoints();
                        shape2.setPoints();
                        shape.setSolvedPos(true);
                        shape2.setSolvedPos(true);
                        shape2.isMoveable = false;
                        shape.isMoveable = false;
                        repaint();
                    }

                }}
            }
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (UiElement uiElement : uiElements) {
            uiElement.draw(g);
            System.out.println("added frontend.UiElement");
        }
        for (TangramShape shape : levelShapes) {
            shape.draw(g);
        }
        for (TangramShape shape : puzzleShapes) {
            shape.draw(g);
        }
        this.grabFocus();

        drawStats(g);

        g.drawString("MouseDrag/Rechtsklick - Shape wählen/bewegen", 1030, 700);
        g.drawString("Leertaste - Prüfen", 1030, 720);
        g.drawString("R - Shape drehen", 1030, 740);
        g.drawString("L - Neues Level", 1030, 760);


    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (TangramShape shape : puzzleShapes) {
            if (shape.shape.contains(e.getPoint())) {
                selectedShape = shape;
                initialMousePos = e.getPoint();
                break;
            }
        }
/*        for (TangramShape shape : levelShapes) {
            if (shape.shape.contains(e.getPoint())) {
                selectedShape = shape;
                initialMousePos = e.getPoint();
                break;
            }
        }*/
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selectedShape= null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Handle mouse enter event
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Handle mouse exit event
    }

    // MouseMotionListener methods
    @Override
    public void mouseDragged(MouseEvent e) {
        if(selectedShape!=null&&!isSolved&&selectedShape.isMoveable){
            int dx = e.getX() - initialMousePos.x;
            int dy = e.getY() - initialMousePos.y;
            selectedShape.move(dx, dy);
            initialMousePos = e.getPoint();

            selectedShape.setEdges();
            selectedShape.setPoints();
            if(!inLevel){
               startTime = System.currentTimeMillis();
            }
            inLevel= true;
            repaint();
        }




    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }


    // Other methods...


private class KeyPress extends KeyAdapter {
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released: " + e.getKeyCode());
        // Handle key release event
        switch (e.getKeyCode()) {
            case KeyEvent.VK_L-> {
                // Rotate the selected shape
                init();
            }
            case KeyEvent.VK_SPACE -> {
                if(!isSolved){
                    isSolved(levelShapes,puzzleShapes);
                }
            }
            case KeyEvent.VK_F -> {
                // Flip the selected shape
                if (selectedShape != null) {
                    System.out.println("Flipping shape");
                    selectedShape.rotate(45);
                    repaint();
                }
            }

            case KeyEvent.VK_S -> {
                // Shuffle the shapes

                    List<TangramShape> shuffledShapes = PositionRandomizer.shufflePolygons(levelShapes, new ArrayList<>(), 300, 300);
                levelShapes = shuffledShapes;
                    repaint();

            }

        }
    }

    @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key pressed: " + e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R -> {
                // Rotate the selected shape
                if (selectedShape != null) {
                    System.out.println("Flipping shape");
                    selectedShape.rotate(45);
                    repaint();
                }
            }
            case KeyEvent.VK_I->{
                TangramShape dis = levelShapes.get(0);
                TangramShape dad = levelShapes.get(1);

                System.out.println(dis.pointsOnOtherLines(dad));
                repaint();

            }
            case KeyEvent.VK_P -> {
                if (selectedShape != null) {
                    System.out.println(selectedShape.points);

                    repaint();
                }
            }

        }
            // Handle key press event
        }
}

}




