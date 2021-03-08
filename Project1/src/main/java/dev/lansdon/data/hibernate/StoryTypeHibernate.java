package dev.lansdon.data.hibernate;

import dev.lansdon.data.StoryTypeDAO;
import dev.lansdon.models.StoryType;
import dev.lansdon.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoryTypeHibernate implements StoryTypeDAO {
    HibernateUtil hu = HibernateUtil.getHibernateUtil();

    @Override
    public StoryType getById(Integer id) {
        Session s = hu.getSession();
        StoryType st = s.get(StoryType.class, id);
        s.close();
        return st;
    }

    @Override
    public Set<StoryType> getAll() {
        Session s = hu.getSession();
        String query = "FROM StoryType";
        Query<StoryType> q = s.createQuery(query, StoryType.class);
        List<StoryType> storyTypeList = q.getResultList();
        Set<StoryType> storyTypes = new HashSet<>();
        storyTypes.addAll(storyTypeList);
        s.close();
        return storyTypes;
    }

    @Override
    public void update(StoryType storyType) {

    }

    @Override
    public void delete(StoryType storyType) {

    }

    @Override
    public StoryType add(StoryType st) {
        return null;
    }

    @Override
    public StoryType getByName(String name) {
        Session s = hu.getSession();
        String query = "FROM StoryType WHERE name = :name";
        Query<StoryType> q = s.createQuery(query, StoryType.class);
        q.setParameter("name", name);
        StoryType st = q.getSingleResult();
        s.close();
        return st;
    }
}
