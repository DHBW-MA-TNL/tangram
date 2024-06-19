package main;

import frontend.Difficulty;
import frontend.UiElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TangramGame extends JFrame {

    public static List<UiElement> uiElements = new ArrayList<>();
    public static int score = 0;

    public TangramGame() {
        setTitle("Tangram Game");
        setSize(1350, 800);
        setMinimumSize(new Dimension(800, 800));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new Difficulty());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new TangramGame();
            game.setVisible(true);
        });
    }

    public static void addScore(int i) {
        score = score + i;
    }
}

