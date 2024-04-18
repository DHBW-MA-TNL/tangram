import javax.swing.*;
import java.awt.*;

public class TangramPanel extends JPanel {
    private TangramShape[] shapes;

    public TangramPanel(TangramShape[] shapes) {
        this.shapes = shapes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (TangramShape shape : shapes) {
            shape.draw(g);
        }
    }

    // TODO: Implement methods to handle mouse events for dragging shapes
    //do
    public void rotateShape(int shapeIndex, double angle) {
        shapes[shapeIndex].rotate(angle);
        repaint();
    }

    public void flipShape(int shapeIndex, boolean horizontal) {
        shapes[shapeIndex].flip(horizontal);
        repaint();
    }

    public void dragShape(int shapeIndex, int dx, int dy) {
        shapes[shapeIndex].drag(dx, dy);
        repaint();
    }

    public void resetShapes() {
        for (TangramShape shape : shapes) {
            shape.reset();
        }
        repaint();
    }

    public boolean isSolved() {
        for (TangramShape shape : shapes) {
            if (!shape.isSolved()) {
                return false;
            }
        }
        return true;
    }

    public void solve() {
        for (TangramShape shape : shapes) {
            shape.solve();
        }
        repaint();
    }

    public void shuffle() {
        for (TangramShape shape : shapes) {
            shape.shuffle();
        }
        repaint();
    }

    public void setShapes(TangramShape[] shapes) {
        this.shapes = shapes;
        repaint();
    }



}