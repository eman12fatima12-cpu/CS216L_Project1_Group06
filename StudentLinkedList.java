/**
 * Singly linked list that stores Student records.
 * Also contains the searching and sorting algorithms used by the project.
 */
public class StudentLinkedList {

    /** A single node of the list. */
    private static class Node {
        Student data;
        Node next;

        Node(Student data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public StudentLinkedList() {
        head = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    /** Inserts a student at the end of the list. O(n) because we walk to the tail. */
    public void addLast(Student s) {
        Node newNode = new Node(s);
        if (head == null) {
            head = newNode;
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = newNode;
        }
        size++;
    }

    /**
     * Deletes the node with the given ID and unlinks it from the list.
     * Returns the removed Student, or null if the ID was not found.
     */
    public Student deleteById(int id) {
        if (head == null) return null;

        // Case 1: the head node is the one to delete
        if (head.data.getId() == id) {
            Student removed = head.data;
            head = head.next;      // old head becomes unreachable (garbage collected)
            size--;
            return removed;
        }

        // Case 2: delete a node somewhere after the head
        Node prev = head;
        Node cur = head.next;
        while (cur != null) {
            if (cur.data.getId() == id) {
                prev.next = cur.next;   // unlink the node
                cur.next = null;
                size--;
                return cur.data;
            }
            prev = cur;
            cur = cur.next;
        }
        return null;
    }

    /** SEARCH: Linear search by ID. O(n). Returns null if not found. */
    public Student linearSearchById(int id) {
        Node cur = head;
        while (cur != null) {
            if (cur.data.getId() == id) {
                return cur.data;
            }
            cur = cur.next;
        }
        return null;
    }

    /** SEARCH: Linear search by name (case-insensitive, partial match). O(n). */
    public StudentLinkedList linearSearchByName(String keyword) {
        StudentLinkedList results = new StudentLinkedList();
        String key = keyword.toLowerCase();
        Node cur = head;
        while (cur != null) {
            if (cur.data.getName().toLowerCase().contains(key)) {
                results.addLast(cur.data);
            }
            cur = cur.next;
        }
        return results;
    }

    /**
     * SORT: Bubble sort by GPA. O(n^2) worst/average, O(n) best (already sorted).
     * Swaps the data inside nodes instead of re-linking nodes.
     * @param descending true for highest GPA first
     */
    public void bubbleSortByGpa(boolean descending) {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            swapped = false;
            Node cur = head;
            while (cur.next != null) {
                double a = cur.data.getGpa();
                double b = cur.next.data.getGpa();
                boolean outOfOrder = descending ? (a < b) : (a > b);
                if (outOfOrder) {
                    Student temp = cur.data;
                    cur.data = cur.next.data;
                    cur.next.data = temp;
                    swapped = true;
                }
                cur = cur.next;
            }
        } while (swapped);
    }

    /** Prints every record in the list. O(n). */
    public void display() {
        if (head == null) {
            System.out.println("  (no records)");
            return;
        }
        Node cur = head;
        int count = 1;
        while (cur != null) {
            System.out.println("  " + count++ + ". " + cur.data);
            cur = cur.next;
        }
    }
}
