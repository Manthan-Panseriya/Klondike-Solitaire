package com.klondike_solitaire;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class GameMoveListener extends MouseInputAdapter {
	
	private Deck deck = GamePanel.getDeck();
	private Waste waste = null;
	private Tableau selectedTaubleau = null;
	private Foundation selectedFoundation = null;
	private Card selectedCard = null;

	@Override
	public void mousePressed(MouseEvent e) {
		Component pressedComponent = e.getComponent().getComponentAt(e.getPoint());
		if(pressedComponent instanceof Foundation) {
			selectedFoundation = (Foundation) pressedComponent;
			selectedTaubleau = null;
			waste = null;
			selectedCard = selectedFoundation.topCard();
		}else if(pressedComponent instanceof Tableau) {
			selectedTaubleau = (Tableau) pressedComponent;
			waste = null;
			selectedCard = selectedTaubleau.getClickedCard(e.getY() - 200);
			// for directly moving card from tableau to foundation
			for(Foundation foundation : GamePanel.getFoundationPiles()) {
				if(selectedTaubleau.moveTo(foundation, selectedCard)) { // undo added
					selectedTaubleau = null;
					break;
				}
			}
		}else if(pressedComponent instanceof Deck) {
			selectedTaubleau = null;
			if(!deck.isEmpty()) {
				Waste waste = GamePanel.getWastePile();
				waste.push(deck.pop());
				GamePanel.undo.add(waste.topCard()); // adding current moved card into undo stack
				GamePanel.undo.peek().prevPile = deck;
				GamePanel.undo.peek().currentPile = waste;
				waste.topCard().showFace();
			}
		}else if(pressedComponent instanceof Waste) {
			selectedTaubleau = null;
			waste = GamePanel.getWastePile();
			selectedCard = waste.topCard();
			if(selectedCard != null) {
				for(Foundation foundation : GamePanel.getFoundationPiles()) {
					foundation.moveFromWaste(waste, selectedCard); // undo added
				}
			}
		}
		e.getComponent().repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(selectedCard != null) {
			Component releasedComponent = e.getComponent().getComponentAt(e.getPoint());
			if(releasedComponent instanceof Tableau) {
				if(waste != null) {
					Tableau destination = (Tableau) releasedComponent;
					if(!waste.isEmpty()) {
						destination.moveFromWaste(waste, selectedCard); // undo added
					}
					waste.repaint();
				}else if(selectedTaubleau != null) {
					Tableau source = selectedTaubleau;
					Tableau destination = (Tableau) releasedComponent;
					source.moveTo(destination, selectedCard); // undo added
					source.repaint();
				}else if(selectedFoundation != null) { 
					Foundation source = selectedFoundation;
					Tableau destination = (Tableau) releasedComponent;
					source.moveTo(destination, selectedCard); // undo added
					source.repaint();
					destination.repaint();
				}
			}
		}
		e.getComponent().repaint();
		selectedCard = null;
		selectedFoundation = null;
		selectedTaubleau = null;
		waste = null;
	}
}