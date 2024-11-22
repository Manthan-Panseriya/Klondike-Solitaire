package com.klondike_solitaire;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Statistics extends JFrame {

    // private JButton back;
    protected JLabel statisticsLabel, gamePlayedLabel, gameWonLabel, bestTimeLabel, totalMoveLabel, bestScoreLabel;
    protected JLabel gamePlayedValueLabel, gameWonValueLabel, bestTimeValueLabel, totalMoveValueLabel, bestScoreValueLabel;
    protected JPanel panel;

    public Statistics() {

        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setBounds(0, 0, 340, 390);
        panel.setLayout(null);
        add(panel);

        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setBackground(Color.BLUE);
        statisticsPanel.setLayout(null);
        statisticsPanel.setBounds(0, 0, 350, 35);
        panel.add(statisticsPanel);

        statisticsLabel = initPanel(statisticsLabel, "STATISTICS");
        statisticsLabel.setBounds(115, 5, 340, 34);
        statisticsPanel.add(statisticsLabel);

        gamePlayedLabel = initPanel(gamePlayedLabel, "Game Played:");
        gamePlayedLabel.setBounds(20, 80, 200, 20);
        panel.add(gamePlayedLabel);

        gameWonLabel = initPanel(gameWonLabel ,"Game Won:");
        gameWonLabel.setBounds(20, 130, 200, 20);
        panel.add(gameWonLabel);

        bestTimeLabel = initPanel(bestTimeLabel, "Best Time");
        bestTimeLabel.setBounds(20, 180, 200, 20);
        panel.add(bestTimeLabel);

        totalMoveLabel = initPanel(totalMoveLabel, "Total move:");
        totalMoveLabel.setBounds(20, 230, 200, 20);
        panel.add(totalMoveLabel);

        bestScoreLabel = initPanel(bestTimeLabel, "Best Score:");
        bestScoreLabel.setBounds(20, 280, 200, 20);
        panel.add(bestScoreLabel);

        setScoreLabels();

        JButton button = new JButton("Back");
        button.setBackground(Color.red);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBounds(100, 350, 150, 30);
        button.setFocusable(false);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setSize(350, 400);
        this.setLocation(580, 220);

        this.setUndecorated(true);
        this.setVisible(true);
        this.setLayout(null);

    }

    private JLabel initPanel(JLabel label ,String name){
        label = new JLabel(name);
        label.setForeground(Color.white);
        label.setFont(new Font("Consalas", Font.BOLD, 20));
        return label;
    }

    private void setScoreLabels(){

        gamePlayedValueLabel = initPanel(gamePlayedValueLabel, String.valueOf(User.game_played));
        gamePlayedValueLabel.setBounds(280, 80, 60, 20);
        panel.add(gamePlayedValueLabel);

        gameWonValueLabel = initPanel(gameWonValueLabel ,String.valueOf(User.game_won));
        gameWonValueLabel.setBounds(280, 130, 60, 20);
        panel.add(gameWonValueLabel);

        if(User.best_time == null)
            bestTimeValueLabel = initPanel(bestTimeValueLabel, "00:00");
        else
            bestTimeValueLabel = initPanel(bestTimeValueLabel, User.best_time);

        bestTimeValueLabel.setBounds(262, 180, 60, 20);
        panel.add(bestTimeValueLabel);

        totalMoveValueLabel = initPanel(totalMoveValueLabel, String.valueOf(User.moves));
        totalMoveValueLabel.setBounds(280, 230, 60, 20);
        panel.add(totalMoveValueLabel);

        bestScoreValueLabel = initPanel(bestScoreValueLabel, String.valueOf(User.best_score));
        bestScoreValueLabel.setBounds(280, 280, 60, 20);
        panel.add(bestScoreValueLabel);

    }
}
