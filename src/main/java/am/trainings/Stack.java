package am.trainings;

/**
 * Homework: Implement a Stack data structure.
 * <p>
 * A Stack follows LIFO (Last-In, First-Out) order.
 * Think of it like a stack of plates — you add and remove from the top only.
 * <p>
 * Rules:
 * - Use a plain Object[] array internally.
 * - The field `tos` (top-of-stack) tracks how many elements are on the stack.
 * - Handle edge cases: popping/peeking an empty stack should throw an exception.
 * <p>
 * Good luck!
 */
public class Stack {

    private Object[] data;
    private int tos; // top-of-stack: points to the next free slot (also equals current size)

    /**
     * Creates a Stack with the given capacity.
     * The stack starts empty (tos = 0).
     */
    public Stack(int capacity) {
        // TODO: implement
    }

    /**
     * Creates a Stack with a default capacity of 10.
     */
    public Stack() {
        // TODO: implement (hint: call the other constructor)
    }

    /**
     * Pushes (adds) an element onto the top of the stack.
     * If the stack is full, throw a RuntimeException with message "Stack is full".
     */
    public void push(Object value) {
        // TODO: implement
    }

    /**
     * Removes and returns the element at the top of the stack.
     * If the stack is empty, throw a RuntimeException with message "Stack is empty".
     */
    public Object pop() {
        // TODO: implement
        return null;
    }

    /**
     * Returns the element at the top of the stack WITHOUT removing it.
     * If the stack is empty, throw a RuntimeException with message "Stack is empty".
     */
    public Object peek() {
        // TODO: implement
        return null;
    }

    /**
     * Returns true if the stack has no elements.
     */
    public boolean isEmpty() {
        // TODO: implement
        return false;
    }

    /**
     * Returns the number of elements currently on the stack.
     */
    public int size() {
        // TODO: implement
        return 0;
    }

    /**
     * Returns true if the stack is full (no room to push more elements).
     */
    public boolean isFull() {
        // TODO: implement
        return false;
    }

    /**
     * Returns a string representation of the stack from bottom to top.
     * Example format: [1, 2, 3]  (where 3 is the top)
     * Empty stack: []
     */
    @Override
    public String toString() {
        // TODO: implement
        return "[]";
    }
}
