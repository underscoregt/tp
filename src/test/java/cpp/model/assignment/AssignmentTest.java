package cpp.model.assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.testutil.AssignmentBuilder;

public class AssignmentTest {

    @Test
    public void equals_differentType_returnsFalse() {
        Assignment a = new AssignmentBuilder().build();
        String s = "Not an assignment";
        Assertions.assertNotEquals(a, s);
    }

    @Test
    public void equals_sameIdNameDeadline_returnsTrue() {
        Assignment a1 = new AssignmentBuilder().withId("id1").withName("Assignment X").withDeadline("13-12-2020 10:00")
                .build();
        Assignment a2 = new AssignmentBuilder().withId("id1").withName("Assignment X").withDeadline("13-12-2020 10:00")
                .build();
        Assertions.assertEquals(a1, a2);
    }

    @Test
    public void equals_differentId_returnsFalse() {
        Assignment a1 = new AssignmentBuilder().withId("id1").build();
        Assignment a2 = new AssignmentBuilder().withId("id2").build();
        Assertions.assertNotEquals(a1, a2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        Assignment a1 = new AssignmentBuilder().withId("id1").withName("Assignment X").build();
        Assignment a2 = new AssignmentBuilder().withId("id1").withName("Assignment Y").build();
        Assertions.assertNotEquals(a1, a2);
    }

    @Test
    public void equals_differentDeadline_returnsFalse() {
        Assignment a1 = new AssignmentBuilder().withId("id1").withDeadline("13-12-2020 10:00").build();
        Assignment a2 = new AssignmentBuilder().withId("id1").withDeadline("14-12-2020 10:00").build();
        Assertions.assertNotEquals(a1, a2);
    }

    @Test
    public void hashCode_sameIdNameDeadline_equal() {
        Assignment a1 = new AssignmentBuilder().withId("id1").withName("Same").withDeadline("13-12-2020 10:00")
                .build();
        Assignment a2 = new AssignmentBuilder().withId("id1").withName("Same").withDeadline("13-12-2020 10:00")
                .build();
        Assertions.assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void hashCode_differentName_notEqual() {
        Assignment a1 = new AssignmentBuilder().withId("id1").withName("Same").withDeadline("13-12-2020 10:00")
                .build();
        Assignment a2 = new AssignmentBuilder().withId("id1").withName("Sam3").withDeadline("13-12-2020 10:00")
                .build();
        Assertions.assertNotEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void toString_containsNameAndDeadline() {
        Assignment a = new AssignmentBuilder().withId("id1").withName("ToStringTest").withDeadline("13-12-2020 10:00")
                .build();
        String s = a.toString();
        Assertions.assertTrue(s.contains(a.getName().fullName));
        Assertions.assertTrue(s.contains(a.getDeadline().toString()));
    }

}
