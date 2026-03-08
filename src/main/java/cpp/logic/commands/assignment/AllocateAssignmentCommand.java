package cpp.logic.commands.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cpp.commons.core.index.Index;
import cpp.commons.util.ToStringBuilder;
import cpp.logic.Messages;
import cpp.logic.commands.Command;
import cpp.logic.commands.CommandResult;
import cpp.logic.commands.exceptions.CommandException;
import cpp.logic.parser.CliSyntax;
import cpp.model.Model;
import cpp.model.assignment.Assignment;
import cpp.model.assignment.Name;
import cpp.model.person.Person;

/**
 * Allocates an existing assignment to a person by their displayed indices.
 */
public class AllocateAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "allocass";

    public static final String MESSAGE_USAGE = AllocateAssignmentCommand.COMMAND_WORD
            + ": Allocates an assignment to a person. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "ASSIGNMENT NAME "
            + "[" + CliSyntax.PREFIX_CLASS + "CLASS NAME] "
            + "[" + CliSyntax.PREFIX_CONTACT + "CONTACT INDICES...]\n"
            + "Example: " + AllocateAssignmentCommand.COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Assignment 1 "
            + CliSyntax.PREFIX_CLASS + "CS2103T10 "
            + CliSyntax.PREFIX_CONTACT + "1 2 3";

    public static final String MESSAGE_SUCCESS = """
            Allocated assignment: %1$s to %2$s contacts.\nContacts allocated: %3$s
            """;
    public static final String MESSAGE_INVALID_ASSIGNMENT_NAME = "The assignment name provided is invalid";
    public static final String MESSAGE_ALLOCATION_FAILED = "No contacts were allocated the assignment.";

    private final Name assignmentName;
    private final List<Index> contactIndices;

    /**
     * Creates an AllocateAssignmentCommand with the specified assignment and
     * person indices.
     */
    public AllocateAssignmentCommand(Name assignmentName, List<Index> contactIndices) {
        Objects.requireNonNull(assignmentName);
        Objects.requireNonNull(contactIndices);
        this.assignmentName = assignmentName;
        this.contactIndices = new ArrayList<>(contactIndices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);

        List<Assignment> assignmentList = model.getAddressBook().getAssignmentList();

        Assignment assignmentToAllocate = Assignment.findAssignment(assignmentList, this.assignmentName);

        if (assignmentToAllocate == null) {
            throw new CommandException(AllocateAssignmentCommand.MESSAGE_INVALID_ASSIGNMENT_NAME);
        }

        List<Person> lastShownPersonList = model.getFilteredPersonList();

        return this.allocateToContacts(model, assignmentToAllocate, lastShownPersonList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AllocateAssignmentCommand)) {
            return false;
        }
        AllocateAssignmentCommand o = (AllocateAssignmentCommand) other;
        return this.assignmentName.equals(o.assignmentName) && this.contactIndices.equals(o.contactIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignmentName", this.assignmentName)
                .add("contactIndices", this.contactIndices)
                .toString();
    }

    private CommandResult allocateToContacts(Model model, Assignment assignmentToAllocate,
            List<Person> lastShownPersonList) throws CommandException {
        StringBuilder allocatedPersons = new StringBuilder();
        boolean anyAllocated = false;
        int allocatedCount = 0;

        for (Index idx : this.contactIndices) {
            if (idx.getZeroBased() >= lastShownPersonList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person person = lastShownPersonList.get(idx.getZeroBased());
            boolean allocated = model.allocateAssignmentToPerson(assignmentToAllocate, person);

            if (!allocated) {
                continue;
            }

            if (allocatedPersons.length() > 0) {
                allocatedPersons.append("; ");
            }
            allocatedPersons.append(person.getName().fullName);
            anyAllocated = true;
            allocatedCount++;
        }

        if (!anyAllocated) {
            throw new CommandException(AllocateAssignmentCommand.MESSAGE_ALLOCATION_FAILED);
        }

        return new CommandResult(String.format(AllocateAssignmentCommand.MESSAGE_SUCCESS,
                Messages.format(assignmentToAllocate), allocatedCount, allocatedPersons.toString()));
    }

}
