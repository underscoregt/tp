package cpp.model;

import java.util.List;
import java.util.Objects;

import cpp.commons.util.ToStringBuilder;
import cpp.model.assignment.Assignment;
import cpp.model.assignment.UniqueAssignmentList;
import cpp.model.classgroup.ClassGroup;
import cpp.model.classgroup.UniqueClassGroupList;
import cpp.model.contact.Contact;
import cpp.model.contact.UniqueContactList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueContactList contacts;
    private final UniqueAssignmentList assignments;
    private final UniqueClassGroupList classGroups;

    /*
     * The 'unusual' code block below is a non-static initialization block,
     * sometimes used to avoid duplication
     * between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other
     * ways to avoid duplication among constructors.
     */
    {
        this.contacts = new UniqueContactList();
        this.assignments = new UniqueAssignmentList();
        this.classGroups = new UniqueClassGroupList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Contacts in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        this.resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    /**
     * Replaces the contents of the assignment list with {@code assignments}.
     * {@code assignments} must not contain duplicate assignments.
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments.setAssignments(assignments);
    }

    /**
     * Replaces the contents of the class group list with {@code classGroups}
     * {@code classGroups} must not contain duplicate class groups
     */
    public void setClassGroups(List<ClassGroup> classGroups) {
        this.classGroups.setClassGroups(classGroups);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        Objects.requireNonNull(newData);

        this.setContacts(newData.getContactList());
        this.setAssignments(newData.getAssignmentList());
        this.setClassGroups(newData.getClassGroupList());
    }

    //// contact-level operations

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in
     * the address book.
     */
    public boolean hasContact(Contact contact) {
        Objects.requireNonNull(contact);
        return this.contacts.contains(contact);
    }

    /**
     * Adds a contact to the address book.
     * The contact must not already exist in the address book.
     */
    public void addContact(Contact p) {
        this.contacts.add(p);
    }

    /**
     * Replaces the given contact {@code target} in the list with
     * {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another
     * existing contact in the address book.
     */
    public void setContact(Contact target, Contact editedContact) {
        Objects.requireNonNull(editedContact);
        this.contacts.setContact(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeContact(Contact key) {
        this.contacts.remove(key);
    }

    //// assignment level operations
    /**
     * Returns true if an assignment with the same identity as {@code assignment}
     * exists in the address book.
     */
    public boolean hasAssignment(Assignment assignment) {
        Objects.requireNonNull(assignment);
        return this.assignments.contains(assignment);
    }

    /**
     * Adds an assignment to the address book.
     * The assignment must not already exist in the address book.
     */
    public void addAssignment(Assignment assignment) {
        Objects.requireNonNull(assignment);
        this.assignments.add(assignment);
    }

    /**
     * Returns true if a class group with the same identity as {@code classGroup}
     * exists in the address book.
     */
    public boolean hasClassGroup(ClassGroup classGroup) {
        Objects.requireNonNull(classGroup);
        return this.classGroups.contains(classGroup);
    }

    /**
     * Adds a class group to the address book.
     * The class group must not already exist in the address book.
     */
    public void addClassGroup(ClassGroup classGroup) {
        Objects.requireNonNull(classGroup);
        this.classGroups.add(classGroup);
    }

    /**
     * Replaces the given class group {@code target} in the list with
     * {@code editedClassGroup}.
     *
     * {@code target} must exist in the address book.
     * The class group identity of {@code editedClassGroup} must not be the same as
     * another existing class group in the address book.
     */
    public void setClassGroup(ClassGroup target, ClassGroup editedClassGroup) {
        Objects.requireNonNull(editedClassGroup);
        this.classGroups.setClassGroup(target, editedClassGroup);
    }

    /**
     * Removes ClassGroup {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClassGroup(ClassGroup key) {
        this.classGroups.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("contacts", this.contacts)
                .add("assignments", this.assignments)
                .add("classGroups", this.classGroups)
                .toString();
    }

    @Override
    public ObservableList<Contact> getContactList() {
        return this.contacts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Assignment> getAssignmentList() {
        return this.assignments.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<ClassGroup> getClassGroupList() {
        return this.classGroups.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return this.contacts.equals(otherAddressBook.contacts) && this.assignments.equals(otherAddressBook.assignments)
                && this.classGroups.equals(otherAddressBook.classGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.contacts, this.assignments, this.classGroups);
    }
}
