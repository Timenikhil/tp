package seedu.edulog.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.edulog.commons.core.GuiSettings;
import seedu.edulog.logic.commands.CommandResult;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.logic.parser.exceptions.ParseException;
import seedu.edulog.model.ReadOnlyEduLog;
import seedu.edulog.model.student.Student;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the EduLog.
     *
     * @see seedu.edulog.model.Model#getEduLog()
     */
    ReadOnlyEduLog getEduLog();

    /** Returns an unmodifiable view of the filtered list of students */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Returns the user prefs' edulog book file path.
     */
    Path getEduLogFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}