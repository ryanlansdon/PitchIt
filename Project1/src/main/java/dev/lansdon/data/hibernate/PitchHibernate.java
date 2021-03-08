package dev.lansdon.data.hibernate;

import dev.lansdon.data.PitchDAO;
import dev.lansdon.models.Genre;
import dev.lansdon.models.Pitch;

import dev.lansdon.models.Role;
import dev.lansdon.models.Story;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import dev.lansdon.utils.HibernateUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PitchHibernate implements PitchDAO {
    HibernateUtil hu = HibernateUtil.getHibernateUtil();
    StoryHibernate sh = new StoryHibernate();

    @Override
    public Pitch getById(Integer id) {
        Session s = hu.getSession();
        Pitch p = s.get(Pitch.class, id);
        s.close();
        return p;
    }

    @Override
    public Set<Pitch> getAll() {
        Session s = hu.getSession();
        String query = "FROM Pitch";
        Query<Pitch> q = s.createQuery(query, Pitch.class);
        List<Pitch> pitchList = q.getResultList();
        Set<Pitch> pitches = new HashSet<>();
        pitches.addAll(pitchList);
        s.close();
        return pitches;
    }

    @Override
    public void update(Pitch pitch) {
        Session s = hu.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.update(pitch);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            s.close();
        }
    }

    @Override
    public void delete(Pitch pitch) {
        Session s = hu.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.delete(pitch);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            s.close();
        }
    }

    @Override
    public Pitch add(Pitch p) {
        Story story = sh.add(p.getStory());
        p.setStory(story);
        Session s = hu.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.save(p);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            s.close();
        }
        return p;
    }

    @Override
    public Set<Pitch> getPitchesByGenre(Genre g) {
        Session s = hu.getSession();
        String query = "FROM Pitch WHERE story.genre.name = :genre";
        Query<Pitch> q = s.createQuery(query, Pitch.class);
        q.setParameter("genre", g.getName());
        List<Pitch> pitchList = q.getResultList();
        Set<Pitch> pitches = new HashSet<>();
        pitches.addAll(pitchList);
        s.close();
        return pitches;
    }

//    @Override
//    public Set<Pitch> getPitchesByRole(Role r) {
//        Session s = hu.getSession();
//        String query;
//        Query<Pitch> q;
//        switch (r.getId()) {
//            case 1:
//                query = "FROM Pitch WHERE status.id=1 AND asstStatus.id=1";
//                break;
//            case 2:
//                query = "FROM Pitch WHERE status.id=1 AND asstStatus.id=2 AND editorStatus.id=1";
//                break;
//            case 3:
//                query = "FROM Pitch WHERE status.id=1 AND asstStatus.id=2 AND editorStatus.id=2 AND srStatus.id=1";
//                break;
//            default:
//                return null;
//        }
//        Query<Pitch> q = s.createQuery(query, Pitch.class);
//        List<Pitch> pitchList = q.getResultList();
//        Set<Pitch> pitches = new HashSet<>();
//        pitches.addAll(pitchList);
//        return pitches;
//    }
}
