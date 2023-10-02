package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Duplicates a person identified using it's displayed index from the address book.
 */
public class DuplicateCommand extends Command {

    public static final String COMMAND_WORD = "duplicate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Duplicates the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DUPLICATE_PERSON_SUCCESS = "Duplicated Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON_FAILURE = "Person does not exist.";

    private final Index index;

    /**
    * @param index of the person in the filtered person list to duplicate
    */
    public DuplicateCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Executes the duplicate command.
     * @param model {@code Model} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (index.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_FAILURE);
        }

        Person personToDuplicate = lastShownList.get(index.getZeroBased());
        Person duplicatedPerson = createDuplicatedPerson(personToDuplicate);

        model.addPerson(duplicatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DUPLICATE_PERSON_SUCCESS, personToDuplicate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DuplicateCommand // instanceof handles nulls
                && index.equals(((DuplicateCommand) other).index)); // state check
    }

    @Override
    public String toString() {
        return "DUPLICATE COMMAND";
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     */
    private static Person createDuplicatedPerson(Person personToEdit) {
        assert personToEdit != null;

        Name updatedName = new Name(personToEdit.getName().fullName + " copy");
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }
}
