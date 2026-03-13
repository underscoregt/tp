package cpp.logic.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpp.logic.commands.exceptions.CommandException;
import cpp.model.Model;
import cpp.model.ModelManager;
import cpp.model.UserPrefs;
import cpp.testutil.TypicalContacts;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * ListAssignmentCommand.
 */
public class ListAssignmentTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());
        this.expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listAssignments_returnsAssignmentMessage() {
        CommandResult expectedResult = new CommandResult(ListCommand.MESSAGE_ASSIGNMENTS,
                CommandResult.ListView.ASSIGNMENTS);
        CommandTestUtil.assertCommandSuccess(new ListAssignmentCommand(), this.model, expectedResult,
                this.expectedModel);
    }

    @Test
    public void execute_returnsCorrectListView() throws CommandException {
        ListAssignmentCommand command = new ListAssignmentCommand();
        CommandResult result = command.execute(this.model);
        Assertions.assertEquals(CommandResult.ListView.ASSIGNMENTS, result.getListView());
    }

    @Test
    public void execute_returnsCorrectMessage() throws CommandException {
        ListAssignmentCommand command = new ListAssignmentCommand();
        CommandResult result = command.execute(this.model);
        Assertions.assertEquals(ListCommand.MESSAGE_ASSIGNMENTS, result.getFeedbackToUser());
    }

    @Test
    public void execute_commandSucceedsWithEmptyAssignmentList() throws CommandException {
        ListAssignmentCommand command = new ListAssignmentCommand();
        CommandResult result = command.execute(this.model);
        Assertions.assertEquals(ListCommand.MESSAGE_ASSIGNMENTS, result.getFeedbackToUser());
        Assertions.assertEquals(CommandResult.ListView.ASSIGNMENTS, result.getListView());
    }

    @Test
    public void execute_multipleListAssignmentCommands_displaySameList() throws CommandException {
        ListAssignmentCommand command1 = new ListAssignmentCommand();
        ListAssignmentCommand command2 = new ListAssignmentCommand();
        CommandResult result1 = command1.execute(this.model);
        CommandResult result2 = command2.execute(this.model);
        Assertions.assertEquals(result1.getFeedbackToUser(), result2.getFeedbackToUser());
        Assertions.assertEquals(result1.getListView(), result2.getListView());
    }

    @Test
    public void execute_modelNotModified() throws CommandException {
        Model originalModel = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());
        ListAssignmentCommand command = new ListAssignmentCommand();
        command.execute(this.model);
        Assertions.assertEquals(originalModel.getAddressBook(), this.model.getAddressBook());
    }

    @Test
    public void execute_multipleExecutions_sameResult() throws CommandException {
        ListAssignmentCommand command1 = new ListAssignmentCommand();
        ListAssignmentCommand command2 = new ListAssignmentCommand();
        CommandResult result1 = command1.execute(this.model);
        CommandResult result2 = command2.execute(this.model);
        Assertions.assertEquals(result1, result2);
    }

    @Test
    public void equals_sameCommand_returnsTrue() {
        ListAssignmentCommand command1 = new ListAssignmentCommand();
        ListAssignmentCommand command2 = new ListAssignmentCommand();
        Assertions.assertEquals(command1, command2);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        ListAssignmentCommand command = new ListAssignmentCommand();
        Assertions.assertEquals(command, command);
    }

    @Test
    public void equals_differentCommandType_returnsFalse() {
        ListAssignmentCommand command1 = new ListAssignmentCommand();
        ListContactCommand command2 = new ListContactCommand();
        Assertions.assertNotEquals(command1, command2);
    }

    @Test
    public void equals_nullCommand_returnsFalse() {
        ListAssignmentCommand command = new ListAssignmentCommand();
        Assertions.assertNotEquals(command, null);
    }

    @Test
    public void equals_nonCommandObject_returnsFalse() {
        ListAssignmentCommand command = new ListAssignmentCommand();
        Assertions.assertNotEquals(command, new Object());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        ListAssignmentCommand command = new ListAssignmentCommand();
        String result = command.toString();
        Assertions.assertNotNull(result);
    }
}
