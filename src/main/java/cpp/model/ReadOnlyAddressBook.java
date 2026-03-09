package cpp.model;

import cpp.model.assignment.Assignment;
import cpp.model.assignment.ContactAssignment;
import cpp.model.classgroup.ClassGroup;
import cpp.model.contact.Contact;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the contacts list.
     * This list will not contain any duplicate contacts.
     */
    ObservableList<Contact> getContactList();

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

    /**
     * Returns an unmodifiable view of the class groups list.
     * This list will not contain any duplicate class groups.
     */
    ObservableList<ClassGroup> getClassGroupList();

}
