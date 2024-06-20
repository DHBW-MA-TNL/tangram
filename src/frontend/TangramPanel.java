package frontend;

import backend.PositionRandomizer;
import backend.TangramShape;
import backend.TangramTimer;
import cfg.Commons;
import main.TangramGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
/**
 * TangramPanel is a class that extends JPanel and implements MouseListener and MouseMotionListener.
 * It represents the main game panel for the Tangram game.
 * It contains UI elements, a timer, a list of gray shapes and puzzle shapes, and variables to track the game state.
 */
public class TangramPanel extends JPanel implements MouseListener, MouseMotionListener {
    /**
     * An array of UI elements to be displayed on the panel.
     */
    private final UiElement[] uiElements;

    /**
     * A timer to track the time elapsed during the game.
     */
    final TangramTimer timer = new TangramTimer();

    /**
     * A list of gray shapes in the game.
     */
    List<TangramShape> grayShapes;

    /**
     * A boolean variable to indicate whether the game is in level state.
     */
    boolean inLevel = false;

    /**
     * A boolean variable to indicate whether the puzzle is solved.
     */
    boolean isSolved = false;

    /**
     * An integer variable to store the current level of the game.
     */
    int lvl;

    /**
     * An integer variable to track the streak of solving puzzles under 1 minute.
     */
    int streakUnder1Min = 0;

    /**
     * An integer variable to track the streak of solving puzzles over 4 minutes.
     */
    int streakOver4Min = 0;

    /**
     * A list of puzzle shapes in the game.
     */
    private List<TangramShape> puzzleShapes;

    /**
     * A TangramShape object to store the currently selected shape.
     */
    private TangramShape selectedShape = null;

    /**
     * A Point object to store the initial mouse position when a shape is selected.
     */
    private Point initialMousePos;

/**
 * Constructor for the TangramPanel class.
 * It initializes the gray shapes and puzzle shapes based on the current level.
 * It also sets the UI elements and the level.
 * It adds mouse and key listeners to the panel, and sets the panel to be focusable.
 * It creates and adds buttons to the panel, and initializes the difficulty bar.
 * It shuffles the positions of the gray shapes, and checks if any gray shape is outside the visible area.
 * If a shape is outside the visible area, it reinitializes the game.
 * Finally, it repaints the panel to reflect the new state.
 *
 * @param uiElements The array of UI elements to be displayed on the panel.
 * @param lvl The current level of the game.
 *
 * @see UiElement
 * @see GrayShapes
 * @see PuzzleSource
 * @see PositionRandomizer
 * @see TangramShape
 */
public TangramPanel(UiElement[] uiElements, int lvl) {
    this.grayShapes = new GrayShapes().getGrayShapes().subList(0, Commons.removeShapes[lvl]);
    this.puzzleShapes = new PuzzleSource().getPuzzleShapes().subList(0, Commons.removeShapes[lvl]);
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

    nonClickButton("Figuren", 1190, 50);
    clickButton("Tangram Puzzle", 120, 50,
            e -> {
                getParent().add(new DifficultyPanel());
                setVisible(false);
                getParent().remove(this);
                repaint();
            });
    clickButton("Neues Level", 675, 730, e -> init());
    clickButton("Prüfen", 900, 730, e -> {
        if (!isSolved) {
            isSolved(grayShapes, puzzleShapes);
        }
    });

    difficultyBar();

    grayShapes = PositionRandomizer.shufflePolygons(grayShapes, new ArrayList<>(), 300, 300);

    for (TangramShape shape : grayShapes) {
        if (shape.isOutsideVisibleArea(922, 690)) {
            System.out.println("Shape out of bounds");
            init();
        }
    }

    repaint();
}

/**
 * This method creates a difficulty bar on the panel.
 * The difficulty bar consists of two parts: a scale panel and a scale cover.
 * The scale panel is a colored bar that represents the full range of difficulty levels.
 * The scale cover is a gray bar that covers a portion of the scale panel to represent the current difficulty level.
 * The position and size of the scale cover are calculated based on the current difficulty level.
 *
 * @see DifficultyScalePanel
 *
 * @author Luca Krawczyk
 */
void difficultyBar(){
    // Create a scale panel with red and green colors
    var scalePanel = new DifficultyScalePanel(Color.RED, Color.GREEN, 1);
    // Create a scale cover with light gray color
    var scaleCover = new DifficultyScalePanel(Color.LIGHT_GRAY, Color.LIGHT_GRAY, 2);
    // Set the position and size of the scale cover based on the current difficulty level
    scaleCover.setBounds(20, 400, 50, ((350) / 5) * Commons.diffScaler[lvl]);
    // Set the position and size of the scale panel
    scalePanel.setBounds(20, 400, 50, 350);
    // Add the scale cover and scale panel to the panel
    add(scaleCover);
    add(scalePanel);
}

/**
 * This method initializes the game state.
 * It sets the game state to unsolved, resets the level state, and generates new shapes for the game.
 * It also checks if any shape is outside the visible area and if so, reinitializes the game.
 * Finally, it repaints the game panel to reflect the new state.
 *
 * @see GrayShapes
 * @see PuzzleSource
 * @see PositionRandomizer
 * @see TangramShape
 */
public void init() {
    // Set the game state to unsolved
    isSolved = false;
    // Reset the level state
    inLevel = false;

    // Generate new gray shapes for the game based on the current level
    this.grayShapes = new GrayShapes().getGrayShapes().subList(0, Commons.removeShapes[lvl]);
    // Generate new puzzle shapes for the game based on the current level
    this.puzzleShapes = new PuzzleSource().getPuzzleShapes().subList(0, Commons.removeShapes[lvl]);

    // Shuffle the positions of the gray shapes
    grayShapes = PositionRandomizer.shufflePolygons(grayShapes, new ArrayList<>(), 300, 300);

    // Check if any gray shape is outside the visible area
    for (TangramShape shape : grayShapes) {
        if (shape.isOutsideVisibleArea(922, 690)) {
            // If a shape is outside the visible area, print a message and reinitialize the game
            System.out.println("Shape out of bounds");
            init();
        }
    }

    // Repaint the game panel to reflect the new state
    repaint();
}


/**
 * This method displays a dialog box with a message indicating that the puzzle has been solved.
 * The message includes the time taken to solve the puzzle.
 *
 * @param time The time taken to solve the puzzle, in seconds.
 */
void solvedScreen(double time) {
    JOptionPane.showMessageDialog(this, "Gelöst nach: " + time + " Sekunden", "Gelöst", JOptionPane.INFORMATION_MESSAGE);
}

/**
 * This method draws the game statistics on the panel.
 * It sets the color for the text, defines the position for the stats, and then draws the level and score.
 *
 * @param g The Graphics object to protect the items to be drawn on this component.
 */
void drawStats(Graphics g) {
    // Set the color for the text
    g.setColor(Color.white);
    // Define the position for the stats
    int statsX = 1120;
    int statsY = 190;

    // Draw the level on the panel
    g.drawString("Schwierigkeitsstufe: " + lvl, statsX, statsY + 15);
    // Draw the score on the panel
    g.drawString("Score: " + TangramGame.score, statsX, statsY + 30);
}

/**
 * This method creates a non-clickable button and adds it to the panel.
 * The button is created with the specified text, and its background and text colors are set.
 * The position and size of the button are calculated based on the provided x and y coordinates.
 *
 * @param text The text to be displayed on the button.
 * @param x The x-coordinate for the button's position.
 * @param y The y-coordinate for the button's position.
 *
 * @see RoundButton
 */
private void nonClickButton(String text, int x, int y) {
    // Create a new RoundButton with the specified text
    var button = new RoundButton(text);
    // Set the background color of the button to light gray
    button.setBackground(Color.LIGHT_GRAY);
    // Set the text color of the button to black
    button.setForeground(Color.BLACK);
    // Add the button to the panel
    add(button);
    // Define the width and height of the button
    int width = 200;
    int height = 50;
    // Set the position and size of the button
    button.setBounds(x - width / 2, y - height / 2, width, height);
}

/**
 * This method creates a clickable button and adds it to the panel.
 * The button is created with the specified text, and its background and text colors are set.
 * The position and size of the button are calculated based on the provided x and y coordinates.
 * An ActionListener is also added to the button to handle click events.
 *
 * @param text The text to be displayed on the button.
 * @param x The x-coordinate for the button's position.
 * @param y The y-coordinate for the button's position.
 * @param actionListener The ActionListener to handle click events.
 *
 * @see RoundButton
 * @see ActionListener
 */
private void clickButton(String text, int x, int y, ActionListener actionListener) {
    var button = new RoundButton(text);
    button.setBackground(Color.LIGHT_GRAY); // Set the background color of the button
    button.setForeground(Color.BLACK); // Set the text color of the button
    add(button);
    button.setBounds(x - 100, y - 25, 200, 50);
    button.addActionListener(actionListener);

    add(button);
    repaint();
}

/**
 * This method resets all unsolved shapes to their initial state.
 * It first sets the selected shape to null, then iterates over the provided list of shapes.
 * For each shape that is not solved, it sets the shape to be moveable and resets the shape to its initial state.
 * Finally, it repaints the panel to reflect the changes.
 *
 * @param shapes The list of shapes to be reset.
 *
 * @see TangramShape
 */
void resetUnsolvedShapes(List<TangramShape> shapes) {
    selectedShape = null;
    for (TangramShape shape : shapes) {
        if (shape.isNotSolved()) {
            shape.isMoveable = true;
            shape.resetShape();
        }
    }
    repaint();
}

/**
 * This method checks if the puzzle is solved.
 * It first applies gravity to all shapes, then checks if all shapes in both the gray shapes list and the solution shapes list are solved.
 * If the puzzle is solved, it stops the timer, calculates the elapsed time, displays a solved screen, adds score, sets the puzzle as solved, initializes a new puzzle, and updates the streaks.
 * If the puzzle is not solved, it resets all unsolved shapes in the solution shapes list.
 *
 * @param gS The list of gray shapes.
 * @param sol The list of solution shapes.
 *
 * @see TangramShape
 * @see TangramTimer
 * @see TangramGame
 */
public void isSolved(List<TangramShape> gS, List<TangramShape> sol) {
    gravityAll(gS, sol);
    if (allShapesSolved(gS) && allShapesSolved(sol)) {
        System.out.println("Solved");
        timer.stop();
        double elapsedTime = timer.getElapsedTimeInSeconds();
        System.out.println("Elapsed time: " + elapsedTime + " seconds");
        solvedScreen(elapsedTime);
        TangramGame.addScore(1);
        isSolved = true;
        init();
        updateStreaks(elapsedTime);
    } else {
        System.out.println("Not solved");
        resetUnsolvedShapes(sol);
    }
}

/**
 * This method checks if all shapes in the provided list are solved.
 * It uses the Java 8 Stream API to iterate over the list and checks if all shapes are solved.
 * A shape is considered solved if its isSolved method returns true.
 *
 * @param shapes The list of shapes to be checked.
 * @return true if all shapes are solved, false otherwise.
 *
 * @see TangramShape
 * @see Stream
 */
private boolean allShapesSolved(List<TangramShape> shapes) {
    return shapes.stream().allMatch(TangramShape::isSolved);
}

/**
 * This method updates the streaks based on the elapsed time.
 * If the elapsed time is less than the level up time, it increments the streak under 1 minute and checks for level up.
 * If the elapsed time is greater than the level down time, it increments the streak over 4 minutes and checks for level down.
 * If the elapsed time is neither less than the level up time nor greater than the level down time, it resets the streaks.
 *
 * @param elapsedTime The elapsed time in seconds.
 *
 * @see Commons
 */
private void updateStreaks(double elapsedTime) {
    if (elapsedTime < Commons.levelUpTime) {
        streakUnder1Min++;
        checkLevelUp();
    } else if (elapsedTime > Commons.levelDownTime) {
        streakOver4Min++;
        checkLevelDown();
    } else {
        resetStreaks();
    }
}

/**
 * This method checks if the player is eligible for a level up.
 * It checks if the streak of solving puzzles under 1 minute equals the required streak for a level up and if the current level is less than 4.
 * If both conditions are met, it displays a dialog box asking the player if they want to play the next level.
 * If the player chooses to play the next level, it increments the level, initializes the game state, and resets the streaks.
 * If the player chooses not to play the next level, it simply resets the streaks.
 *
 * @see JOptionPane
 * @see Commons
 */
private void checkLevelUp() {
    if (streakUnder1Min == Commons.requiredLevelUpStreak && lvl < 4) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Möchten Sie das nächste Level spielen?", "Weiter?", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            lvl++;
            init();
        }
        resetStreaks();
    }
}

/**
 * This method checks if the player is eligible for a level down.
 * It checks if the streak of solving puzzles over 4 minutes equals the required streak for a level down and if the current level is greater than 0.
 * If both conditions are met, it displays a dialog box asking the player if they want to play the previous level.
 * If the player chooses to play the previous level, it decrements the level, initializes the game state, and resets the streaks.
 * If the player chooses not to play the previous level, it simply resets the streaks.
 *
 * @see JOptionPane
 * @see Commons
 */
private void checkLevelDown() {
    if (streakOver4Min == Commons.requiredLevelDownStreak && lvl > 0) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Möchten Sie das vorherige Level spielen?", "Zurück?", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            lvl--;
            init();
        }
        resetStreaks();
    }
}

/**
 * This method resets the streaks for solving puzzles under 1 minute and over 4 minutes.
 * It sets both streakUnder1Min and streakOver4Min to 0.
 */
private void resetStreaks() {
    streakUnder1Min = 0;
    streakOver4Min = 0;
}

/**
 * This method applies gravity to all unsolved shapes in the provided lists.
 * It iterates over each shape in the first list (gray shapes), and if the shape is not solved,
 * it iterates over each shape in the second list (solution shapes).
 * If a solution shape is not solved, it applies gravity to the solution shape with respect to the gray shape,
 * and then repaints the panel to reflect the changes.
 *
 * @param gS The list of gray shapes.
 * @param sol The list of solution shapes.
 *
 * @see TangramShape
 */
public void gravityAll(List<TangramShape> gS, List<TangramShape> sol) {
    for (TangramShape grayShape : gS) {
        if (grayShape.isNotSolved()) {
            for (TangramShape coloredShape : sol) {
                if (coloredShape.isNotSolved()) {
                    coloredShape.gravity(grayShape);
                    repaint();
                }
            }
        }
    }
}

/**
 * This method is called to repaint this component.
 * It first calls the superclass's paintComponent method to ensure the component's UI is painted correctly.
 * Then, it iterates over each UI element and each shape (both gray shapes and puzzle shapes), and calls their draw method to draw them on the component.
 * After all elements and shapes are drawn, it calls the grabFocus method to request that this component gets the input focus.
 * Finally, it calls the drawStats and drawManual methods to draw the game statistics and manual on the component.
 *
 * @param g The Graphics object to protect the items to be drawn on this component.
 *
 * @see UiElement
 * @see TangramShape
 * @see Graphics
 */
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (UiElement uiElement : uiElements) {
        uiElement.draw(g);
        //System.out.println("added frontend.UiElement");
    }
    for (TangramShape shape : grayShapes) {
        shape.draw(g);
    }
    for (TangramShape shape : puzzleShapes) {
        shape.draw(g);
    }
    this.grabFocus();

    drawStats(g);
    drawManual(g);
}

/**
 * This method draws the game manual on the panel.
 * It sets the color for the text, defines the position for the manual, and then draws the instructions.
 * The instructions include how to select/move a shape, how to check if the puzzle is solved, how to rotate a shape, and how to start a new level.
 *
 * @param g The Graphics object to protect the items to be drawn on this component.
 *
 * @see Graphics
 */
void drawManual(Graphics g) {
    g.setColor(Color.white);
    g.drawString("MouseDrag/Rechtsklick - Shape wählen/bewegen", 1030, 700);
    g.drawString("Leertaste - Prüfen", 1030, 720);
    g.drawString("R - Shape drehen", 1030, 740);
    g.drawString("L - Neues Level", 1030, 760);
}

/**
 * This method is triggered when the mouse is pressed.
 * It iterates over each shape in the puzzleShapes list, and checks if the shape contains the point where the mouse was pressed.
 * If a shape contains the mouse press point, it sets that shape as the selectedShape and stores the mouse press point as the initialMousePos.
 * It then breaks the loop as we only need to select one shape at a time.
 *
 * @param e The MouseEvent that occurred.
 *
 * @see MouseEvent
 * @see TangramShape
 */
@Override
public void mousePressed(MouseEvent e) {
    for (TangramShape shape : puzzleShapes) {
        if (shape.shape.contains(e.getPoint())) {
            selectedShape = shape;
            initialMousePos = e.getPoint();
            break;
        }
    }
}

/**
 * This method is triggered when the mouse is released.
 * It sets the selectedShape to null, indicating that no shape is currently selected.
 * It then calls the repaint method to update the panel and reflect the changes.
 *
 * @param e The MouseEvent that occurred.
 *
 * @see MouseEvent
 */
@Override
public void mouseReleased(MouseEvent e) {
    selectedShape = null;
    repaint();
}
/**
 * This method is triggered when the mouse is dragged.
 * If a shape is currently selected, the puzzle is not solved, and the selected shape is moveable,
 * it calculates the distance moved by the mouse in the x and y directions, and moves the selected shape by the same distance.
 * It then updates the initial mouse position to the current mouse position, and sets the edges and points of the selected shape.
 * If the game is not in level state, it starts the timer and sets the game to be in level state.
 * Finally, it repaints the panel to reflect the changes.
 *
 * @param e The MouseEvent that occurred.
 *
 * @see MouseEvent
 * @see TangramShape
 */
@Override
public void mouseDragged(MouseEvent e) {
    if (selectedShape != null && !isSolved && selectedShape.isMoveable) {
        int dx = e.getX() - initialMousePos.x;
        int dy = e.getY() - initialMousePos.y;
        selectedShape.move(dx, dy);
        initialMousePos = e.getPoint();

        selectedShape.setEdges();
        selectedShape.setPoints();
        if (!inLevel) {
            timer.start();
        }
        inLevel = true;
        repaint();
    }
}

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    public void mouseMoved(MouseEvent e) {
    }


    /**
     * This is a private inner class that extends the KeyAdapter class.
     * It overrides the keyReleased method to provide custom behavior for key release events.
     *
     * @see KeyAdapter
     *
     * @author Luca Krawczyk
     */
    private class KeyPress extends KeyAdapter {
        /**
         * This method is called whenever a key is released.
         * It overrides the keyReleased method in the KeyAdapter class.
         *
         * @param e The KeyEvent that occurred.
         */
        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("Key released: " + e.getKeyCode());
            // Handle key release event
            switch (e.getKeyCode()) {
                // If the 'L' key is released, the init() method is called.
                case KeyEvent.VK_L -> init();

                // If the 'SPACE' key is released, the isSolved method is called
                // if the puzzle is not already solved.
                case KeyEvent.VK_SPACE -> {
                    if (!isSolved) {
                        isSolved(grayShapes, puzzleShapes);
                    }
                }
                // If the 'F' key is released, the selected shape is flipped
                // if a shape is currently selected.
                case KeyEvent.VK_F -> {
                    // Flip the selected shape
                    if (selectedShape != null) {
                        System.out.println("Flipping shape");
                        selectedShape.rotate(45);
                        repaint();
                    }
                }

                // If the 'R' key is released, the selected shape is rotated
                // if a shape is currently selected.
                case KeyEvent.VK_R -> {
                    // Rotate the selected shape
                    if (selectedShape != null) {
                        System.out.println("Flipping shape");
                        selectedShape.rotate(45);
                        repaint();
                    }
                }
            }
        }
    }

}




