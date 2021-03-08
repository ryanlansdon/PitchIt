package dev.lansdon.data.hibernate;

import dev.lansdon.data.UserDAO;
import dev.lansdon.exceptions.NonUniqueUsernameException;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import dev.lansdon.utils.HibernateUtil;

public class UserHibernate implements UserDAO {
    private HibernateUtil hu = HibernateUtil.getHibernateUtil();

    @Override
    public User getById(Integer id) {
        Session s = hu.getSession();
        User u = s.get(User.class, id);
        s.close();
        return u;
    }

    @Override
    public Set<User> getAll() {
        Session s = hu.getSession();
        String query = "FROM User";
        Query<User> q = s.createQuery(query, User.class);
        List<User> userList = q.getResultList();
        Set<User> users = new HashSet<>();
        users.addAll(userList);
        s.close();
        return users;
    }

    @Override
    public void update(User user) {
        Session s = hu.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.update(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            s.close();
        }
    }

    @Override
    public void delete(User user) {
        Session s = hu.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.delete(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            s.close();
        }
    }

    @Override
    public User add(User u) throws NonUniqueUsernameException {
        if (this.getByUsername(u.getUsername()) == null) {
            Session s = hu.getSession();
            Transaction tx = null;
            try {
                tx = s.beginTransaction();
                s.save(u);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
            } finally {
                s.close();
            }
            return u;
        } else {
            throw new NonUniqueUsernameException();
        }
    }

    @Override
    public User getByUsername(String username) {
        User u = null;
        Session s = hu.getSession();
        String query = "FROM User WHERE username = :username";
        Query<User> q = s.createQuery(query, User.class);
        q.setParameter("username", username);
        try {
            u = q.getSingleResult();
        } catch (Exception e) {

        }
        s.close();
        return u;
    }
}
