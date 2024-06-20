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

public class TangramPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final UiElement[] uiElements;

    //Time Variables
    final TangramTimer timer = new TangramTimer();
    //Shape Variables
    List<TangramShape> grayShapes;
    boolean inLevel = false;
    boolean isSolved = false;
    int lvl;
    int streakUnder1Min = 0;
    int streakOver4Min = 0;
    private List<TangramShape> puzzleShapes;
    private TangramShape selectedShape = null;
    private Point initialMousePos;


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

    void difficultyBar(){
        var scalePanel = new DifficultyScalePanel(Color.RED, Color.GREEN, 1);
        var scaleCover = new DifficultyScalePanel(Color.LIGHT_GRAY, Color.LIGHT_GRAY, 2);
        scaleCover.setBounds(20, 400, 50, ((350) / 5) * Commons.diffScaler[lvl]);
        scalePanel.setBounds(20, 400, 50, 350);
        add(scaleCover);
        add(scalePanel);
    }

    public void init() {
        isSolved = false;
        inLevel = false;

        this.grayShapes = new GrayShapes().getGrayShapes().subList(0, Commons.removeShapes[lvl]);
        this.puzzleShapes = new PuzzleSource().getPuzzleShapes().subList(0, Commons.removeShapes[lvl]);

        grayShapes = PositionRandomizer.shufflePolygons(grayShapes, new ArrayList<>(), 300, 300);

        for (TangramShape shape : grayShapes) {
            if (shape.isOutsideVisibleArea(922, 690)) {
                System.out.println("Shape out of bounds");
                init();
            }
        }

        repaint();
    }


    void solvedScreen(double time) {
        JOptionPane.showMessageDialog(this, "Gelöst nach: " + time + " Sekunden", "Gelöst", JOptionPane.INFORMATION_MESSAGE);
    }

    void drawStats(Graphics g) {
        g.setColor(Color.white);
        int statsX = 1120;
        int statsY = 190;


        g.drawString("Schwierigkeitsstufe: " + lvl, statsX, statsY + 15);
        g.drawString("Score: " + TangramGame.score, statsX, statsY + 30);
    }

    private void nonClickButton(String text, int x, int y) {
        var button = new RoundButton(text);
        button.setBackground(Color.LIGHT_GRAY); // Set the background color of the button
        button.setForeground(Color.BLACK); // Set the text color of the button
        add(button);
        int width = 200;
        int height = 50;
        button.setBounds(x - width / 2, y - height / 2, width, height); // Set the position and size of the button
    }

    private void clickButton(String text, int x, int y, ActionListener actionListener) {
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
        selectedShape = null;
        for (TangramShape shape : shapes) {
            if (shape.isNotSolved()) {
                shape.isMoveable = true;
                shape.resetShape();
            }
        }
        repaint();
    }


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

    private boolean allShapesSolved(List<TangramShape> shapes) {
        return shapes.stream().allMatch(TangramShape::isSolved);
    }

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

    private void resetStreaks() {
        streakUnder1Min = 0;
        streakOver4Min = 0;
    }

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

    void drawManual(Graphics g) {
        g.setColor(Color.white);
        g.drawString("MouseDrag/Rechtsklick - Shape wählen/bewegen", 1030, 700);
        g.drawString("Leertaste - Prüfen", 1030, 720);
        g.drawString("R - Shape drehen", 1030, 740);
        g.drawString("L - Neues Level", 1030, 760);
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selectedShape = null;
        repaint();
    }

    // MouseMotionListener methods
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


    /*
     * KeyAdapter class to handle key press and key release events
     *
     * @author Luca Krawczyk
     *
     */
    private class KeyPress extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("Key released: " + e.getKeyCode());
            // Handle key release event
            switch (e.getKeyCode()) {
                case KeyEvent.VK_L -> init();

                case KeyEvent.VK_SPACE -> {
                    if (!isSolved) {
                        isSolved(grayShapes, puzzleShapes);
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




