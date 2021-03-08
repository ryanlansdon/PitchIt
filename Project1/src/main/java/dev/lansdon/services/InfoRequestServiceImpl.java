package dev.lansdon.services;

import dev.lansdon.data.DAOFactory;
import dev.lansdon.data.InfoRequestDAO;
import dev.lansdon.data.UserDAO;
import dev.lansdon.models.InfoRequest;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.User;

import java.util.Set;

public class InfoRequestServiceImpl implements InfoRequestService {
    InfoRequestDAO irDAO;
    UserDAO uDAO;

    public InfoRequestServiceImpl() {
        irDAO = DAOFactory.getInfoRequestDAO();
        uDAO = DAOFactory.getUserDAO();
    }

    @Override
    public InfoRequest addRequestByUsers(User requested, User editor, InfoRequest ir) {
        ir = irDAO.add(ir);
        Set<InfoRequest> irIn = requested.getInRequests();
        irIn.add(ir);
        uDAO.update(requested);
        Set<InfoRequest> irOut = editor.getOutRequests();
        irOut.add(ir);
        uDAO.update(editor);
        return ir;
    }

    @Override
    public InfoRequest addRequestByPitch(User editor, Pitch pitch, InfoRequest ir) {
        ir = irDAO.add(ir);
        Set<InfoRequest> irOut = editor.getOutRequests();
        irOut.add(ir);
        uDAO.update(editor);
        User author = uDAO.getById(pitch.getAuthor());
        Set<InfoRequest> irIn = author.getInRequests();
        irIn.add(ir);
        uDAO.update(author);
        return ir;
    }

    @Override
    public void updateRequest(InfoRequest ir) {
        irDAO.update(ir);
    }
}
