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
 * ListClassCommand.
 */
public class ListClassTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());
        this.expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listClasses_returnsClassesMessage() {
        CommandResult expectedResult = new CommandResult(ListCommand.MESSAGE_CLASSES,
                CommandResult.ListView.CLASSGROUPS);
        CommandTestUtil.assertCommandSuccess(new ListClassCommand(), this.model, expectedResult,
                this.expectedModel);
    }

    @Test
    public void execute_returnsCorrectListView() throws CommandException {
        ListClassCommand command = new ListClassCommand();
        CommandResult result = command.execute(this.model);
        Assertions.assertEquals(CommandResult.ListView.CLASSGROUPS, result.getListView());
    }

    @Test
    public void execute_returnsCorrectMessage() throws CommandException {
        ListClassCommand command = new ListClassCommand();
        CommandResult result = command.execute(this.model);
        Assertions.assertEquals(ListCommand.MESSAGE_CLASSES, result.getFeedbackToUser());
    }

    @Test
    public void execute_commandSucceedsWithEmptyClassList() throws CommandException {
        ListClassCommand command = new ListClassCommand();
        CommandResult result = command.execute(this.model);
        Assertions.assertEquals(ListCommand.MESSAGE_CLASSES, result.getFeedbackToUser());
        Assertions.assertEquals(CommandResult.ListView.CLASSGROUPS, result.getListView());
    }

    @Test
    public void execute_multipleListClassCommands_displaySameList() throws CommandException {
        ListClassCommand command1 = new ListClassCommand();
        ListClassCommand command2 = new ListClassCommand();
        CommandResult result1 = command1.execute(this.model);
        CommandResult result2 = command2.execute(this.model);
        Assertions.assertEquals(result1.getFeedbackToUser(), result2.getFeedbackToUser());
        Assertions.assertEquals(result1.getListView(), result2.getListView());
    }

    @Test
    public void execute_modelNotModified() throws CommandException {
        Model originalModel = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());
        ListClassCommand command = new ListClassCommand();
        command.execute(this.model);
        Assertions.assertEquals(originalModel.getAddressBook(), this.model.getAddressBook());
    }

    @Test
    public void execute_multipleExecutions_sameResult() throws CommandException {
        ListClassCommand command1 = new ListClassCommand();
        ListClassCommand command2 = new ListClassCommand();
        CommandResult result1 = command1.execute(this.model);
        CommandResult result2 = command2.execute(this.model);
        Assertions.assertEquals(result1, result2);
    }

    @Test
    public void equals_sameCommand_returnsTrue() {
        ListClassCommand command1 = new ListClassCommand();
        ListClassCommand command2 = new ListClassCommand();
        Assertions.assertEquals(command1, command2);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        ListClassCommand command = new ListClassCommand();
        Assertions.assertEquals(command, command);
    }

    @Test
    public void equals_differentCommandType_returnsFalse() {
        ListClassCommand command1 = new ListClassCommand();
        ListContactCommand command2 = new ListContactCommand();
        Assertions.assertNotEquals(command1, command2);
    }

    @Test
    public void equals_nullCommand_returnsFalse() {
        ListClassCommand command = new ListClassCommand();
        Assertions.assertNotEquals(command, null);
    }

    @Test
    public void equals_nonCommandObject_returnsFalse() {
        ListClassCommand command = new ListClassCommand();
        Assertions.assertNotEquals(command, new Object());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        ListClassCommand command = new ListClassCommand();
        String result = command.toString();
        Assertions.assertNotNull(result);
    }
}
