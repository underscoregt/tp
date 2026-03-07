package cpp.model.assignment.exceptions;

/**
 * Signals that an operation that requires an assignment to be submitted was
 * attempted on an assignment that has not been submitted.
 */
public class AssignmentNotSubmittedException extends RuntimeException {
    public AssignmentNotSubmittedException(String message) {
        super(message);
    }
}
