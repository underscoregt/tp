package cpp.model.assignment.exceptions;

/**
 * Signals that an operation that requires a contact to be assigned an
 * assignment was attempted on a contact that is not assigned the assignment.
 */
public class ContactNotAssignedAssignmentException extends RuntimeException {

    public ContactNotAssignedAssignmentException(String message) {
        super(message);
    }

}
