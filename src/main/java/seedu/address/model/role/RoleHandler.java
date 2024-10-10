package seedu.address.model.role;

import java.util.Objects;

import seedu.address.model.person.Person;
import seedu.address.model.role.exceptions.InvalidRoleException;

/**
 * Handles the checking of roles and adding to roles
 */
public class RoleHandler {
    public static final String MESSAGE_CONSTRAINTS = "Roles should be alphanumeric";

    /**
     * Enum for the different types of roles
     */
    public enum RoleType {
        SPONSOR, VOLUNTEER, ATTENDEE, VENDOR
    }
    private static final Attendee ATTENDEE = new Attendee();
    private static final Sponsor SPONSOR = new Sponsor();
    private static final Vendor VENDOR = new Vendor();
    private static final Volunteer VOLUNTEER = new Volunteer();


    public RoleHandler() {

    }

    /**
     * Returns the specified role from string, null if invalid.
     * @param role String representation of role.
     * @return Corresponding role object.
     */
    public Role getRole(String role) throws InvalidRoleException {
        role = role.trim().toLowerCase();
        switch (role) {
        case Attendee.ROLE_WORD:
            return ATTENDEE;
        case Sponsor.ROLE_WORD:
            return SPONSOR;
        case Vendor.ROLE_WORD:
            return VENDOR;
        case Volunteer.ROLE_WORD:
            return VOLUNTEER;
        default:
            throw new InvalidRoleException();
        }
    }

    /**
     * Checks if a person has the specified role
     * @param person Person to check
     * @param roleString String representation of a valid role
     * @return True if person has the role, false otherwise
     * @throws InvalidRoleException If roleString is invalid
     */
    public boolean isRole(Person person, String roleString) throws InvalidRoleException {
        Objects.requireNonNull(person);
        Objects.requireNonNull(roleString);

        Role role = getRole(roleString);
        if (role == null) {
            throw new InvalidRoleException();
        }
        return role.isRole(person);
    }
}