package cpp.logic.parser.classgroup;

import java.util.List;
import java.util.stream.Stream;

import cpp.commons.core.index.Index;
import cpp.logic.Messages;
import cpp.logic.commands.classgroup.AddClassGroupCommand;
import cpp.logic.parser.ArgumentMultimap;
import cpp.logic.parser.ArgumentTokenizer;
import cpp.logic.parser.CliSyntax;
import cpp.logic.parser.Parser;
import cpp.logic.parser.ParserUtil;
import cpp.logic.parser.Prefix;
import cpp.logic.parser.exceptions.ParseException;
import cpp.model.classgroup.ClassGroup;
import cpp.model.classgroup.ClassGroupName;

/**
 * Parses input arguments and creates a new AddClassGroupCommand object
 */
public class AddClassGroupCommandParser implements Parser<AddClassGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddClassGroupCommand and returns an AddClassGroupCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddClassGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_CLASS,
                CliSyntax.PREFIX_CONTACT);

        if (!AddClassGroupCommandParser.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_CLASS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddClassGroupCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_CLASS, CliSyntax.PREFIX_CONTACT);

        ClassGroupName name = ParserUtil.parseClassGroupName(argMultimap.getValue(CliSyntax.PREFIX_CLASS).get());

        String contactString = argMultimap.getValue(CliSyntax.PREFIX_CONTACT).orElse("");
        List<Index> contactIndices = ParserUtil.parseContactIndices(contactString);

        ClassGroup classGroup = new ClassGroup(name);

        return new AddClassGroupCommand(classGroup, contactIndices);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
