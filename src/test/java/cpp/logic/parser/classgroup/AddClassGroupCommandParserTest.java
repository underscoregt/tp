package cpp.logic.parser.classgroup;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import cpp.logic.Messages;
import cpp.logic.commands.CommandTestUtil;
import cpp.logic.commands.classgroup.AddClassGroupCommand;
import cpp.logic.parser.CommandParserTestUtil;
import cpp.model.classgroup.ClassGroup;
import cpp.model.classgroup.ClassGroupName;
import cpp.testutil.ClassGroupBuilder;
import cpp.testutil.ClassGroupUtil;
import cpp.testutil.TypicalClassGroups;

public class AddClassGroupCommandParserTest {

    private AddClassGroupCommandParser parser = new AddClassGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        ClassGroup expectedClassGroup = new ClassGroupBuilder(TypicalClassGroups.CLASS_GROUP_ONE).build();
        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(this.parser,
                CommandTestUtil.PREAMBLE_WHITESPACE + ClassGroupUtil.getClassGroupDetails(expectedClassGroup),
                new AddClassGroupCommand(expectedClassGroup, new ArrayList<>()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddClassGroupCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(this.parser,
                TypicalClassGroups.CLASS_GROUP_ONE.getName().fullName,
                expectedMessage);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(this.parser,
                CommandTestUtil.PREAMBLE_NON_EMPTY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(this.parser,
                CommandTestUtil.INVALID_CLASS_NAME_DESC,
                ClassGroupName.MESSAGE_CONSTRAINTS);
    }

}
