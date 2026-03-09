package cpp.model.classgroup.exceptions;

/**
 * Signals that the operation is unable to find the specified class group.
 */
public class ClassGroupNotFoundException extends RuntimeException {
    public ClassGroupNotFoundException() {
        super("Class group not found");
    }
}
