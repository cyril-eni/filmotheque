package fr.eni.filmotheque.service;

import fr.eni.filmotheque.bo.Genre;

import java.util.List;

/**
 * Fonctionnalités que doivent implémenter mes services
 */
public interface GenreService {
    public List<Genre> getGenres();

    public Genre getGenreById(long id);

    public void addGenre(Genre genre);

    public void deleteGenreById(long id);
}
