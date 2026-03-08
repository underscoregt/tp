package cpp.logic.commands.assignment;

import java.util.List;
import java.util.Objects;

import cpp.commons.core.index.Index;
import cpp.commons.util.ToStringBuilder;
import cpp.logic.Messages;
import cpp.logic.commands.Command;
import cpp.logic.commands.CommandResult;
import cpp.logic.commands.exceptions.CommandException;
import cpp.model.Model;
import cpp.model.assignment.Assignment;

/**
 * Deletes an assignment identified using its displayed index from the address book.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = DeleteAssignmentCommand.COMMAND_WORD
            + ": Deletes the assignment identified by the index number used in the displayed assignment list.\n"
            + "Parameters: ass/INDEX (must be a positive integer)\n"
            + "Example: " + DeleteAssignmentCommand.COMMAND_WORD + " ass/1";

    public static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Deleted Assignment: %1$s";

    private final Index targetIndex;

    public DeleteAssignmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (this.targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToDelete = lastShownList.get(this.targetIndex.getZeroBased());
        model.deleteAssignment(assignmentToDelete);
        return new CommandResult(
                String.format(DeleteAssignmentCommand.MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                        Messages.format(assignmentToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteAssignmentCommand)) {
            return false;
        }

        DeleteAssignmentCommand otherDeleteAssignmentCommand = (DeleteAssignmentCommand) other;
        return this.targetIndex.equals(otherDeleteAssignmentCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", this.targetIndex)
                .toString();
    }
}
