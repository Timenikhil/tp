package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Typicalstudents.ALICE;
import static seedu.address.testutil.Typicalstudents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.student;
import seedu.address.model.student.exceptions.DuplicatestudentException;
import seedu.address.testutil.studentBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getstudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatestudents_throwsDuplicatestudentException() {
        // Two students with the same identity fields
        student editedAlice = new studentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<student> newstudents = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newstudents);

        assertThrows(DuplicatestudentException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasstudent_nullstudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasstudent(null));
    }

    @Test
    public void hasstudent_studentNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasstudent(ALICE));
    }

    @Test
    public void hasstudent_studentInAddressBook_returnsTrue() {
        addressBook.addstudent(ALICE);
        assertTrue(addressBook.hasstudent(ALICE));
    }

    @Test
    public void hasstudent_studentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addstudent(ALICE);
        student editedAlice = new studentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasstudent(editedAlice));
    }

    @Test
    public void getstudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getstudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{students=" + addressBook.getstudentList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose students list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<student> students = FXCollections.observableArrayList();

        AddressBookStub(Collection<student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<student> getstudentList() {
            return students;
        }
    }

}
