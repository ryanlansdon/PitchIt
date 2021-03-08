package dev.lansdon.data.hibernate;

import dev.lansdon.data.GenreDAO;
import dev.lansdon.models.Genre;
import dev.lansdon.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenreHibernate implements GenreDAO {
    HibernateUtil hu = HibernateUtil.getHibernateUtil();

    @Override
    public Genre getById(Integer id) {
        Session s = hu.getSession();
        Genre g = s.get(Genre.class, id);
        s.close();
        return g;
    }

    @Override
    public Set<Genre> getAll() {
        Session s = hu.getSession();
        String query = "FROM Genre";
        Query<Genre> q = s.createQuery(query, Genre.class);
        List<Genre> genreList = q.getResultList();
        Set<Genre> genres = new HashSet<>();
        genres.addAll(genreList);
        s.close();
        return genres;
    }

    @Override
    public void update(Genre genre) {

    }

    @Override
    public void delete(Genre genre) {

    }

    @Override
    public Genre add(Genre g) {
        return null;
    }

    @Override
    public Genre getByName(String name) {
        Session s = hu.getSession();
        String query = "FROM Genre WHERE name = :name";
        Query<Genre> q = s.createQuery(query, Genre.class);
        q.setParameter("name", name);
        Genre g = q.getSingleResult();
        s.close();
        return g;
    }
}
