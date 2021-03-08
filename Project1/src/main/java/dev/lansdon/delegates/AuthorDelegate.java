package dev.lansdon.delegates;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lansdon.models.*;
import dev.lansdon.services.PitchService;
import dev.lansdon.services.PitchServiceImpl;
import dev.lansdon.services.UserService;
import dev.lansdon.services.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/*
 /author - (GET) gets author pitches
         - (POST) adds pitch for author
 /author/:id - (PUT) updates a pitch
             - (DELETE) deletes a pitch
 /author/draft/:id - (PUT) add draft to pitch
 */
public class AuthorDelegate implements FrontControllerDelegate {
    private ObjectMapper om = new ObjectMapper();
    private PitchService pService = new PitchServiceImpl();
    private UserService uService = new UserServiceImpl();
    Logger log = Logger.getLogger(AuthorDelegate.class);

    @Override
    public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = (String) req.getAttribute("path");
        User u = (User) req.getSession().getAttribute("user");

        if (path == null || path.equals("")) {
            switch (req.getMethod()) {
                case "POST":
                    log.debug("Into post method");
                    // Add a pitch for the author
                    Map m = om.readValue(req.getInputStream(), Map.class);
                    log.debug(m);
                    Pitch p = new Pitch();
                    Story s = new Story();
                    StoryType st = new StoryType();
                    st.setName((String) m.get("type"));
                    s.setType(st);
                    Genre g = new Genre();
                    g.setName((String) m.get("genre"));
                    s.setGenre(g);
                    s.setTitle((String) m.get("title"));
                    s.setTagline((String) m.get("tagline"));
                    s.setDescription((String) m.get("description"));
                    s.setAuthorInfo((String) m.get("authorInfo"));
                    s.setCompletionDate(LocalDate.parse((String) m.get("completionDate")));
                    p.setStory(s);
                    p.setPendingDate(LocalDate.parse((String) m.get("pendingDate")));
                    p.setId(pService.createPitch(p));
                    if (uService.calculatePoints(u) + p.getStory().getType().getWeight() > 100) {
                        PitchStatus ps = new PitchStatus();
                        ps.setId(6);
                        ps.setName("On Hold");
                        p.setStatus(ps);
                    }
                    Set<Pitch> pitches = u.getPitches();
                    pitches.add(p);
                    u.setPitches(pitches);
                    uService.updateUser(u);

                    res.getWriter().write(om.writeValueAsString(pitches));
                    req.getSession().setAttribute("user", u);
                    res.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "GET":
                    // Gets all pitches for author
                    res.getWriter().write(om.writeValueAsString(u.getPitches()));
                    break;
                default:
                    res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } else if (path.contains("draft")) {
            StringBuilder pathId = new StringBuilder(path);
            pathId.replace(0, pathId.indexOf("/")+1, "");
            Integer id = Integer.valueOf(pathId.toString());
            Pitch p = pService.getPitchById(id);
            Map m = om.readValue(req.getInputStream(), Map.class);
            String draft = (String) m.get("draftText");
            Story s = p.getStory();
            s.setDraft(draft);
            p.setStory(s);
            PitchStatus ps = new PitchStatus();
            ps.setId(9);
            ps.setName("Draft");

            if (s.getType().getName().equals("Article")) {
                p.setSrStatus(ps);
                p.setStatus(ps);
            } else if (s.getType().getName().equals("Short Story")) {
                p.setEditorStatus(ps);
                p.setStatus(ps);
            }
            pService.updatePitch(p);
            u = uService.getUserById(u.getId());
            req.getSession().setAttribute("user", u);
            res.setStatus(200);
        }
    }
}
