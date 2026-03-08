package cpp.model.assignment.exceptions;

/**
 * Signals that the operation will result in duplicate contact assignment.
 */
public class DuplicateContactAssignmentException extends RuntimeException {
    public DuplicateContactAssignmentException() {
        super("Operation would result in duplicate contact assignment");
    }
}
