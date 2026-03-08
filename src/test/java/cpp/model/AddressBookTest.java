package cpp.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.logic.commands.CommandTestUtil;
import cpp.model.assignment.Assignment;
import cpp.model.assignment.ContactAssignment;
import cpp.model.assignment.Name;
import cpp.model.person.Person;
import cpp.model.person.exceptions.DuplicatePersonException;
import cpp.testutil.Assert;
import cpp.testutil.PersonBuilder;
import cpp.testutil.TypicalPersons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        Assertions.assertEquals(Collections.emptyList(), this.addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> this.addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = TypicalPersons.getTypicalAddressBook();
        this.addressBook.resetData(newData);
        Assertions.assertEquals(newData, this.addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(TypicalPersons.ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        Assert.assertThrows(DuplicatePersonException.class, () -> this.addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> this.addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        Assertions.assertFalse(this.addressBook.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        this.addressBook.addPerson(TypicalPersons.ALICE);
        Assertions.assertTrue(this.addressBook.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        this.addressBook.addPerson(TypicalPersons.ALICE);
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        Assertions.assertTrue(this.addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class,
                () -> this.addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + this.addressBook.getPersonList()
                + ", assignments=" + this.addressBook.getAssignmentList() + "}";
        Assertions.assertEquals(expected, this.addressBook.toString());
    }

    @Test
    public void hasAssignment_nullAssignment_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class,
                () -> this.addressBook.hasAssignment(null));
    }

    @Test
    public void hasAssignment_assignmentNotInAddressBook_returnsFalse() {
        Assignment assignment = new Assignment(new Name("Assignment 1"),
                LocalDateTime.of(2020, 1, 1, 10, 0));
        Assertions.assertFalse(this.addressBook.hasAssignment(assignment));
    }

    @Test
    public void hasAssignment_assignmentInAddressBook_returnsTrue() {
        Assignment assignment = new Assignment(new Name("Assignment 1"),
                LocalDateTime.of(2020, 1, 1, 10, 0));
        this.addressBook.addAssignment(assignment);
        Assertions.assertTrue(this.addressBook.hasAssignment(assignment));
    }

    @Test
    public void hasAssignment_assignmentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        Assignment assignment = new Assignment(new Name("Assignment 1"),
                LocalDateTime.of(2020, 1, 1, 10, 0));
        this.addressBook.addAssignment(assignment);
        // Same name (identity), different deadline
        Assignment editedAssignment = new Assignment(new Name("Assignment 1"),
                LocalDateTime.of(2021, 1, 1, 10, 0));
        Assertions.assertTrue(this.addressBook.hasAssignment(editedAssignment));
    }

    @Test
    public void addAssignment_nullAssignment_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class,
                () -> this.addressBook.addAssignment(null));
    }

    @Test
    public void addAssignment_validAssignment_addSuccessful() {
        Assignment assignment = new Assignment(new Name("Assignment 1"),
                LocalDateTime.of(2020, 1, 1, 10, 0));
        this.addressBook.addAssignment(assignment);
        Assertions.assertTrue(this.addressBook.hasAssignment(assignment));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface
     * constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return this.persons;
        }

        @Override
        public ObservableList<Assignment> getAssignmentList() {
            throw new UnsupportedOperationException("Method 'getAssignmentList' should not be called.");
        }

        @Override
        public ObservableList<ContactAssignment> getContactAssignmentList() {
            throw new UnsupportedOperationException("Method 'getContactAssignmentList' should not be called.");
        }
    }

}
