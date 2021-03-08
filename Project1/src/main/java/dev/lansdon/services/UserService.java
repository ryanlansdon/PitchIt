package dev.lansdon.services;

import dev.lansdon.exceptions.NonUniqueUsernameException;
import dev.lansdon.models.User;

public interface UserService {
    public User addUser(User u) throws NonUniqueUsernameException;
    public User getUserById(Integer id);
    public User getUserByUsername(String username);
    public void updateUser(User u);
    public Integer calculatePoints(User u);
}
