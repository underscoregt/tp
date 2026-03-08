package cpp.logic.parser;

import cpp.commons.core.index.Index;
import cpp.logic.Messages;
import cpp.logic.commands.Command;
import cpp.logic.commands.DeleteCommand;
import cpp.logic.commands.assignment.DeleteAssignmentCommand;
import cpp.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand or DeleteAssignmentCommand object.
 */
public class DeleteCommandParser implements Parser<Command> {

    @Override
    public Command parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_CONTACT, CliSyntax.PREFIX_ASSIGNMENT);

        boolean hasContact = argMultimap.getValue(CliSyntax.PREFIX_CONTACT).isPresent();
        boolean hasAssignment = argMultimap.getValue(CliSyntax.PREFIX_ASSIGNMENT).isPresent();

        if (hasContact && !hasAssignment) {
            return parseDeleteContact(argMultimap);
        } else if (hasAssignment && !hasContact) {
            return parseDeleteAssignment(argMultimap);
        } else {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    // parse delete contact by index
    private DeleteCommand parseDeleteContact(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_CONTACT).get());
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    // parse delete assignment by index
    private DeleteAssignmentCommand parseDeleteAssignment(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_ASSIGNMENT).get());
            return new DeleteAssignmentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE), pe);
        }
    }

}
