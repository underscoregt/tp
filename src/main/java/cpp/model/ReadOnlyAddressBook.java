package cpp.model;

import cpp.model.assignment.Assignment;
import cpp.model.assignment.ContactAssignment;
import cpp.model.person.Person;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the assignments list.
     * This list will not contain any duplicate assignments.
     */
    ObservableList<Assignment> getAssignmentList();

    /**
     * Returns an unmodifiable view of the contact assignments list.
     * This list will not contain any duplicate contact assignments.
     */
    ObservableList<ContactAssignment> getContactAssignmentList();

}
