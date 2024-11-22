package com.klondike_solitaire;

import javax.swing.*;
import java.awt.*;

public class HowtoPlay extends JFrame {
    JButton forwardBtn;
    HowtoPlay(){


        JPanel panel=new JPanel();
        panel.setBounds(0,0,550,550);
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        add(panel);

        JButton button = new JButton("Backward");
        button.setBackground(Color.red);
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 18));
        button.setBounds(40, 480, 150, 30);
        button.setFocusable(false);
        panel.add(button);

         forwardBtn = new JButton("Forward");
        forwardBtn.setBackground(Color.red);
        forwardBtn.setForeground(Color.WHITE);
        forwardBtn.setFocusable(false);
        forwardBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
        forwardBtn.setBounds(350, 480, 150, 30);
        forwardBtn.setFocusable(false);
        panel.add(forwardBtn);

        setUndecorated(true);
        setSize(550,550);
        setLocation(420,130);
        setLayout(null);
        setVisible(true);


    }

    public static void main(String[] args) {
        new HowtoPlay();
    }
}
