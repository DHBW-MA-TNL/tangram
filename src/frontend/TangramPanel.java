package frontend;

import backend.PositionRandomizer;
import backend.TangramShape;
import cfg.Commons;
import main.TangramGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TangramPanel extends JPanel implements MouseListener, MouseMotionListener {
    List<TangramShape> levelShapes;
    private List<TangramShape> puzzleShapes;
    private TangramShape selectedShape = null;
    private UiElement[] uiElements;
    private Point initialMousePos;
    int lvl;


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


        List<TangramShape> shuffledShapes = PositionRandomizer.shufflePolygons(levelShapes, new ArrayList<>(), 300, 300);
        levelShapes = shuffledShapes;
        repaint();

    }

    public void init(){
        this.levelShapes = new LevelShapes().getlevelShapes().subList(0,Commons.removeShapes[lvl]);
        this.puzzleShapes = new PuzzleSource().getPuzzleShapes().subList(0,Commons.removeShapes[lvl]);
        this.uiElements = uiElements;

        List<TangramShape> shuffledShapes = PositionRandomizer.shufflePolygons(levelShapes, new ArrayList<>(), 300, 300);
        levelShapes = shuffledShapes;
        repaint();
    }

    void drawStats(Graphics g){
        g.setColor(Color.black);
        g.drawString("Schwierigkeitsstufe: "+ lvl, 900,50);
        g.drawString("Score: "+ TangramGame.score, 900, 60);
    }



    public  void isSolved(List<TangramShape> puzzle, List<TangramShape> sol) {
        boolean solved = true;
        gravity(puzzle, sol);
        for (TangramShape shape : puzzle) {
            if(!shape.isSolved()){
                solved=false;

                    System.out.println("Not solved");
                return;
            }
        }
        for (TangramShape shape : sol) {
            if(!shape.isSolved()){
                solved=false;

                System.out.println("Not solved");
                return;
            }
        }
        if(solved){
            System.out.println("Solved");
            TangramGame.addScore(1);
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
        if (false) {
            System.out.println("Dropping shape");
            for (TangramShape shape : levelShapes) {
                    if (selectedShape.isCloseTo(shape)) {
                        System.out.println("Close to shape2");
                        selectedShape.shape.xpoints = shape.shape.xpoints;
                        selectedShape.shape.ypoints = shape.shape.ypoints;
                        repaint();

                }

            }

        }
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
        if(selectedShape!=null){
            int dx = e.getX() - initialMousePos.x;
            int dy = e.getY() - initialMousePos.y;
            selectedShape.move(dx, dy);
            initialMousePos = e.getPoint();

            selectedShape.setEdges();
            selectedShape.setPoints();
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
                isSolved(levelShapes,puzzleShapes);
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




