/**
 * Stack (LIFO) of UndoAction objects, implemented with linked nodes.
 * Supports: push, pop, peek, isEmpty, size, display.
 */
public class ActionStack {

    private static class Node {
        UndoAction data;
        Node next;

        Node(UndoAction data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node top;
    private int size;

    public ActionStack() {
        top = null;
        size = 0;
    }

    /** Adds an action on top. O(1). */
    public void push(UndoAction action) {
        top = new Node(action, top);
        size++;
    }

    /** Removes and returns the top action, or null if the stack is empty. O(1). */
    public UndoAction pop() {
        if (isEmpty()) return null;
        UndoAction action = top.data;
        top = top.next;
        size--;
        return action;
    }

    /** Returns the top action without removing it, or null if empty. O(1). */
    public UndoAction peek() {
        return isEmpty() ? null : top.data;
    }

    public boolean isEmpty() { return top == null; }
    public int size() { return size; }

    /** Prints the stack from top (most recent) to bottom. O(n). */
    public void display() {
        if (isEmpty()) {
            System.out.println("  (undo history is empty)");
            return;
        }
        Node cur = top;
        int pos = 1;
        while (cur != null) {
            System.out.println("  " + pos++ + ". " + cur.data + (cur == top ? "   <-- next to undo" : ""));
            cur = cur.next;
        }
    }
}
