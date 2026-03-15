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
import cpp.model.assignment.AssignmentName;
import cpp.model.assignment.ContactAssignment;
import cpp.model.assignment.exceptions.ContactAlreadyAllocatedAssignmentException;
import cpp.model.contact.Contact;
import cpp.model.util.AssignmentUtil;

/**
 * Unallocates an existing assignment from a contact by their displayed indices
 * or class group.
 */
public class UnallocateAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "unallocass";

    public static final String MESSAGE_USAGE = UnallocateAssignmentCommand.COMMAND_WORD
            + ": Unallocates an assignment from a contact. "
            + "Parameters: "
            + CliSyntax.PREFIX_ASSIGNMENT + "ASSIGNMENT NAME "
            + "[" + CliSyntax.PREFIX_CLASS + "CLASS NAME] "
            + "[" + CliSyntax.PREFIX_CONTACT + "CONTACT INDICES...]\n"
            + "At least one of " + CliSyntax.PREFIX_CLASS + " or " + CliSyntax.PREFIX_CONTACT + " must be provided.\n"
            + "Example: " + UnallocateAssignmentCommand.COMMAND_WORD + " "
            + CliSyntax.PREFIX_ASSIGNMENT + "Assignment 1 "
            + CliSyntax.PREFIX_CLASS + "CS2103T10 "
            + CliSyntax.PREFIX_CONTACT + "1 2 3";

    public static final String MESSAGE_SUCCESS = """
            Unallocated assignment: %1$s from %2$s contacts.\nContacts unallocated: %3$s
            """;
    public static final String MESSAGE_INVALID_ASSIGNMENT_NAME = "The assignment name provided is invalid";
    public static final String MESSAGE_UNALLOCATION_FAILED = "No contacts were unallocated the assignment.";

    private final AssignmentName assignmentName;
    private final List<Index> contactIndices;

    /**
     * Creates an UnallocateAssignmentCommand with the specified assignment and
     * contact indices.
     */
    public UnallocateAssignmentCommand(AssignmentName assignmentName, List<Index> contactIndices) {
        Objects.requireNonNull(assignmentName);
        Objects.requireNonNull(contactIndices);
        this.assignmentName = assignmentName;
        this.contactIndices = new ArrayList<>(contactIndices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);

        List<Assignment> assignmentList = model.getAddressBook().getAssignmentList();

        Assignment assignmentToUnallocate = AssignmentUtil.findAssignment(assignmentList, this.assignmentName);

        if (assignmentToUnallocate == null) {
            throw new CommandException(UnallocateAssignmentCommand.MESSAGE_INVALID_ASSIGNMENT_NAME);
        }

        List<Contact> lastShownContactList = model.getFilteredContactList();

        this.checkContactIndices(lastShownContactList);

        return this.unallocateFromContacts(model, assignmentToUnallocate, lastShownContactList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnallocateAssignmentCommand)) {
            return false;
        }
        UnallocateAssignmentCommand o = (UnallocateAssignmentCommand) other;
        return this.assignmentName.equals(o.assignmentName) && this.contactIndices.equals(o.contactIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignmentName", this.assignmentName)
                .add("contactIndices", this.contactIndices)
                .toString();
    }

    private void checkContactIndices(List<Contact> lastShownContactList) throws CommandException {
        for (Index idx : this.contactIndices) {
            if (idx.getZeroBased() >= lastShownContactList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
        }
    }

    private CommandResult unallocateFromContacts(Model model, Assignment assignmentToUnallocate,
            List<Contact> lastShownContactList) throws CommandException {
        StringBuilder unallocatedContacts = new StringBuilder();
        boolean anyUnallocated = false;
        int unallocatedCount = 0;

        for (Index idx : this.contactIndices) {
            Contact contact = lastShownContactList.get(idx.getZeroBased());
            ContactAssignment ca = new ContactAssignment(assignmentToUnallocate.getId(), contact.getId());

            try {
                model.addContactAssignment(ca);

            } catch (ContactAlreadyAllocatedAssignmentException e) {
                // Skip already allocated contacts without failing the entire command.
                continue;
            }

            if (unallocatedContacts.length() > 0) {
                unallocatedContacts.append("; ");
            }
            unallocatedContacts.append(contact.getName().fullName);
            anyUnallocated = true;
            unallocatedCount++;
        }

        if (!anyUnallocated) {
            throw new CommandException(UnallocateAssignmentCommand.MESSAGE_UNALLOCATION_FAILED);
        }

        return new CommandResult(String.format(UnallocateAssignmentCommand.MESSAGE_SUCCESS,
                Messages.format(assignmentToUnallocate), unallocatedCount, unallocatedContacts.toString()));
    }

}
