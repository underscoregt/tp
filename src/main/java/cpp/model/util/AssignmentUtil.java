package cpp.model.util;

import java.util.List;

import cpp.model.assignment.Assignment;
import cpp.model.assignment.AssignmentName;

/**
 * Utility class for Assignment related operations.
 */
public class AssignmentUtil {

    /**
     * Finds and returns the assignment with the given name from the list of
     * assignments. Returns null if no such assignment is found.
     *
     * @param assignments the list of assignments to search through
     * @param name        the name of the assignment to find
     * @return the assignment with the given name, or null if no such assignment is
     *         found
     */
    public static Assignment findAssignment(List<Assignment> assignments, AssignmentName name) {
        for (Assignment assignment : assignments) {
            if (assignment.getName().equals(name)) {
                return assignment;
            }
        }
        return null;
    }
}
