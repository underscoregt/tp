package cpp.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpp.model.classgroup.ClassGroup;

/**
 * A utility class containing a list of {@code ClassGroup} objects to be used in
 * tests.
 */
public class TypicalClassGroups {

    public static final ClassGroup CLASS_GROUP_ONE = new ClassGroupBuilder()
            .withId("aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb")
            .withName("CS2103T10")
            .build();

    public static final ClassGroup CLASS_GROUP_TWO = new ClassGroupBuilder()
            .withId("aaaaaaaa-1111-2222-3333-bbbbbbbbbbbc")
            .withName("CS2101T10")
            .build();

    public static final ClassGroup CLASS_GROUP_THREE = new ClassGroupBuilder()
            .withId("aaaaaaaa-1111-2222-3333-bbbbbbbbbbb3")
            .withName("CS3230T3")
            .build();

    public static List<ClassGroup> getTypicalClassGroups() {
        return new ArrayList<>(Arrays.asList(TypicalClassGroups.CLASS_GROUP_ONE));
    }
}
