package dev.lansdon.data;

import dev.lansdon.exceptions.NonUniqueUsernameException;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.User;

public interface UserDAO extends GenericDAO<User> {
    public User add(User u) throws NonUniqueUsernameException;
    public User getByUsername(String username);
}
