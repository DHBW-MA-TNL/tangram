import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TangramPanel extends JPanel implements MouseListener, MouseMotionListener {
    List<TangramShape> shuffled;
    private List<TangramShape> coloredShapes;
    private TangramShape selectedShape = null;
    private UiElement[] uiElements;
    private Point initialMousePos;


    public TangramPanel(List<TangramShape> coloredShapes, UiElement[]  uiElements, List<TangramShape> shuffled) {
        this.shuffled = shuffled;
        this.coloredShapes = coloredShapes;
        this.uiElements = uiElements;
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(new KeyPress());
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (UiElement uiElement : uiElements) {
            uiElement.draw(g);
        }
        for (TangramShape shape : shuffled) {
            shape.draw(g);
        }
        for (TangramShape shape : coloredShapes) {
            shape.draw(g);
        }



    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (TangramShape shape : coloredShapes) {
            if (shape.shape.contains(e.getPoint())) {
                selectedShape = shape;
                initialMousePos = e.getPoint();
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedShape != null) {
            System.out.println("Dropping shape");
            for (TangramShape shape : shuffled) {
                for (TangramShape shape2 : coloredShapes) {
                    if (selectedShape.isCloseTo(shape2)) {
                        System.out.println("Close to shape2");
                        selectedShape.shape.xpoints = shape.shape.xpoints;
                        selectedShape.shape.ypoints = shape.shape.ypoints;
                        repaint();
                    }
                }

            }
            selectedShape= null;
        }
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
        if (selectedShape != null) {
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
        // Handle mouse move event
        // Handle mouse drag event

        if (selectedShape != null) {
            Point currentPoint = e.getPoint();
            Point previousPoint = selectedShape.shape.getBounds().getLocation();

            if (previousPoint != null) {
                // Calculate the difference between the new position and the current position
                int dx = currentPoint.x - previousPoint.x;
                int dy = currentPoint.y - previousPoint.y;

                // Move the shape by the calculated difference
                selectedShape.move(new Point(dx, dy));
            }



            repaint();


        }

    }



    // Other methods...


private class KeyPress extends KeyAdapter {
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released: " + e.getKeyCode());
        // Handle key release event
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R -> {
                // Rotate the selected shape
                if (selectedShape != null) {
                    System.out.println("Rotating shape");
                   // selectedShape.rotateAroundPoint(new Point(selectedShape.shape.xpoints[0], selectedShape.shape.ypoints[0]), Math.toRadians(45));
                }
            }
            case KeyEvent.VK_SPACE -> {
                TangramGame.isSolved(shuffled);
            }
            case KeyEvent.VK_F -> {
                // Flip the selected shape
                if (selectedShape != null) {
                    System.out.println("Flipping shape");
                    selectedShape.rotate(90);
                    repaint();
                }
            }

            case KeyEvent.VK_S -> {
                // Shuffle the shapes

                    List<TangramShape> shuffledShapes = PositionRandomizer.shufflePolygons(shuffled, new ArrayList<>(), 100, 100);
                shuffled = shuffledShapes;
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
                    System.out.println("Rotating shape");
                    selectedShape.rotateAroundPoint(new Point(selectedShape.shape.xpoints[0], selectedShape.shape.ypoints[0]), 90);

                    repaint();
                }
            }
            case KeyEvent.VK_I->{
                TangramShape dis = shuffled.get(0);
                TangramShape dad = shuffled.get(1);

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
