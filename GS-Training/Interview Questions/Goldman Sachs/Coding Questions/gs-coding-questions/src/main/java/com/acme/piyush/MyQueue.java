/*Implement a Queue using two Stacks, which is a classic interview question.
A queue is FIFO (First-In-First-Out), while a stack is LIFO (Last-In-First-Out) —
so we use two stacks to reverse the order and simulate queue behavior.*/

package com.acme.piyush;

import java.util.Stack;

public class MyQueue<T> {
    private Stack<T> inputStack = new Stack<>();
    private Stack<T> outputStack = new Stack<>();

    // Add element to the end of the queue
    public void enqueue(T x) {
        inputStack.push(x);
    }

    // Remove the element from the front of the queue
    public T dequeue() {
        shiftStacks();
        if (outputStack.isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return outputStack.pop();
    }

    // Peek at the front element
    public T peek() {
        shiftStacks();
        if (outputStack.isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return outputStack.peek();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return inputStack.isEmpty() && outputStack.isEmpty();
    }

    // Helper to move elements from input to output if needed
    private void shiftStacks() {
        if (outputStack.isEmpty()) {
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
    }

    public static void main(String[] args) {
        MyQueue<Integer> q = new MyQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        System.out.println(q.dequeue()); // 1
        System.out.println(q.peek());    // 2
        q.enqueue(4);
        System.out.println(q.dequeue()); // 2
        System.out.println(q.dequeue()); // 3
        System.out.println(q.dequeue()); // 4
        // System.out.println(q.dequeue()); // RuntimeException
    }
}
