package com.klondike_solitaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class Deck extends Pile implements ActionListener {

    JButton resetBtn = null;

    public Deck(int x, int y) {

        super(x, y);
        super.setSize(72, 96);

        for (Suit suit : Suit.values()) {
            for (int j = 1; j <= 13; ++j) {
                push(new Card(j, suit));
            }
        }

        Collections.shuffle(cards);

        initButton();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.white);
        g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        if (!isEmpty()) {
            g2d.drawImage(Card.getCardBack(), 0, 0, this.getWidth(), this.getHeight(), this);
            if (resetBtn != null) {
                // resetBtn is created
                this.remove(resetBtn);
                this.revalidate();
                this.repaint();
            }
        } else {
            // jyare deck khali hase tyare aa run thase
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            this.add(resetBtn);
        }
    }

    protected void initButton() {
        resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("Sans Serif", Font.BOLD, 10));
        resetBtn.setContentAreaFilled(false);
        resetBtn.setBounds(0, 30, 70, 20);
        resetBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetBtn) {
            while (!GamePanel.getWastePile().isEmpty()) {
                push(GamePanel.getWastePile().pop());
            }
            if(User.score > 20){
                User.score -= 40;
            }
            this.repaint();
            GamePanel.getWastePile().repaint();
        }
    }
}
