package cpp.testutil;

import cpp.logic.commands.classgroup.AddClassGroupCommand;
import cpp.logic.parser.CliSyntax;
import cpp.model.classgroup.ClassGroup;

/**
 * A utility class containing methods to help with testing of ClassGroup.
 */
public class ClassGroupUtil {

    /**
     * Returns an add command string for adding the {@code classGroup}.
     */
    public static String getAddClassGroupCommand(ClassGroup classGroup) {
        return AddClassGroupCommand.COMMAND_WORD + " " + ClassGroupUtil.getClassGroupDetails(classGroup);
    }

    /**
     * Returns the part of command string for the given {@code classGroup}'s
     * details.
     */
    public static String getClassGroupDetails(ClassGroup classGroup) {
        return " " + CliSyntax.PREFIX_CLASS + classGroup.getName().fullName + " ";
    }
}
