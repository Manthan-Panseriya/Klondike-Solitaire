package com.klondike_solitaire;

import javax.swing.*;
import java.awt.*;

public class Solitaire extends JFrame {

    protected static GamePanel gamePanel;
    protected static final int PANEL_WIDTH = 750, PANEL_HEIGHT = 600;
    protected static Solitaire solitaire;

    public Solitaire() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLocation(330, 110);
        setResizable(false);
        add(gamePanel);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        solitaire = new Solitaire();
    }
}