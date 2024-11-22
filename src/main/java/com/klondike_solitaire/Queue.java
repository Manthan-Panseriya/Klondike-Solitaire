package com.klondike_solitaire;

public class Queue {
    private Card[] arr;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public Queue() {
        this.capacity = 12; // Default capacity
        arr = new Card[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public Queue(int capacity) {
        this.capacity = capacity;
        arr = new Card[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(Card obj) {
        if (isFull()) {
            grow(); // Grow the array if the queue is full
        }

        rear = (rear + 1) % capacity; // circular queue working
        arr[rear] = obj;
        size++;
    }

    public Card dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
        }

        Card obj = arr[front];
        front = (front + 1) % capacity; // circular queue working
        size--;
        return obj;
    }

    public Card peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
        }
        return arr[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    // grow the queue by doubling its capacity
    private void grow() {
        int newCapacity = capacity * 2;
        Card[] newArr = new Card[newCapacity];

        for (int i = 0; i < size; i++) {
            newArr[i] = arr[(front + i) % capacity];
        }

        arr = newArr;
        front = 0;
        rear = size - 1;
        capacity = newCapacity;
    }

    public void printQueue() {
        for (int i = 0; i < size; i++) {
            System.out.print(arr[(front + i) % capacity] + " ");
        }
        System.out.println();
    }
}
