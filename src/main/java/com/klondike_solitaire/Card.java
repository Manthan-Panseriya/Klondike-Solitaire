package com.klondike_solitaire;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Card {

    public static String cardBackFilename = "back001",
            cardOutlineFilename = "bottom01",
            fpBaseFilename = "fpBase0";
    public static String directory = "Images", extension = ".gif";
    private Image im;
    private int value;
    private String suit;
    protected boolean faceUp;
    private Colour colour;
    protected Pile prevPile, currentPile;
    protected boolean undoContainsMoreThanOneCard;

    public Card(int value, Suit suit) {
        this.value = value;
        switch (suit) {

            case Clubs:
                this.suit = "c";
                colour = Colour.Black;
                break;

            case Diamonds:
                this.suit = "d";
                colour = Colour.Red;
                break;

            case Spades:
                this.suit = "s";
                colour = Colour.Black;
                break;

            case Hearts:
                this.suit = "h";
                colour = Colour.Red;
                break;
        }
        // E:\Klondike Solitaire\src\main\java\com\Images\01c.gif
        faceUp = false;
        // getClass().getResource(directory + cardFile(suit, value))
        try {
            ImageIcon ii = new ImageIcon(
                    System.getProperty("user.dir") + "\\src\\main\\java\\com\\Images" + cardFile(suit, value));
            im = ii.getImage();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String cardFile(Suit s, int val) {

        char ch = 'a';

        if (s == Suit.Clubs) {
            ch = 'c';
        } else if (s == Suit.Hearts) {
            ch = 'h';
        } else if (s == Suit.Spades) {
            ch = 's';
        } else if (s == Suit.Diamonds) {
            ch = 'd';
        }

        if (val < 10)
            return "/0" + val + ch + extension;
        else
            return "/" + val + ch + extension;
    }

    public Image getCardImage() {
        return im;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public Colour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }

    // Card.class.getResource(directory + "/" + fpBaseFilename + suit + extension)
    public static Image getFoundationBase(int suit) {
        ImageIcon ii = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\com\\Images\\"
                + fpBaseFilename + suit + extension);
        return ii.getImage();
    }

    // Card.class.getResource(directory + "/" + cardOutlineFilename + extension)
    public static Image getCardOutline() {
        ImageIcon ii = new ImageIcon(
                System.getProperty("user.dir") + "\\src\\main\\java\\com\\Images\\" + cardOutlineFilename + extension);
        return ii.getImage();// x
    }

    // Card.class.getResource(directory + "/" + cardBackFilename + extension)
    public static Image getCardBack() {
        ImageIcon ii = new ImageIcon(
                System.getProperty("user.dir") + "\\src\\main\\java\\com\\Images\\" + cardBackFilename + ".jpg");
        return ii.getImage();
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public void showFace() {
        faceUp = true;
    }
}