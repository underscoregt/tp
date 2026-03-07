package cpp.model.assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.testutil.Assert;

public class ContactAssignmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ContactAssignment(null, "c1"));
        Assert.assertThrows(NullPointerException.class, () -> new ContactAssignment("a1", null));
    }

    @Test
    public void stateTransitions_and_equality() {
        ContactAssignment ca = new ContactAssignment("a1", "c1");
        Assertions.assertFalse(ca.isSubmitted());
        Assertions.assertFalse(ca.isGraded());
        Assertions.assertEquals(0, ca.getScore());

        ca.markSubmitted();
        Assertions.assertTrue(ca.isSubmitted());
        ca.markUnsubmitted();
        Assertions.assertFalse(ca.isSubmitted());

        ca.grade(95);
        Assertions.assertTrue(ca.isGraded());
        Assertions.assertEquals(95, ca.getScore());
        ca.ungrade();
        Assertions.assertFalse(ca.isGraded());
        Assertions.assertEquals(0, ca.getScore());

        ContactAssignment other = new ContactAssignment("a1", "c1");
        Assertions.assertEquals(ca.getAssignmentId(), other.getAssignmentId());
        Assertions.assertEquals(ca.getContactId(), other.getContactId());
        Assertions.assertEquals(other, new ContactAssignment("a1", "c1"));
    }

}
