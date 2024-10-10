package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Typicalstudents.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.calendar.Lesson;
import seedu.address.model.student.student;
import seedu.address.testutil.studentBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullstudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingstudentAdded modelStub = new ModelStubAcceptingstudentAdded();
        student validstudent = new studentBuilder().build();

        CommandResult commandResult = new AddCommand(validstudent).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validstudent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validstudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicatestudent_throwsCommandException() {
        student validstudent = new studentBuilder().build();
        AddCommand addCommand = new AddCommand(validstudent);
        ModelStub modelStub = new ModelStubWithstudent(validstudent);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_student, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        student alice = new studentBuilder().withName("Alice").build();
        student bob = new studentBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addstudent(student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasstudent(student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletestudent(student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setstudent(student target, student editedstudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<student> getFilteredstudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredstudentList(Predicate<student> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithstudent extends ModelStub {
        private final student student;

        ModelStubWithstudent(student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasstudent(student student) {
            requireNonNull(student);
            return this.student.isSamestudent(student);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingstudentAdded extends ModelStub {
        final ArrayList<student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasstudent(student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSamestudent);
        }

        @Override
        public void addstudent(student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
