package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueStudentList Students;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        Students = new UniqueStudentList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Students in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Student list with {@code Students}.
     * {@code Students} must not contain duplicate Students.
     */
    public void setStudents(List<Student> Students) {
        this.Students.setStudents(Students);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
    }

    //// Student-level operations

    /**
     * Returns true if a Student with the same identity as {@code Student} exists in the address book.
     */
    public boolean hasStudent(Student Student) {
        requireNonNull(Student);
        return Students.contains(Student);
    }

    /**
     * Adds a Student to the address book.
     * The Student must not already exist in the address book.
     */
    public void addStudent(Student p) {
        Students.add(p);
    }

    /**
     * Replaces the given Student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The Student identity of {@code editedStudent} must not be the same as another existing Student in the address book.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        Students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        Students.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Students", Students)
                .toString();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return Students.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return Students.equals(otherAddressBook.Students);
    }

    @Override
    public int hashCode() {
        return Students.hashCode();
    }
}
