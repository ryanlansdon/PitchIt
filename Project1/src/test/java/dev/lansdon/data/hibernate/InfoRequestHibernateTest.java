package dev.lansdon.data.hibernate;

import dev.lansdon.models.InfoRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InfoRequestHibernateTest {
    public static InfoRequestHibernate irHibernate;

    @BeforeAll
    public static void setup() {
        irHibernate = new InfoRequestHibernate();
    }

    @Test
    public void testAddInfoRequest() {
        InfoRequest ir = new InfoRequest();
        ir.setRequestText("This is a test request");
        ir.setId(irHibernate.add(ir).getId());
        assertTrue(ir.equals(irHibernate.getById(ir.getId())));
    }

//    @Test
//    public void testAddByPitch() {
//        InfoRequest ir = new InfoRequest();
//        ir.setRequestText("Testing add request by pitch");
//        ir.setId(irHibernate.add)
//    }
}
