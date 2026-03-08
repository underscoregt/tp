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
    public void constructor_validAssignment_success() {
        ContactAssignment ca = new ContactAssignment("a1", "c1");
        Assertions.assertEquals("a1", ca.getAssignmentId());
        Assertions.assertEquals("c1", ca.getContactId());
        Assertions.assertFalse(ca.isSubmitted());
        Assertions.assertFalse(ca.isGraded());
        Assertions.assertEquals(0, ca.getScore());
    }

    @Test
    public void constructor_validAssignmentWithState_success() {
        ContactAssignment ca = new ContactAssignment("a1", "c1", true, true, 85);
        Assertions.assertEquals("a1", ca.getAssignmentId());
        Assertions.assertEquals("c1", ca.getContactId());
        Assertions.assertTrue(ca.isSubmitted());
        Assertions.assertTrue(ca.isGraded());
        Assertions.assertEquals(85, ca.getScore());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ContactAssignment ca1 = new ContactAssignment("a1", "c1");
        ContactAssignment ca2 = new ContactAssignment("a1", "c1");
        Assertions.assertEquals(ca1, ca2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        ContactAssignment ca1 = new ContactAssignment("a1", "c1");
        ContactAssignment ca2 = new ContactAssignment("a2", "c1");
        ContactAssignment ca3 = new ContactAssignment("a1", "c2");
        Assertions.assertNotEquals(ca1, ca2);
        Assertions.assertNotEquals(ca1, ca3);
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        ContactAssignment ca = new ContactAssignment("a1", "c1");
        Assertions.assertNotEquals(ca, null);
    }

    @Test
    public void toString_validContactAssignment_returnsString() {
        ContactAssignment ca = new ContactAssignment(
                "a1", "c1", true, true, 90);
        String expected = """
                ContactAssignment[assignmentId=a1, contactId=c1, submitted=true, graded=true, score=90]""";
        Assertions.assertEquals(expected, ca.toString());
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
