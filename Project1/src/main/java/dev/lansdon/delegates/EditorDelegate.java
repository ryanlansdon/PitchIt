package dev.lansdon.delegates;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lansdon.models.*;
import dev.lansdon.services.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.Line;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
    /editor - (GET) get relevant pitches
            - (PUT) update pitch
    /editor/pitch/:id - (POST) create info request by pitch
    /editor/user/:id  - (POST) create info request by editor
    /editor/accept/:id - (PUT) accept pitch
    /editor/reject/:id - (PUT) reject pitch
 */
public class EditorDelegate implements FrontControllerDelegate {
    private ObjectMapper om = new ObjectMapper();
    private PitchService pServ = new PitchServiceImpl();
    private UserService uServ = new UserServiceImpl();
    private InfoRequestService irServ = new InfoRequestServiceImpl();
    Logger log = Logger.getLogger(AuthorDelegate.class);

    @Override
    public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = (String) req.getAttribute("path");
        User u = (User) req.getSession().getAttribute("user");

        if (path == null || path.equals("")) {
            switch (req.getMethod()) {
                case "GET":
                    Set<Pitch> pitches = getRelevantPitches(req, res);
                    res.getWriter().write(om.writeValueAsString(pitches));
                    break;
                case "PUT": // TODO FIX
                    log.debug("Put request");
                    Pitch p = om.readValue(req.getInputStream(), Pitch.class);
                    pServ.updatePitch(p);
                    res.setStatus(200);
                    break;
                default:
                    res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } else if (path.contains("pitch")) {
            StringBuilder pathId = new StringBuilder(path);
            pathId.replace(0, pathId.indexOf("/")+1, "");
            Integer id = Integer.valueOf(pathId.toString());
            Pitch p = pServ.getPitchById(id);
            Map m = om.readValue(req.getInputStream(), Map.class);
            InfoRequest ir = new InfoRequest();
            ir.setRequestText((String) m.get("requestText"));
            irServ.addRequestByPitch(u, p, ir);
            u = uServ.getUserById(u.getId());
            req.getSession().setAttribute("user", u);
            res.setStatus(200);
        } else if (path.contains("user")) {
            StringBuilder pathId = new StringBuilder(path);
            pathId.replace(0, pathId.indexOf("/")+1, "");
            Integer id = Integer.valueOf(pathId.toString());
            User requested = uServ.getUserById(id);
            Map m = om.readValue(req.getInputStream(), Map.class);
            InfoRequest ir = new InfoRequest();
            ir.setRequestText((String) m.get("requestText"));
            irServ.addRequestByUsers(requested, u, ir);
            u = uServ.getUserById(u.getId());
            req.getSession().setAttribute("user", u);
            res.setStatus(200);
        } else if (path.contains("accept")) {
            u = acceptPitch(req, res);
            req.getSession().setAttribute("user", u);
            res.setStatus(200);
        } else if (path.contains("reject")) {
            u = rejectPitch(req, res);
            req.getSession().setAttribute("user", u);
            res.setStatus(200);
        } else if (req.getMethod().equals("GET")) {
            Pitch p = pServ.getPitchById(Integer.valueOf(path));
            res.getWriter().write(om.writeValueAsString(p));
            res.setStatus(200);
        } else {
            res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    public User acceptPitch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = (String) req.getAttribute("path");
        User u = (User) req.getSession().getAttribute("user");
        StringBuilder pathId = new StringBuilder(path);
        pathId.replace(0, pathId.indexOf("/")+1, "");
        Integer id = Integer.valueOf(pathId.toString());
        Pitch p = pServ.getPitchById(id);
        PitchStatus ps = new PitchStatus();
        ps.setId(2);
        ps.setName("Accepted");

        switch (u.getRole().getId()) {
            case 1:
                p.setAsstStatus(ps);
                break;
            case 2:
                p.setEditorStatus(ps);
                break;
            case 3:
                p.setStatus(ps);
                p.setSrStatus(ps);
                break;
            default:
                break;
        }

        pServ.updatePitch(p);
        u = uServ.getUserById(u.getId());
        return u;
    }

    public User rejectPitch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = (String) req.getAttribute("path");
        User u = (User) req.getSession().getAttribute("user");
        StringBuilder pathId = new StringBuilder(path);
        pathId.replace(0, pathId.indexOf("/")+1, "");
        Integer id = Integer.valueOf(pathId.toString());
        Pitch p = pServ.getPitchById(id);
        Map m = om.readValue(req.getInputStream(), Map.class);
        p.setRejectReason((String) m.get("rejectReason"));
        PitchStatus ps = new PitchStatus();
        ps.setId(3);
        ps.setName("Rejected");
        p.setStatus(ps);

        pServ.updatePitch(p);
        u = uServ.getUserById(u.getId());
        return u;
    }

    public Set<Pitch> getRelevantPitches(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        Set<Pitch> pitches = new HashSet<>();

        switch (u.getRole().getId()) {
            case 1:
                for (Genre g : u.getGenres()) {
                    Set<Pitch> genrePitches = pServ.getPitchesByGenre(g);
                    Set<Pitch> priorityPitches = new HashSet<>();
                    for (Pitch p : genrePitches) {
                        if (p.getStatus().getId() == 1 && p.getAsstStatus().getId() == 4) {
                            priorityPitches.add(p);
                        }
                    }
                    if (!priorityPitches.isEmpty()) {
                        pitches.addAll(priorityPitches);
                    } else {
                        for (Pitch p : genrePitches) {
                            if (p.getStatus().getId() == 1 && p.getAsstStatus().getId() == 1) {
                                pitches.add(p);
                            }
                        }
                    }
                }
                break;
            case 2:
                Set<Pitch> priorityPitches = new HashSet<>();
                for (Pitch p : pServ.getAllPitches()) {
                    if (p.getStatus().getId() == 1 && p.getAsstStatus().getId() == 2 && p.getEditorStatus().getId() == 4) {
                        priorityPitches.add(p);
                    }
                }
                if (!priorityPitches.isEmpty()) {
                    pitches.addAll(priorityPitches);
                } else {
                    for (Pitch p : pServ.getAllPitches()) {
                        if (p.getStatus().getId() == 1 && p.getAsstStatus().getId() == 2 && p.getEditorStatus().getId() == 1) {
                            pitches.add(p);
                        }
                    }
                }
                break;
            case 3:
                for (Genre g : u.getGenres()) {
                    Set<Pitch> genrePitches = pServ.getPitchesByGenre(g);
                    for (Pitch p : genrePitches) {
                        if (p.getStatus().getId() == 1 && p.getAsstStatus().getId() == 2 && p.getEditorStatus().getId() == 2) {
                            if (p.getSrStatus().getId() == 1 || p.getSrStatus().getId() == 4) {
                                pitches.add(p);
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
        return pitches;
    }
}
