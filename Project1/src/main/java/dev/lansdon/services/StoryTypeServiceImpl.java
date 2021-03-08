package dev.lansdon.services;

import dev.lansdon.data.DAOFactory;
import dev.lansdon.data.StoryTypeDAO;
import dev.lansdon.models.StoryType;

import java.util.Set;

public class StoryTypeServiceImpl implements StoryTypeService {
    private StoryTypeDAO stDAO;

    public StoryTypeServiceImpl() {
        stDAO = DAOFactory.getStoryTypeDAO();
    }

    @Override
    public StoryType getStoryTypeById(Integer id) {
        return stDAO.getById(id);
    }

    @Override
    public Set<StoryType> getAllStoryTypes() {
        return stDAO.getAll();
    }

    @Override
    public StoryType getStoryTypeByName(String name) {
        return stDAO.getByName(name);
    }
}
