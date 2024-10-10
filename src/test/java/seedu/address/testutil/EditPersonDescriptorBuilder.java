package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditstudentDescriptor;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.student;
import seedu.address.model.student.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditstudentDescriptor objects.
 */
public class EditstudentDescriptorBuilder {

    private EditstudentDescriptor descriptor;

    public EditstudentDescriptorBuilder() {
        descriptor = new EditstudentDescriptor();
    }

    public EditstudentDescriptorBuilder(EditstudentDescriptor descriptor) {
        this.descriptor = new EditstudentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditstudentDescriptor} with fields containing {@code student}'s details
     */
    public EditstudentDescriptorBuilder(student student) {
        descriptor = new EditstudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setPhone(student.getPhone());
        descriptor.setEmail(student.getEmail());
        descriptor.setAddress(student.getAddress());
        descriptor.setTags(student.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditstudentDescriptor} that we are building.
     */
    public EditstudentDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditstudentDescriptor} that we are building.
     */
    public EditstudentDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditstudentDescriptor} that we are building.
     */
    public EditstudentDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditstudentDescriptor} that we are building.
     */
    public EditstudentDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditstudentDescriptor}
     * that we are building.
     */
    public EditstudentDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditstudentDescriptor build() {
        return descriptor;
    }
}
