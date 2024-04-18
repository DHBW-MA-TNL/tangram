import javax.swing.*;
import java.awt.*;

public class TangramGame extends JFrame{

    public  TangramGame(){
        //shapes
        var shapes = new TangramShape[]{
                new TangramShape(new Polygon(new int[]{0, 100, 200}, new int[]{0, 100, 0}, 3), Color.RED),
                new TangramShape(new Polygon(new int[]{0, 100, 200}, new int[]{0, 100, 0}, 3), Color.GREEN),
                new TangramShape(new Polygon(new int[]{0, 100, 200}, new int[]{0, 100, 0}, 3), Color.BLUE),
                new TangramShape(new Polygon(new int[]{0, 100, 200}, new int[]{0, 100, 0}, 3), Color.YELLOW),
                new TangramShape(new Polygon(new int[]{0, 100, 200}, new int[]{0, 100, 0}, 3), Color.ORANGE),
                new TangramShape(new Polygon(new int[]{0, 100, 200}, new int[]{0, 100, 0}, 3), Color.MAGENTA),
                new TangramShape(new Polygon(new int[]{0, 100, 200}, new int[]{0, 100, 0}, 3), Color.CYAN),
                new TangramShape(new Polygon(new int[]{0, 100, 200}, new int[]{0, 100, 0}, 3), Color.PINK),
        };

        add(new TangramPanel(shapes));
        setTitle("Tangram Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new TangramGame();
            game.setVisible(true);
        });
    }
}