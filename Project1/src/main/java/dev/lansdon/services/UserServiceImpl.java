package dev.lansdon.services;

import dev.lansdon.data.DAOFactory;
import dev.lansdon.data.UserDAO;
import dev.lansdon.exceptions.NonUniqueUsernameException;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.User;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = DAOFactory.getUserDAO();
    }

    @Override
    public Integer calculatePoints(User u) {
        Integer points = 0;
        for (Pitch p : u.getPitches()) {
            points += p.getStory().getType().getWeight();
        }
        return points;
    }

    @Override
    public User addUser(User u) throws NonUniqueUsernameException {
        return userDAO.add(u);
    }

    @Override
    public User getUserById(Integer id) {
        User u = userDAO.getById(id);
        u.setPoints(calculatePoints(u));
        return u;
    }

    @Override
    public User getUserByUsername(String username) {
        User u = userDAO.getByUsername(username);
        u.setPoints(calculatePoints(u));
        return u;
    }

    @Override
    public void updateUser(User u) {
        u.setPoints(calculatePoints(u));
        userDAO.update(u);
    }
}
