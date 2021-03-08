package dev.lansdon.data.hibernate;

import dev.lansdon.data.InfoRequestDAO;
import dev.lansdon.models.InfoRequest;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.User;
import dev.lansdon.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InfoRequestHibernate implements InfoRequestDAO {
    HibernateUtil hu = HibernateUtil.getHibernateUtil();

    @Override
    public InfoRequest getById(Integer id) {
        Session s = hu.getSession();
        InfoRequest ir = s.get(InfoRequest.class, id);
        s.close();
        return ir;
    }

    @Override
    public Set<InfoRequest> getAll() {
        Session s = hu.getSession();
        String query = "FROM InfoRequest";
        Query<InfoRequest> q = s.createQuery(query, InfoRequest.class);
        List<InfoRequest> infoRequestList = q.getResultList();
        Set<InfoRequest> requests = new HashSet<>();
        requests.addAll(infoRequestList);
        s.close();
        return requests;
    }

    @Override
    public void update(InfoRequest infoRequest) {
        Session s = hu.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.update(infoRequest);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            s.close();
        }
    }

    @Override
    public void delete(InfoRequest infoRequest) {
        Session s = hu.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.delete(infoRequest);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            s.close();
        }
    }

    @Override
    public InfoRequest add(InfoRequest ir) {
        Session s = hu.getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.save(ir);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            s.close();
        }
        return ir;
    }
}
