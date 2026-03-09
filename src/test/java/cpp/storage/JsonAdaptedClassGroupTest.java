package cpp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.commons.exceptions.IllegalValueException;
import cpp.model.classgroup.ClassGroupName;
import cpp.testutil.Assert;
import cpp.testutil.TypicalClassGroups;

public class JsonAdaptedClassGroupTest {

    private static final String INVALID_NAME = "Cl@ss 1";

    private static final String VALID_ID = TypicalClassGroups.CLASS_GROUP_ONE.getId();
    private static final String VALID_NAME = TypicalClassGroups.CLASS_GROUP_ONE.getName().fullName;

    @Test
    public void toModelType_validClassGroupDetails_returnsClassGroup() throws Exception {
        JsonAdaptedClassGroup json = new JsonAdaptedClassGroup(TypicalClassGroups.CLASS_GROUP_ONE);
        Assertions.assertEquals(TypicalClassGroups.CLASS_GROUP_ONE, json.toModelType());
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedClassGroup json = new JsonAdaptedClassGroup(null, JsonAdaptedClassGroupTest.VALID_NAME);
        String expectedMessage = "A class group's id field is missing.";
        Assert.assertThrows(IllegalValueException.class, expectedMessage, json::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedClassGroup json = new JsonAdaptedClassGroup(JsonAdaptedClassGroupTest.VALID_ID,
                JsonAdaptedClassGroupTest.INVALID_NAME);
        String expectedMessage = ClassGroupName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, json::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedClassGroup json = new JsonAdaptedClassGroup(JsonAdaptedClassGroupTest.VALID_ID, null);
        String expectedMessage = String.format(JsonAdaptedClassGroup.MISSING_FIELD_MESSAGE_FORMAT,
                ClassGroupName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, json::toModelType);
    }

}
