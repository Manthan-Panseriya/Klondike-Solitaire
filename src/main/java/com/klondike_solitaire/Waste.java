package com.klondike_solitaire;

import java.awt.*;

public class Waste extends Pile {

    public Waste(int x, int y) {
        super(x, y);
        super.setSize(72, 96);
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        if (this.isEmpty()) {
            g2d.drawImage(Card.getCardOutline(), 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            g2d.drawImage(this.topCard().getCardImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
