package com.klondike_solitaire;

public class OwnStack {
    private Card[] stackArray;
    private int top;
    private int maxSize;

    public OwnStack(int size) {
        this.maxSize = size;
        this.stackArray = new Card[52];
        this.top = -1; // Stack is initially empty
    }

    // Method to push an element onto the stack
    public void push(Card value) {
        if (top == maxSize - 1) {
            System.out.println("Stack Overflow! Cannot add " + value);
        } else {
            stackArray[++top] = value;
            System.out.println(value + " pushed onto the stack.");
        }
    }

    // Method to pop an element from the stack
    public Card pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow! No element to pop.");
            return null;
        } else {
            Card poppedValue = stackArray[top--];
            System.out.println(poppedValue + " popped from the stack.");
            return poppedValue;
        }
    }

    public Card peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty. No element to peek.");
            return null;
        } else {
            return stackArray[top];
        }
    }

    public boolean isEmpty() {
        return (top == -1);
    }
}