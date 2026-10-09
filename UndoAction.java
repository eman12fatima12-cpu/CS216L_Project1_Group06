/**
 * One undoable action. Stores what is needed to reverse it.
 */
public class UndoAction {

    public enum Type { ADD, DELETE, UPDATE }

    private final Type type;
    private final Student snapshot; // ADD: the added student; DELETE: the removed student; UPDATE: the OLD values

    public UndoAction(Type type, Student snapshot) {
        this.type = type;
        this.snapshot = snapshot;
    }

    public Type getType() { return type; }
    public Student getSnapshot() { return snapshot; }

    @Override
    public String toString() {
        return type + " -> " + snapshot.getName() + " (ID " + snapshot.getId() + ")";
    }
}
