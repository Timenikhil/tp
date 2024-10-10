package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showstudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_student;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_student;
import static seedu.address.testutil.Typicalstudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendar.EdulogCalendar;
import seedu.address.model.student.student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EdulogCalendar());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        student studentToDelete = model.getFilteredstudentList().get(INDEX_FIRST_student.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_student);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_student_SUCCESS,
                Messages.format(studentToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new EdulogCalendar());
        expectedModel.deletestudent(studentToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredstudentList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_student_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showstudentAtIndex(model, INDEX_FIRST_student);

        student studentToDelete = model.getFilteredstudentList().get(INDEX_FIRST_student.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_student);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_student_SUCCESS,
                Messages.format(studentToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new EdulogCalendar());
        expectedModel.deletestudent(studentToDelete);
        showNostudent(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showstudentAtIndex(model, INDEX_FIRST_student);

        Index outOfBoundIndex = INDEX_SECOND_student;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getstudentList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_student_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_student);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_student);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_student);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNostudent(Model model) {
        model.updateFilteredstudentList(p -> false);

        assertTrue(model.getFilteredstudentList().isEmpty());
    }
}
