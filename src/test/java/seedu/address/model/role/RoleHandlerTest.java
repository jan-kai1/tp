package seedu.address.model.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.role.exceptions.InvalidRoleException;


public class RoleHandlerTest {
    @Test
    public void getRole_validRole_success() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (RoleHandler.getRole("attendee") instanceof Attendee);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_vendor() {

        try {
            assert (RoleHandler.getRole("vendor") instanceof Vendor);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_sponsor() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (RoleHandler.getRole("sponsor") instanceof Sponsor);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_volunteer() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (RoleHandler.getRole("volunteer") instanceof Volunteer);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }
    @Test
    public void getRole_invalidRole_success() {
        try {
            assert (RoleHandler.getRole("invalid") instanceof Vendor);

        } catch (InvalidRoleException e) {
            assert (true);
        }
    }

    @Test
    public void isRole_validRole_success() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role1 = RoleHandler.getRole("attendee");
            Role role2 = RoleHandler.getRole("attendee");
            assertEquals(role1, role2);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_hasRole_notInRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = RoleHandler.getRole("attendee");
            assert (!new RoleHandler().isRole(BOB, "attendee"));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_hasRole_inRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = RoleHandler.getRole("attendee");
            role.addPerson(ALICE);
            assert (new RoleHandler().isRole(ALICE, "attendee"));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_hasDifferentRole_notInRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = RoleHandler.getRole("attendee");
            role.addPerson(ALICE);
            assert (!new RoleHandler().isRole(ALICE, "sponsor"));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_invalidRole_failure() {
        RoleHandler roleHandler = new RoleHandler();

        assertThrows(InvalidRoleException.class, () -> new RoleHandler().isRole(ALICE, "invalid"));


    }

    @Test
    public void isValidRoleName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> RoleHandler.isValidRoleName(null));
    }

    @Test
    public void isValidRoleName_invalidValue_returnsFalse() {
        assertFalse(RoleHandler.isValidRoleName("InvalidRole"));
        assertFalse(RoleHandler.isValidRoleName("Random"));
        assertFalse(RoleHandler.isValidRoleName(""));
    }

    @Test
    public void isValidRoleName_validValue_returnsTrue() {
        assertTrue(RoleHandler.isValidRoleName(Attendee.ROLE_WORD));
        assertTrue(RoleHandler.isValidRoleName(Sponsor.ROLE_WORD));
        assertTrue(RoleHandler.isValidRoleName(Vendor.ROLE_WORD));
        assertTrue(RoleHandler.isValidRoleName(Volunteer.ROLE_WORD));
    }

    @Test
    public void isValidRoleName_validValueWithWhitespace_returnsFalse() {
        assertTrue(RoleHandler.isValidRoleName(" " + Attendee.ROLE_WORD + " "));
        assertTrue(RoleHandler.isValidRoleName(" " + Sponsor.ROLE_WORD + " "));
    }

    @Test
    public void isValidRoleName_caseInsensitive_returnsFalse() {
        assertTrue(RoleHandler.isValidRoleName(Attendee.ROLE_WORD.toLowerCase()));
        assertTrue(RoleHandler.isValidRoleName(Sponsor.ROLE_WORD.toUpperCase()));
    }

}
