package frontend;

import java.awt.*;

/**
 * UiElement is a class that represents a UI element in the game.
 * It contains a shape and a color, and provides a method to draw the shape with the specified color.
 */
public class UiElement {
    /**
     * The shape of the UI element.
     */
    private final Polygon shape;

    /**
     * The color of the UI element.
     */
    private final Color color;

    /**
     * Constructor for the UiElement class.
     * It initializes the shape and color of the UI element.
     *
     * @param shape The shape of the UI element.
     * @param color The color of the UI element.
     */
    public UiElement(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    /**
     * This method draws the UI element.
     * It sets the color of the Graphics object to the color of the UI element, and fills the shape of the UI element with this color.
     *
     * @param g The Graphics object to draw on.
     *
     * @see Graphics
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(shape);
    }
}