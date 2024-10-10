package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.Typicalstudents;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_studentS_FILE = TEST_DATA_FOLDER.resolve("typicalstudentsAddressBook.json");
    private static final Path INVALID_student_FILE = TEST_DATA_FOLDER.resolve("invalidstudentAddressBook.json");
    private static final Path DUPLICATE_student_FILE = TEST_DATA_FOLDER.resolve("duplicatestudentAddressBook.json");

    @Test
    public void toModelType_typicalstudentsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_studentS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalstudentsAddressBook = Typicalstudents.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalstudentsAddressBook);
    }

    @Test
    public void toModelType_invalidstudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_student_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatestudents_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_student_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_student,
                dataFromFile::toModelType);
    }

}
