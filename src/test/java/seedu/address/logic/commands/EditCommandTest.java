package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showstudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_student;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_student;
import static seedu.address.testutil.Typicalstudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditstudentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendar.EdulogCalendar;
import seedu.address.model.student.student;
import seedu.address.testutil.EditstudentDescriptorBuilder;
import seedu.address.testutil.studentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EdulogCalendar());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        student editedstudent = new studentBuilder().build();
        EditstudentDescriptor descriptor = new EditstudentDescriptorBuilder(editedstudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_student, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_student_SUCCESS, Messages.format(editedstudent));

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new EdulogCalendar());
        expectedModel.setstudent(model.getFilteredstudentList().get(0), editedstudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLaststudent = Index.fromOneBased(model.getFilteredstudentList().size());
        student laststudent = model.getFilteredstudentList().get(indexLaststudent.getZeroBased());

        studentBuilder studentInList = new studentBuilder(laststudent);
        student editedstudent = studentInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditstudentDescriptor descriptor = new EditstudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLaststudent, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_student_SUCCESS, Messages.format(editedstudent));

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new EdulogCalendar());
        expectedModel.setstudent(laststudent, editedstudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_student, new EditstudentDescriptor());
        student editedstudent = model.getFilteredstudentList().get(INDEX_FIRST_student.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_student_SUCCESS, Messages.format(editedstudent));

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new EdulogCalendar());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showstudentAtIndex(model, INDEX_FIRST_student);

        student studentInFilteredList = model.getFilteredstudentList().get(INDEX_FIRST_student.getZeroBased());
        student editedstudent = new studentBuilder(studentInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_student,
                new EditstudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_student_SUCCESS, Messages.format(editedstudent));

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new EdulogCalendar());
        expectedModel.setstudent(model.getFilteredstudentList().get(0), editedstudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatestudentUnfilteredList_failure() {
        student firststudent = model.getFilteredstudentList().get(INDEX_FIRST_student.getZeroBased());
        EditstudentDescriptor descriptor = new EditstudentDescriptorBuilder(firststudent).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_student, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_student);
    }

    @Test
    public void execute_duplicatestudentFilteredList_failure() {
        showstudentAtIndex(model, INDEX_FIRST_student);

        // edit student in filtered list into a duplicate in address book
        student studentInList = model.getAddressBook().getstudentList().get(INDEX_SECOND_student.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_student,
                new EditstudentDescriptorBuilder(studentInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_student);
    }

    @Test
    public void execute_invalidstudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredstudentList().size() + 1);
        EditstudentDescriptor descriptor = new EditstudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_student_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidstudentIndexFilteredList_failure() {
        showstudentAtIndex(model, INDEX_FIRST_student);
        Index outOfBoundIndex = INDEX_SECOND_student;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getstudentList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditstudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_student_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_student, DESC_AMY);

        // same values -> returns true
        EditstudentDescriptor copyDescriptor = new EditstudentDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_student, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_student, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_student, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditstudentDescriptor editstudentDescriptor = new EditstudentDescriptor();
        EditCommand editCommand = new EditCommand(index, editstudentDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editstudentDescriptor="
                + editstudentDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
