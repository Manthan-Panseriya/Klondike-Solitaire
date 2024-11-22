package com.klondike_solitaire;

import java.awt.*;
import java.util.Stack;

import javax.swing.*;

public class GamePanel extends JPanel {

	protected static Utility utility;
	protected static int XShift = 80;
	public static Point DECK_POSITION = new Point(560, 60);
	public static Point TABLEAU_POSITION = new Point(160, 190);
	private static int TABLEAU_OFFSET = 80;
	private static Deck deck;
	private static Waste waste;
	private static Foundation[] foundationPiles;
	private static Tableau[] tableau;

	static Stack<Card> undo = new Stack<>();

	public GamePanel() {
		super.setLayout(null);
		utility = new Utility();
		utility.thread = new Thread(utility);
		utility.thread.start();
		add(Utility.getTopPanel());
		add(Utility.getBottomPanel());
		Utility.getTopPanel().repaint();
		Utility.getBottomPanel().repaint();
		initializePiles();
		GameMoveListener l = new GameMoveListener();
		addMouseListener(l);
		addMouseMotionListener(l);
	}

	private void initializePiles() {

		deck = new Deck(DECK_POSITION.x, DECK_POSITION.y);
		add(deck);

		waste = new Waste(DECK_POSITION.x - XShift, DECK_POSITION.y);
		add(waste);

		foundationPiles = new Foundation[4];

		for (int i = 0; i < foundationPiles.length; i++) {
			foundationPiles[i] = new Foundation(80 + XShift * i, 60, i + 1);
			add(foundationPiles[i]);
		}

		tableau = new Tableau[7];

		for (int tableauIndex = 0; tableauIndex < tableau.length; tableauIndex++) {
			tableau[tableauIndex] = new Tableau(TABLEAU_POSITION.x + TABLEAU_OFFSET * (tableauIndex - 1),
					TABLEAU_POSITION.y,tableauIndex + 1);
			add(tableau[tableauIndex]);
		}
	}

	public static Foundation[] getFoundationPiles() {
		return foundationPiles;
	}

	public static Tableau[] getTableau() {return tableau; }

	public static Waste getWastePile() {
		return waste;
	}

	public static Deck getDeck() {
		return deck;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		ImageIcon img = new ImageIcon(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\Images\\green_background.jpg");
		g2d.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
