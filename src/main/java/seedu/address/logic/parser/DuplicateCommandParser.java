package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DuplicateCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DuplicateCommand object
 */
public class DuplicateCommandParser implements Parser<DuplicateCommand> {

    /**
    * Parses the given {@code String} of arguments in the context of the DuplicateCommand
    * and returns an DuplicateCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public DuplicateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }
        return new DuplicateCommand(index);
    }
}
