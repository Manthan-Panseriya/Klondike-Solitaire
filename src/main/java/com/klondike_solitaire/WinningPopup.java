package com.klondike_solitaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinningPopup {

    public static void showWinningPopup() {

        JFrame frame = new JFrame("You Win!");
        frame.setUndecorated(true);
        frame.setSize(400, 300);
        frame.setLocation(500, 300);
        frame.setLayout(null); // No layout manager

        // Create panel for the popup
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(null);
        panel.setBounds(0, 0, 400, 300);
        frame.add(panel);

        // Add winning message labels
        JLabel congratsLabel = new JLabel("You Won the Game", JLabel.CENTER);
        congratsLabel.setBounds(50, 50, 300, 30);
        congratsLabel.setForeground(new Color(255, 255, 255, 0)); // Invisible initially
        congratsLabel.setFont(new Font("Serif", Font.BOLD, 28));
        panel.add(congratsLabel);

        JLabel congratsLabel1 = new JLabel("Congratulations!", JLabel.CENTER);
        congratsLabel1.setBounds(50, 120, 300, 40);
        congratsLabel1.setForeground(new Color(255, 255, 255, 0)); // Invisible initially
        congratsLabel1.setFont(new Font("Serif", Font.BOLD, 35));
        panel.add(congratsLabel1);

        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(150, 400, 100, 30); // Starting out of view (for sliding effect)
        closeButton.setBackground(Color.red);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Serif", Font.BOLD, 16));
        closeButton.setFocusable(false);
        closeButton.setVisible(false); // Initially invisible
        panel.add(closeButton);

        // Add ActionListener to close the popup
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the popup window
            }
        });

        // Start the animation
        animatePopup(frame, panel, congratsLabel, congratsLabel1, closeButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void animatePopup(JFrame frame, JPanel panel, JLabel congratsLabel, JLabel congratsLabel1, JButton closeButton) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // Step 1: Fade in the panel
                    int alpha = 0;
                    while (alpha <= 255) {
                        panel.setBackground(new Color(0, 0, 0, alpha)); // Fade in the black background
                        alpha += 5;
                        Thread.sleep(10);
                    }

                    // Step 2: Smooth scaling and fading of the labels
                    float scaleFactor = 0.5f;
                    int labelY = 50, label1Y = 120;
                    while (scaleFactor <= 1.0f) {
                        congratsLabel.setFont(congratsLabel.getFont().deriveFont(28 * scaleFactor));
                        congratsLabel1.setFont(congratsLabel1.getFont().deriveFont(35 * scaleFactor));
                        congratsLabel.setBounds(50, labelY, 300, (int) (30 * scaleFactor));
                        congratsLabel1.setBounds(50, label1Y, 300, (int) (40 * scaleFactor));

                        congratsLabel.setForeground(new Color(255, 255, 255, (int) (255 * scaleFactor)));
                        congratsLabel1.setForeground(new Color(255, 255, 255, (int) (255 * scaleFactor)));

                        scaleFactor += 0.05f;
                        Thread.sleep(30);
                    }

                    // Step 3: Bounce the labels
                    for (int i = 0; i < 2; i++) {
                        congratsLabel.setBounds(50, labelY - 10, 300, 30);
                        congratsLabel1.setBounds(50, label1Y - 10, 300, 40);
                        Thread.sleep(100);
                        congratsLabel.setBounds(50, labelY, 300, 30);
                        congratsLabel1.setBounds(50, label1Y, 300, 40);
                        Thread.sleep(100);
                    }

                    // Step 4: Slide in the close button
                    int buttonY = 400;
                    closeButton.setVisible(true); // Make the button visible now
                    while (buttonY > 240) {
                        buttonY -= 5;
                        closeButton.setBounds(150, buttonY, 100, 30);
                        Thread.sleep(20);
                    }

                    // Step 5: Subtle color pulse effect for the labels
                    for (int i = 0; i < 5; i++) {
                        congratsLabel.setForeground(i % 2 == 0 ? Color.yellow : Color.white);
                        congratsLabel1.setForeground(i % 2 == 0 ? Color.green : Color.white);
                        Thread.sleep(300); // Pulse every 300ms
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        showWinningPopup();
    }
}
