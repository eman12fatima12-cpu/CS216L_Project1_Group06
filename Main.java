import java.util.Scanner;

/**
 * Student Record System - menu-driven console application.
 * Linked list stores records; stack stores undo history.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentLinkedList students = new StudentLinkedList();
    private static final ActionStack undoStack = new ActionStack();

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ", 0, 9);
            System.out.println();
            switch (choice) {
                case 1: addStudent(); break;
                case 2: deleteStudent(); break;
                case 3: updateStudent(); break;
                case 4: searchById(); break;
                case 5: searchByName(); break;
                case 6: sortByGpa(); break;
                case 7: students.display(); break;
                case 8: undoLastAction(); break;
                case 9: undoStack.display(); break;
                case 0: System.out.println("Goodbye!"); break;
            }
            System.out.println();
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("===== STUDENT RECORD SYSTEM =====");
        System.out.println("1. Add student");
        System.out.println("2. Delete student");
        System.out.println("3. Update student");
        System.out.println("4. Search by ID (linear search)");
        System.out.println("5. Search by name (linear search)");
        System.out.println("6. Sort by GPA (bubble sort)");
        System.out.println("7. Display all students");
        System.out.println("8. Undo last delete/update/add");
        System.out.println("9. View undo history (stack)");
        System.out.println("0. Exit");
    }

    // ---------------- Features ----------------

    private static void addStudent() {
        int id = readInt("Enter ID: ", 1, Integer.MAX_VALUE);
        if (students.linearSearchById(id) != null) {
            System.out.println("A student with this ID already exists.");
            return;
        }
        String name = readName("Enter name: ");
        double gpa = readGpa("Enter GPA (0.0 - 4.0): ");

        Student s = new Student(id, name, gpa);
        students.addLast(s);
        undoStack.push(new UndoAction(UndoAction.Type.ADD, s.copy()));
        System.out.println("Student added.");
    }

    private static void deleteStudent() {
        if (students.isEmpty()) {
            System.out.println("No records to delete.");
            return;
        }
        int id = readInt("Enter ID to delete: ", 1, Integer.MAX_VALUE);
        Student removed = students.deleteById(id);
        if (removed == null) {
            System.out.println("Student not found.");
        } else {
            undoStack.push(new UndoAction(UndoAction.Type.DELETE, removed));
            System.out.println("Deleted: " + removed);
        }
    }

    private static void updateStudent() {
        int id = readInt("Enter ID to update: ", 1, Integer.MAX_VALUE);
        Student s = students.linearSearchById(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        Student oldValues = s.copy();   // snapshot for undo
        String name = readName("Enter new name: ");
        double gpa = readGpa("Enter new GPA (0.0 - 4.0): ");
        s.setName(name);
        s.setGpa(gpa);
        undoStack.push(new UndoAction(UndoAction.Type.UPDATE, oldValues));
        System.out.println("Updated: " + s);
    }

    private static void searchById() {
        int id = readInt("Enter ID to search: ", 1, Integer.MAX_VALUE);
        Student s = students.linearSearchById(id);
        System.out.println(s == null ? "Student not found." : "Found: " + s);
    }

    private static void searchByName() {
        String keyword = readNonEmpty("Enter name or part of name: ");
        StudentLinkedList results = students.linearSearchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("No matching students.");
        } else {
            System.out.println("Matches (" + results.getSize() + "):");
            results.display();
        }
    }

    private static void sortByGpa() {
        if (students.getSize() < 2) {
            System.out.println("Need at least 2 records to sort.");
            return;
        }
        int order = readInt("1 = Highest GPA first, 2 = Lowest GPA first: ", 1, 2);
        students.bubbleSortByGpa(order == 1);
        System.out.println("Sorted:");
        students.display();
    }

    /** Pops the stack and reverses the action. */
    private static void undoLastAction() {
        UndoAction action = undoStack.pop();
        if (action == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        Student snap = action.getSnapshot();
        switch (action.getType()) {
            case ADD:
                students.deleteById(snap.getId());
                System.out.println("Undid ADD: removed " + snap.getName());
                break;
            case DELETE:
                students.addLast(snap);
                System.out.println("Undid DELETE: restored " + snap.getName());
                break;
            case UPDATE:
                Student current = students.linearSearchById(snap.getId());
                if (current != null) {
                    current.setName(snap.getName());
                    current.setGpa(snap.getGpa());
                    System.out.println("Undid UPDATE: " + current);
                }
                break;
        }
    }

    // ---------------- Input validation helpers ----------------

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException e) {
                // fall through to the error message
            }
            System.out.println("Invalid input. Enter a whole number between " + min
                    + " and " + (max == Integer.MAX_VALUE ? "max" : String.valueOf(max)) + ".");
        }
    }

    private static double readGpa(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(line);
                if (value >= 0.0 && value <= 4.0) return value;
            } catch (NumberFormatException e) {
                // fall through
            }
            System.out.println("Invalid GPA. Enter a number between 0.0 and 4.0.");
        }
    }

    private static String readName(String prompt) {
        while (true) {
            String name = readNonEmpty(prompt);
            if (name.matches("[A-Za-z .'-]+")) return name;
            System.out.println("Name may only contain letters, spaces, '.', '-' and apostrophes.");
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("Input cannot be empty.");
        }
    }
}
