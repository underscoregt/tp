package cpp.model.classgroup.exceptions;

/**
 * Signals that an operation that would result in duplicate class groups was
 * attempted.
 */
public class DuplicateClassGroupException extends RuntimeException {
    public DuplicateClassGroupException() {
        super("Operation would result in duplicate class groups");
    }
}
