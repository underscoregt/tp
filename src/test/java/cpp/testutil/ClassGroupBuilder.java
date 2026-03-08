package cpp.testutil;

import cpp.model.classgroup.ClassGroup;
import cpp.model.classgroup.ClassGroupName;

/**
 * A utility class to help with building ClassGroup objects.
 */
public class ClassGroupBuilder {

    public static final String DEFAULT_ID = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
    public static final String DEFAULT_NAME = "CS2103T10";

    private String id;
    private ClassGroupName name;

    /**
     * Creates a {@code ClassGroupBuilder} with the default details.
     */
    public ClassGroupBuilder() {
        this.id = ClassGroupBuilder.DEFAULT_ID;
        this.name = new ClassGroupName(ClassGroupBuilder.DEFAULT_NAME);
    }

    /**
     * Initializes the ClassGroupBuilder with the data of {@code classGroupToCopy}.
     */
    public ClassGroupBuilder(ClassGroup classGroupToCopy) {
        this.id = classGroupToCopy.getId();
        this.name = classGroupToCopy.getName();
    }

    /**
     * Sets the {@code id} of the {@code ClassGroup} that we are building.
     */
    public ClassGroupBuilder withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code name} of the {@code ClassGroup} that we are building.
     */
    public ClassGroupBuilder withName(String name) {
        this.name = new ClassGroupName(name);
        return this;
    }

    public ClassGroup build() {
        return new ClassGroup(this.id, this.name);
    }
}
