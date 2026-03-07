package cpp.model.assignment.exceptions;

/**
 * Signals that an operation that would result in duplicate assignments was
 * attempted.
 */
public class DuplicateAssignmentException extends RuntimeException {
    public DuplicateAssignmentException() {
        super("Operation would result in duplicate assignments");
    }
}
