package dev.lansdon.data;

import dev.lansdon.models.Genre;

public interface GenreDAO extends GenericDAO<Genre> {
    public Genre add(Genre g);
    public Genre getByName(String name);
}
