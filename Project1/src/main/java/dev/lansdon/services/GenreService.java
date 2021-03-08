package dev.lansdon.services;

import dev.lansdon.models.Genre;

import java.util.Set;

public interface GenreService {
    public Genre getGenreById(Integer id);
    public Set<Genre> getAllGenres();
    public Genre getGenreByName(String name);
}
