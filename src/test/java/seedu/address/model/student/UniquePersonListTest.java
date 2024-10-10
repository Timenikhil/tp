package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Typicalstudents.ALICE;
import static seedu.address.testutil.Typicalstudents.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.exceptions.DuplicatestudentException;
import seedu.address.model.student.exceptions.studentNotFoundException;
import seedu.address.testutil.studentBuilder;

public class UniquestudentListTest {

    private final UniquestudentList uniquestudentList = new UniquestudentList();

    @Test
    public void contains_nullstudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquestudentList.contains(null));
    }

    @Test
    public void contains_studentNotInList_returnsFalse() {
        assertFalse(uniquestudentList.contains(ALICE));
    }

    @Test
    public void contains_studentInList_returnsTrue() {
        uniquestudentList.add(ALICE);
        assertTrue(uniquestudentList.contains(ALICE));
    }

    @Test
    public void contains_studentWithSameIdentityFieldsInList_returnsTrue() {
        uniquestudentList.add(ALICE);
        student editedAlice = new studentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquestudentList.contains(editedAlice));
    }

    @Test
    public void add_nullstudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquestudentList.add(null));
    }

    @Test
    public void add_duplicatestudent_throwsDuplicatestudentException() {
        uniquestudentList.add(ALICE);
        assertThrows(DuplicatestudentException.class, () -> uniquestudentList.add(ALICE));
    }

    @Test
    public void setstudent_nullTargetstudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquestudentList.setstudent(null, ALICE));
    }

    @Test
    public void setstudent_nullEditedstudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquestudentList.setstudent(ALICE, null));
    }

    @Test
    public void setstudent_targetstudentNotInList_throwsstudentNotFoundException() {
        assertThrows(studentNotFoundException.class, () -> uniquestudentList.setstudent(ALICE, ALICE));
    }

    @Test
    public void setstudent_editedstudentIsSamestudent_success() {
        uniquestudentList.add(ALICE);
        uniquestudentList.setstudent(ALICE, ALICE);
        UniquestudentList expectedUniquestudentList = new UniquestudentList();
        expectedUniquestudentList.add(ALICE);
        assertEquals(expectedUniquestudentList, uniquestudentList);
    }

    @Test
    public void setstudent_editedstudentHasSameIdentity_success() {
        uniquestudentList.add(ALICE);
        student editedAlice = new studentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquestudentList.setstudent(ALICE, editedAlice);
        UniquestudentList expectedUniquestudentList = new UniquestudentList();
        expectedUniquestudentList.add(editedAlice);
        assertEquals(expectedUniquestudentList, uniquestudentList);
    }

    @Test
    public void setstudent_editedstudentHasDifferentIdentity_success() {
        uniquestudentList.add(ALICE);
        uniquestudentList.setstudent(ALICE, BOB);
        UniquestudentList expectedUniquestudentList = new UniquestudentList();
        expectedUniquestudentList.add(BOB);
        assertEquals(expectedUniquestudentList, uniquestudentList);
    }

    @Test
    public void setstudent_editedstudentHasNonUniqueIdentity_throwsDuplicatestudentException() {
        uniquestudentList.add(ALICE);
        uniquestudentList.add(BOB);
        assertThrows(DuplicatestudentException.class, () -> uniquestudentList.setstudent(ALICE, BOB));
    }

    @Test
    public void remove_nullstudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquestudentList.remove(null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsstudentNotFoundException() {
        assertThrows(studentNotFoundException.class, () -> uniquestudentList.remove(ALICE));
    }

    @Test
    public void remove_existingstudent_removesstudent() {
        uniquestudentList.add(ALICE);
        uniquestudentList.remove(ALICE);
        UniquestudentList expectedUniquestudentList = new UniquestudentList();
        assertEquals(expectedUniquestudentList, uniquestudentList);
    }

    @Test
    public void setstudents_nullUniquestudentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquestudentList.setstudents((UniquestudentList) null));
    }

    @Test
    public void setstudents_uniquestudentList_replacesOwnListWithProvidedUniquestudentList() {
        uniquestudentList.add(ALICE);
        UniquestudentList expectedUniquestudentList = new UniquestudentList();
        expectedUniquestudentList.add(BOB);
        uniquestudentList.setstudents(expectedUniquestudentList);
        assertEquals(expectedUniquestudentList, uniquestudentList);
    }

    @Test
    public void setstudents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquestudentList.setstudents((List<student>) null));
    }

    @Test
    public void setstudents_list_replacesOwnListWithProvidedList() {
        uniquestudentList.add(ALICE);
        List<student> studentList = Collections.singletonList(BOB);
        uniquestudentList.setstudents(studentList);
        UniquestudentList expectedUniquestudentList = new UniquestudentList();
        expectedUniquestudentList.add(BOB);
        assertEquals(expectedUniquestudentList, uniquestudentList);
    }

    @Test
    public void setstudents_listWithDuplicatestudents_throwsDuplicatestudentException() {
        List<student> listWithDuplicatestudents = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatestudentException.class, () -> uniquestudentList.setstudents(listWithDuplicatestudents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquestudentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquestudentList.asUnmodifiableObservableList().toString(), uniquestudentList.toString());
    }
}
