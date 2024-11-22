package com.klondike_solitaire;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Tableau extends Pile {

    // -------------------------------------------------------
    // Stacks
    static protected Stack<Deque<Card>> parentCardStack = new Stack<>();
    static protected Deque<Card> multipleCardStack;
    // -------------------------------------------------------

    public Tableau(int x, int y, int initSize) {
        super(x, y);
        super.setSize(72, 450);
        super.setOpaque(false);
        for (int i = 0; i < initSize; ++i) {
            push(GamePanel.getDeck().pop());
        }

        if (initSize > 0) {
            topCard().showFace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.drawLine(0, 0, this.getWidth(), 0);
        g2d.drawLine(0, 0, 0, 96);
        g2d.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, 96);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setPaint(
                new GradientPaint(36, 0,
                new Color(255, 255, 255, 160), 36, 60,
                new Color(0, 0, 0, 0)));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        int cardYPos = 0;
        if (!this.isEmpty()) {
            for (Card c : this.cards) {
                if (c.isFaceUp()) {
                    g.drawImage(c.getCardImage(), 0, cardYPos, 72, 96, this);
                    cardYPos += 20;
                } else {
                    g.drawImage(Card.getCardBack(), 0, cardYPos, 72, 96, this);
                    cardYPos += 20;
                }
            }
        }
    }

    public void moveFromWaste(Waste source, Card card) {
        if (this.accepts(card)) {
            User.score += 10;
            User.moves += 1;
            Utility.moveValueLabel.setText(String.valueOf(User.moves));
            Utility.scoreValueLabel.setText(String.valueOf(User.score));
            this.push(source.pop());
            GamePanel.undo.add(this.topCard()); // adding current moved card into undo stack
            GamePanel.undo.peek().prevPile = source;
            GamePanel.undo.peek().currentPile = this;

            if(Utility.ifWin()){
                System.out.println("You won");
                User.updateGameWonAttributes();
                WinningPopup.showWinningPopup();
            }
        }
        source = null;
    }

    public boolean accepts(Card card) {
        if (!this.isEmpty()) {
            return this.topCard().getValue() == card.getValue() + 1
                    && !this.topCard().getColour().equals(card.getColour());
        }
        return card.getValue() == 13;
    }

    public boolean moveTo(Foundation destination, Card card) {
        if (destination.accepts(card)) {

            User.score += 15;
            User.moves += 1;
            Utility.moveValueLabel.setText(String.valueOf(User.moves));
            Utility.scoreValueLabel.setText(String.valueOf(User.score));
            destination.push(this.pop());
            GamePanel.undo.add(destination.topCard()); // adding current moved card into undo stack
            GamePanel.undo.peek().prevPile = this;
            GamePanel.undo.peek().currentPile = destination;
            if (!this.isEmpty()) {
                this.topCard().showFace();
            }
            return true;
        }
        return false;
    }

    public void moveTo(Tableau destination, Card card) {
        if (!this.isEmpty() || card.getValue() == 13) {
            if (destination.accepts(card)) {
                User.score += 5;
                User.moves += 1;
                Utility.moveValueLabel.setText(String.valueOf(User.moves));
                Utility.scoreValueLabel.setText(String.valueOf(User.score));
                ArrayDeque<Card> toBeMovedCards = new ArrayDeque<>();
                while (!this.isEmpty()) {
                    Card tmp = this.pop();
                    toBeMovedCards.push(tmp);
                    if (tmp.equals(card)) {
                        break;
                    }
                }

                ArrayDeque<Card> temp = new ArrayDeque<>();

                while (!toBeMovedCards.isEmpty()) {
                    destination.push(toBeMovedCards.pop());

                    temp.addLast(destination.topCard());
                    temp.getLast().prevPile = this;
                    temp.getLast().currentPile = destination;
                }

                if (!temp.isEmpty()){

                    GamePanel.undo.push(temp.getFirst()); // for storing single cards
                    GamePanel.undo.peek().prevPile = temp.getFirst().prevPile;
                    GamePanel.undo.peek().currentPile = temp.getLast().currentPile;
                    Card checker = GamePanel.undo.peek();
                    temp.removeFirst();

                    // check if to be moved cards are more than one
                    if(!temp.isEmpty()){
                        multipleCardStack = new ArrayDeque<>();
                        checker.undoContainsMoreThanOneCard = true;
                        while (!temp.isEmpty()){
                            multipleCardStack.addLast(temp.getFirst()); // for storing single cards
                            multipleCardStack.getLast().prevPile = temp.getFirst().prevPile;
                            multipleCardStack.getLast().currentPile = temp.getFirst().currentPile;
                            temp.removeFirst();
                        }
                        parentCardStack.push(multipleCardStack);
                    }
                }
                if(Utility.ifWin()){
                    System.out.println("You won");
                    User.updateGameWonAttributes();
                    WinningPopup.showWinningPopup();
                }
            }
        }

        if (!this.isEmpty()) {
            this.topCard().showFace();
        }
    }

    public Card getClickedCard(int y) {
        int index = y / 20;
        if (index < this.cards.toArray().length) {
            Card returnMe = (Card) cards.toArray()[index];
            if (returnMe.isFaceUp()) {
                return returnMe;
            }
        }
        return (Card) cards.toArray()[cards.toArray().length - 1];
    }

}
