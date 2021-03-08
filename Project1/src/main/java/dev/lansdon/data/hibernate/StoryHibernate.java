package dev.lansdon.data.hibernate;

import dev.lansdon.data.StoryDAO;
import dev.lansdon.models.Story;

import dev.lansdon.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import java.util.Set;

public class StoryHibernate implements StoryDAO {
    private HibernateUtil hu = HibernateUtil.getHibernateUtil();
    private GenreHibernate gh = new GenreHibernate();
    private StoryTypeHibernate sth = new StoryTypeHibernate();

    @Override
    public Story getById(Integer id) {
        return null;
    }

    @Override
    public Set<Story> getAll() {
        return null;
    }

    @Override
    public void update(Story story) {

    }

    @Override
    public void delete(Story story) {

    }

    @Override
    public Story add(Story s) {
        s.setGenre(gh.getByName(s.getGenre().getName()));
        s.setType(sth.getByName(s.getType().getName()));
        Session ses = hu.getSession();
        Transaction tx = null;
        try {
            tx = ses.beginTransaction();
            ses.save(s);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            ses.close();
        }
        return s;
    }
}
