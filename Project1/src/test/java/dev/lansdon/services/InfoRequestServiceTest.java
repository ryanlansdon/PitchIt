package dev.lansdon.services;

import dev.lansdon.models.InfoRequest;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InfoRequestServiceTest {
    public static InfoRequestService irService;
    public static UserService uService;
    public static PitchService pService;

    @BeforeAll
    public static void setup() {
        irService = new InfoRequestServiceImpl();
        uService = new UserServiceImpl();
        pService = new PitchServiceImpl();
    }

    @Test
    public void testAddByPitch() {
        InfoRequest ir = new InfoRequest();
        ir.setRequestText("This is a test for add by pitch");
        User editor = uService.getUserByUsername("ased4");
        Pitch p = pService.getPitchById(1);
        ir = irService.addRequestByPitch(editor, p, ir);
        editor = uService.getUserByUsername("ased4");
        assertTrue(editor.getOutRequests().contains(ir));
    }
}
