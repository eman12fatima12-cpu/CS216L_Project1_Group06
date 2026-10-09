/**
 * Represents one student record.
 */
public class Student {
    private int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }

    public void setName(String name) { this.name = name; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    /** Returns an independent copy (used so undo snapshots are not affected by later edits). */
    public Student copy() {
        return new Student(id, name, gpa);
    }

    @Override
    public String toString() {
        return String.format("ID: %-6d | Name: %-20s | GPA: %.2f", id, name, gpa);
    }
}
