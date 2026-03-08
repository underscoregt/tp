package cpp.model.assignment;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.model.assignment.exceptions.AssignmentNotFoundException;
import cpp.model.assignment.exceptions.AssignmentNotSubmittedException;
import cpp.model.assignment.exceptions.ContactNotAssignedAssignmentException;
import cpp.testutil.Assert;

public class AssignmentManagerTest {

    @Test
    public void allocate_andGetAssignments_success() {
        AssignmentManager manager = new AssignmentManager();
        String assignmentId = "a1";
        String contactId = "c1";

        manager.allocate(assignmentId, contactId);

        Set<ContactAssignment> contacts = manager.getContactsForAssignment(assignmentId);
        Assertions.assertEquals(1, contacts.size());
        ContactAssignment ca = contacts.iterator().next();
        Assertions.assertEquals(assignmentId, ca.getAssignmentId());
        Assertions.assertEquals(contactId, ca.getContactId());

        Set<ContactAssignment> assignments = manager.getAssignmentsForContact(contactId);
        Assertions.assertEquals(1, assignments.size());
        Assertions.assertEquals(ca, assignments.iterator().next());
    }

    @Test
    public void unallocate_existingAndNonexistent_behaviour() {
        AssignmentManager manager = new AssignmentManager();
        String assignmentId = "a1";
        String contactId = "c1";
        manager.allocate(assignmentId, contactId);

        // unallocate existing assignment-contact pair
        manager.unallocate(assignmentId, contactId);
        Assertions.assertTrue(manager.getContactsForAssignment(assignmentId).isEmpty());
        Assertions.assertTrue(manager.getAssignmentsForContact(contactId).isEmpty());

        // unallocate non-existent assignment -> throws
        Assert.assertThrows(AssignmentNotFoundException.class, () -> manager.unallocate("no", "c1"));
    }

    @Test
    public void unallocate_notAssignedContact_noExceptionAndOriginalRemains() {
        AssignmentManager manager = new AssignmentManager();
        String assignmentId = "a1";
        String contactId = "c1";
        manager.allocate(assignmentId, contactId);

        // unallocate for a different contact; should not remove the existing allocation
        manager.unallocate(assignmentId, "other");
        Assertions.assertFalse(manager.getContactsForAssignment(assignmentId).isEmpty());
    }

    @Test
    public void submitAndGrade_success_andErrors() {
        AssignmentManager manager = new AssignmentManager();
        String assignmentId = "a1";
        String contactId = "c1";

        manager.allocate(assignmentId, contactId);

        // submit
        manager.submit(assignmentId, contactId);
        ContactAssignment ca = manager.getContactsForAssignment(assignmentId).iterator().next();
        Assertions.assertTrue(ca.isSubmitted());

        // grade after submission
        manager.grade(assignmentId, contactId, 88);
        ca = manager.getContactsForAssignment(assignmentId).iterator().next();
        Assertions.assertTrue(ca.isGraded());
        Assertions.assertEquals(88, ca.getScore());

        // grading without submission should throw
        AssignmentManager manager2 = new AssignmentManager();
        manager2.allocate("a2", "c2");
        Assert.assertThrows(AssignmentNotSubmittedException.class, () -> manager2.grade("a2", "c2", 50));

        // submit on unknown assignment -> throws AssignmentNotFoundException
        Assert.assertThrows(AssignmentNotFoundException.class, () -> manager.submit("no", "c1"));

        // grade on assignment that exists but contact not assigned ->
        // ContactNotAssignedAssignmentException
        manager.allocate("a3", "someone");
        Assert.assertThrows(ContactNotAssignedAssignmentException.class, () -> manager.grade("a3", "missing", 10));
    }

}
