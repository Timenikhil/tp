package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.calendar.Lesson;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a Student with the same identity as {@code Student} exists in the address book.
     */
    boolean hasStudent(Student Student);

    /**
     * Deletes the given Student.
     * The Student must exist in the address book.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given Student.
     * {@code Student} must not already exist in the address book.
     */
    void addStudent(Student Student);

    /**
     * Replaces the given Student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The Student identity of {@code editedStudent} must not be the same as another existing Student in the address book.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Returns true if a lesson with the same description as {@code lesson} exists in the calendar.
     */
    boolean hasLesson(Lesson lesson);

    /**
     * Adds the given lesson.
     * {@code lesson} must not already exist in the calendar.
     */
    void addLesson(Lesson lesson);



    /** Returns an unmodifiable view of the filtered Student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered Student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);
}
