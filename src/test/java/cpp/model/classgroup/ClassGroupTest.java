package cpp.model.classgroup;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.testutil.ClassGroupBuilder;

public class ClassGroupTest {

    @Test
    public void equals_differentType_returnsFalse() {
        ClassGroup cg = new ClassGroupBuilder().build();
        String s = "Not a class group";
        Assertions.assertNotEquals(cg, s);
    }

    @Test
    public void equals_sameIdAndName_returnsTrue() {
        ClassGroup cg1 = new ClassGroupBuilder().withId("id1").withName("CS2103T10").build();
        ClassGroup cg2 = new ClassGroupBuilder().withId("id1").withName("CS2103T10").build();
        Assertions.assertEquals(cg1, cg2);
    }

    @Test
    public void equals_differentId_returnsFalse() {
        ClassGroup cg1 = new ClassGroupBuilder().withId("id1").build();
        ClassGroup cg2 = new ClassGroupBuilder().withId("id2").build();
        Assertions.assertNotEquals(cg1, cg2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        ClassGroup cg1 = new ClassGroupBuilder().withId("id1").withName("CS2103T10").build();
        ClassGroup cg2 = new ClassGroupBuilder().withId("id1").withName("CS2101T10").build();
        Assertions.assertNotEquals(cg1, cg2);
    }

    @Test
    public void hashCode_sameIdAndName_equal() {
        ClassGroup cg1 = new ClassGroupBuilder().withId("id1").withName("CS2103T10").build();
        ClassGroup cg2 = new ClassGroupBuilder().withId("id1").withName("CS2103T10").build();
        Assertions.assertEquals(cg1.hashCode(), cg2.hashCode());
    }

    @Test
    public void hashCode_differentName_notEqual() {
        ClassGroup cg1 = new ClassGroupBuilder().withId("id1").withName("CS2103T10").build();
        ClassGroup cg2 = new ClassGroupBuilder().withId("id1").withName("CS2101T10").build();
        Assertions.assertNotEquals(cg1.hashCode(), cg2.hashCode());
    }

    @Test
    public void toString_containsName() {
        ClassGroup cg = new ClassGroupBuilder().withId("id1").withName("CS2103T10").build();
        String s = cg.toString();
        Assertions.assertTrue(s.contains(cg.getName().fullName));
    }

}
