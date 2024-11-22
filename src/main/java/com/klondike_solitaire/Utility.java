package com.klondike_solitaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.Stack;

public class Utility extends JPanel implements ActionListener, Runnable {

    // width and height of GamePanel is 800 x 600

    // Top panel will occupy 800 width and 40 height

    // Bottom panel will occupy 800 width and 40 height

    private static Utility topPanel, bottomPanel;
    JButton leaderboardBtn, statisticsBtn, undoBtn, resetBtn;
    static JLabel scoreLabel, scoreValueLabel, moveLabel, moveValueLabel, timeLabel, timeValueLabel;
    Thread thread;
    int sec = 0, min = 0;
    private int undoCounts = 0;

    Utility() {
        getButtons();
        setLabels();
        setTopPanel();
        setBottomPanel();
    }

    Utility(String img_path) {

    }

    protected void getButtons() {
        // for top panel
        leaderboardBtn = initButton(leaderboardBtn, "LeaderBoard");
        leaderboardBtn.setBounds(0, 5, 140, 35);

        statisticsBtn = initButton(statisticsBtn, "Statistics");
        statisticsBtn.setBounds(150, 5, 120, 35);

        // for bottom panel
        undoBtn = initButton(undoBtn, "Undo");
        undoBtn.setBounds(250, 0, 100, 35);

        resetBtn = initButton(resetBtn, "Reset");
        resetBtn.setBounds(400, 0, 100, 35);
    }

    protected JButton initButton(JButton button, String name) {

        button = new JButton(name);
        button.setFont(new Font("Rockwell", Font.BOLD, 16));
        button.setForeground(Color.BLACK);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(this);

        return button;
    }

    protected void setTopPanel() {
        topPanel = new Utility("h");
        topPanel.setBounds(0, 0, 800, 40);
        topPanel.setLayout(null);

        topPanel.add(leaderboardBtn);
        topPanel.add(statisticsBtn);
        topPanel.add(scoreLabel);
        topPanel.add(scoreValueLabel);
        topPanel.add(moveLabel);
        topPanel.add(moveValueLabel);
        topPanel.add(timeLabel);
        topPanel.add(timeValueLabel);
    }

    protected void setBottomPanel() {
        bottomPanel = new Utility("h");
        bottomPanel.setLayout(null);
        bottomPanel.setBounds(0, 560, 800, 40);

        bottomPanel.add(undoBtn);
        bottomPanel.add(resetBtn);
    }

    protected JLabel initLabel(JLabel label, String name) {
        label = new JLabel(name);
        label.setFont(new Font("Rockwell", Font.BOLD, 21));
        label.setForeground(Color.BLACK);
        return label;
    }

    protected void setLabels() {
        // 5-5 (x) gab between string and score label
        scoreLabel = initLabel(scoreLabel, "Score:");
        scoreLabel.setBounds(320, 5, 65, 30);

        scoreValueLabel = initLabel(scoreValueLabel, "0");
        scoreValueLabel.setBounds(390, 5, 50, 30);

        moveLabel = initLabel(moveLabel, "Moves:");
        moveLabel.setBounds(460, 5, 80, 30);

        moveValueLabel = initLabel(moveValueLabel, "0");
        moveValueLabel.setBounds(545, 5, 50, 30);

        timeLabel = initLabel(timeLabel, "Time:");
        timeLabel.setBounds(610, 5, 65, 30);

        timeValueLabel = initLabel(timeValueLabel, "00:00");
        timeValueLabel.setBounds(680, 5, 80, 30);
    }

    protected static JPanel getTopPanel() {
        return topPanel;
    }

    protected static JPanel getBottomPanel() {
        return bottomPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == undoBtn) {

            if (!GamePanel.undo.isEmpty() && undoCounts < 4) {
                undoCounts++;
                if (User.score > 20) {
                    User.score -= 20;
                } else {
                    User.score = 0;
                }
                Utility.scoreValueLabel.setText(String.valueOf(User.score));

                Pile prevPile = GamePanel.undo.peek().prevPile;
                Pile currentPile = GamePanel.undo.peek().currentPile;
                Card checker = GamePanel.undo.peek();
                prevPile.topCard().faceUp = false;
                prevPile.push(GamePanel.undo.pop());
                currentPile.pop();

                if (checker.undoContainsMoreThanOneCard && !Tableau.parentCardStack.isEmpty()) {
                    ArrayDeque<Card> deque = (ArrayDeque<Card>) Tableau.parentCardStack.pop();
                    while (!deque.isEmpty()) {
                        prevPile = deque.getFirst().prevPile;
                        currentPile = deque.getFirst().currentPile;

                        prevPile.push(deque.removeFirst());
                        currentPile.pop();

                        prevPile.repaint();
                        currentPile.repaint();
                    }
                }

                prevPile.repaint();
                currentPile.repaint();
                // System.out.println("check");

                if (undoCounts == 3) {
                    undoBtn.setEnabled(false);
                }

            }
        } else if (e.getSource() == resetBtn) {
            Solitaire.solitaire.dispose();
            User.score = 0;
            User.moves = 0;
            User.updateGamePlayed();
            thread.interrupt();
            Solitaire.solitaire = new Solitaire();
        } else if (e.getSource() == statisticsBtn) {
            new Statistics();
        } else if (e.getSource() == leaderboardBtn) {
            new LeaderBoard();
        }
    }

    @Override
    public void run() {
        try {
            while (min != 30) {

                if (sec == 60) {
                    sec = 0;
                    min++;
                } else {
                    sec++;
                }

                timeValueLabel.setText(min + ":" + sec);

                Thread.sleep(1000);
            }
            Solitaire.solitaire.dispose();
            User.score = 0;
            User.moves = 0;
            User.updateGamePlayed();
            Solitaire.solitaire = new Solitaire();
            System.out.println("Thread completed!!!");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        ImageIcon img = new ImageIcon(
                System.getProperty("user.dir") + "\\src\\main\\java\\com\\Images\\green_background.jpg");
        g2d.drawImage(img.getImage(), 0, 0, 800, 40, this);
    }

    protected static boolean ifWin() {

        if (GamePanel.getWastePile().cards.isEmpty() && GamePanel.getDeck().cards.isEmpty()) {

            for (Tableau tableau : GamePanel.getTableau()) {
                for (int i = 0; i < tableau.cards.size(); i++) {
                    if (!tableau.cards.get(i).isFaceUp()) {
                        return false;
                    }
                }
            }

            return true;
        }
        return false;
    }
}
