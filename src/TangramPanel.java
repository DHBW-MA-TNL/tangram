import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TangramPanel extends JPanel implements MouseListener, MouseMotionListener {
    private TangramShape[] shapes;
    private TangramShape selectedShape = null;

    public TangramPanel(TangramShape[] shapes) {
        this.shapes = shapes;
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(new KeyPress());
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (TangramShape shape : shapes) {
            shape.draw(g);
        }
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse click event
        if (selectedShape != null) {
            selectedShape = null;
        }
        for (TangramShape shape : shapes) {
            if (shape.shape.contains(e.getPoint())) {
                selectedShape = shape;
                System.out.println("Shape selected");
                break;
            } else {
                selectedShape = null;
            }
            System.out.println("Mouse clicked at: " + e.getPoint());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event
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
            TangramGame.touches();
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
                    selectedShape.rotate(Math.toRadians(10));
                    repaint();
                }
            }
        }
    }
}
}
