package dev.lansdon.services;

import dev.lansdon.data.DAOFactory;
import dev.lansdon.data.GenreDAO;
import dev.lansdon.models.Genre;

import java.util.Set;

public class GenreServiceImpl implements GenreService {
    private GenreDAO genreDAO;

    public GenreServiceImpl() {
        genreDAO = DAOFactory.getGenreDAO();
    }

    @Override
    public Genre getGenreById(Integer id) {
        return genreDAO.getById(id);
    }

    @Override
    public Set<Genre> getAllGenres() {
        return genreDAO.getAll();
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreDAO.getByName(name);
    }
}
