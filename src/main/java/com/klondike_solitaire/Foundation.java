package com.klondike_solitaire;
import java.awt.*;

public class Foundation extends Pile{
	
	private int suit;

	public Foundation(int x, int y, int i) {
		super(x, y);
		super.setSize(72, 96);
		this.suit = i;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		if(this.isEmpty()) {
			g2d.drawImage(Card.getFoundationBase(suit), 0, 0,	this.getWidth(), this.getHeight(), this);
		}else {
			g2d.drawImage(this.topCard().getCardImage(),0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	public void moveFromWaste(Waste source, Card card) {
		if(accepts(card)) {
			User.score += 20;
			User.moves += 1;
			Utility.moveValueLabel.setText(String.valueOf(User.moves));
			Utility.scoreValueLabel.setText(String.valueOf(User.score));

			this.push(source.pop());
			GamePanel.undo.add(this.topCard()); // adding current moved card into undo stack
			GamePanel.undo.peek().prevPile = source;
			GamePanel.undo.peek().currentPile = this;

			if(Utility.ifWin()){
				System.out.println("you won");
				User.updateGameWonAttributes();
				WinningPopup.showWinningPopup();
			}
			source = null;
		}
	}
	
	public void moveTo(Tableau destination, Card card) {
		if(destination.accepts(card)) {
			User.score += 20;
			User.moves += 1;
			Utility.moveValueLabel.setText(String.valueOf(User.moves));
			Utility.scoreValueLabel.setText(String.valueOf(User.score));
			destination.push(this.pop());
			GamePanel.undo.add(destination.topCard()); // adding current moved card into undo stack
			GamePanel.undo.peek().prevPile = this;
			GamePanel.undo.peek().currentPile = destination	;
		}
	}

	public boolean accepts(Card card) {
		if(!this.isEmpty()) {
			return this.topCard().getValue() == card.getValue() - 1 &&
					this.topCard().getSuit().equals(card.getSuit());
		}
		return card.getValue() == 1 && intToSuit(card.getSuit()); // Ace
	}
	
	private boolean intToSuit(String pSuit) {
		if (pSuit.equals("c")) {
			return this.suit == 3;
		} else if (pSuit.equals("s")) {
			return this.suit == 1;
		} else if (pSuit.equals("h")) {
			return this.suit == 2;
		} else if (pSuit.equals("d")) {
			return this.suit == 4;
		}
		return false;
	}
}