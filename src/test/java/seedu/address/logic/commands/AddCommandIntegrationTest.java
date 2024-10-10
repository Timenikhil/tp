package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Typicalstudents.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendar.EdulogCalendar;
import seedu.address.model.student.student;
import seedu.address.testutil.studentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EdulogCalendar());
    }

    @Test
    public void execute_newstudent_success() {
        student validstudent = new studentBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new EdulogCalendar());
        expectedModel.addstudent(validstudent);

        assertCommandSuccess(new AddCommand(validstudent), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validstudent)),
                expectedModel);
    }

    @Test
    public void execute_duplicatestudent_throwsCommandException() {
        student studentInList = model.getAddressBook().getstudentList().get(0);
        assertCommandFailure(new AddCommand(studentInList), model,
                AddCommand.MESSAGE_DUPLICATE_student);
    }

}
