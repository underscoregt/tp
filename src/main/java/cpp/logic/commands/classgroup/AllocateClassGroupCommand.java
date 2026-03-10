package cpp.logic.commands.classgroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cpp.commons.core.index.Index;
import cpp.commons.util.ToStringBuilder;
import cpp.logic.Messages;
import cpp.logic.commands.Command;
import cpp.logic.commands.CommandResult;
import cpp.logic.commands.assignment.AllocateAssignmentCommand;
import cpp.logic.commands.exceptions.CommandException;
import cpp.logic.parser.CliSyntax;
import cpp.model.Model;
import cpp.model.classgroup.ClassGroupName;
import cpp.model.contact.Contact;

/**
 * Allocates a contact to a class group by their displayed indices.
 */
public class AllocateClassGroupCommand extends Command {

    public static final String COMMAND_WORD = "allocclass";

    public static final String MESSAGE_USAGE = AllocateAssignmentCommand.COMMAND_WORD
            + ": Allocates an contact to a class group. "
            + "Parameters: "
            + CliSyntax.PREFIX_CLASS + "CLASS NAME "
            + CliSyntax.PREFIX_CONTACT + "CONTACT INDICES...\n"
            + "Example: " + AllocateAssignmentCommand.COMMAND_WORD + " "
            + CliSyntax.PREFIX_CLASS + "CS2103T10 "
            + CliSyntax.PREFIX_CONTACT + "1 2 3";

    public static final String MESSAGE_SUCCESS = """
            Allocated class group: %1$s contacts to %2$s.\nContacts allocated: %3$s
            """;
    public static final String MESSAGE_INVALID_CLASS_GROUP_NAME = "The class group name provided is invalid";
    public static final String MESSAGE_ALLOCATION_FAILED = "No contacts were allocated the class group.";

    private final ClassGroupName classGroupName;
    private final List<Index> contactIndices;

    /**
     * Creates an AllocateClassGroupCommand with the specified class group name and
     * contact indices.
     */
    public AllocateClassGroupCommand(ClassGroupName classGroupName, List<Index> contactIndices) {
        Objects.requireNonNull(classGroupName);
        Objects.requireNonNull(contactIndices);
        this.classGroupName = classGroupName;
        this.contactIndices = new ArrayList<>(contactIndices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // TODO: Implement
        throw new CommandException("Command not implemented yet");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof AllocateClassGroupCommand)) {
            return false;
        }

        AllocateClassGroupCommand o = (AllocateClassGroupCommand) other;
        return o.classGroupName.equals(this.classGroupName)
                && o.contactIndices.equals(this.contactIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("classGroupName", this.classGroupName)
                .add("contactIndices", this.contactIndices)
                .toString();
    }

    private void checkContactIndicesInRange(List<Contact> lastShownContactList, List<Index> contactIndices)
            throws CommandException {
        for (Index index : contactIndices) {
            if (index.getZeroBased() >= lastShownContactList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
        }
    }

    private void allocateContactsToClassGroup(Model model, List<Contact> lastShownContactList) {
        for (Index index : this.contactIndices) {
            String contactId = lastShownContactList.get(index.getZeroBased()).getId();
            // TODO: Implement allocation logic
        }
    }
}
