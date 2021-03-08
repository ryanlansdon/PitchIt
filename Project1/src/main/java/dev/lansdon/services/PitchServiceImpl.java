package dev.lansdon.services;

import dev.lansdon.data.DAOFactory;
import dev.lansdon.data.PitchDAO;
import dev.lansdon.models.Genre;
import dev.lansdon.models.Pitch;

import java.util.Set;

public class PitchServiceImpl implements PitchService {
    private PitchDAO pitchDAO;

    public PitchServiceImpl() {
        pitchDAO = DAOFactory.getPitchDAO();
    }

    @Override
    public Integer createPitch(Pitch p) {
        return pitchDAO.add(p).getId();
    }

    @Override
    public void updatePitch(Pitch p) {
        pitchDAO.update(p);
    }

    @Override
    public Pitch getPitchById(Integer id) {
        return pitchDAO.getById(id);
    }

    @Override
    public Set<Pitch> getPitchesByGenre(Genre g) {
        return pitchDAO.getPitchesByGenre(g);
    }

    @Override
    public Set<Pitch> getAllPitches() {
        return pitchDAO.getAll();
    }
}
