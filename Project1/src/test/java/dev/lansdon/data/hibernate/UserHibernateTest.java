package dev.lansdon.data.hibernate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.lansdon.exceptions.NonUniqueUsernameException;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.Role;
import dev.lansdon.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserHibernateTest {
    public static UserHibernate uh;
    public static PitchHibernate ph;

//    @Test
//    public void testAddUser() {
//        User u = new User();
//        u.setFirstName("Ryan");
//        u.setLastName("Lansdon");
//        u.setUsername("ryan");
//        u.setPassword("pass");
//        Role r = new Role();
//        r.setId(4);
//        r.setName("Author");
//        u.setRole(r);
//        try {
//            u.setId(uh.add(u).getId());
//        } catch(NonUniqueUsernameException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void testGetByPitch() {
//        Pitch p = ph.getById(1);
//        User u = uh.getByPitch(p);
//        assertNotNull(u);
//    }

    @BeforeAll
    public static void setup() {
        uh = new UserHibernate();
        ph = new PitchHibernate();
    }
}
