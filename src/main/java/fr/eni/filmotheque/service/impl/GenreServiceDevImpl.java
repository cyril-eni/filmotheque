package fr.eni.filmotheque.service.impl;

import fr.eni.filmotheque.bo.Genre;
import fr.eni.filmotheque.service.GenreService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("dev") // cette classe sera utilisée comme implémentation uniquement si le profil actif est "dev"
@Service // On doit annoter notre classe de service avec @Service afin qu'elle soit dans le contexte Spring
public class GenreServiceDevImpl implements GenreService {

    // pour l'implémentation de Dev, on gère les genres en mémoire Java
    List<Genre> genres = new ArrayList<>();

    // pour simuler le compteur d'id, je crée une variable
    private int idCourant = 7;

    public GenreServiceDevImpl() {

        genres.add(new Genre(1L, "Science-Fiction"));
        genres.add(new Genre(2L, "Documentaire"));
        genres.add(new Genre(3L, "Action"));
        genres.add(new Genre(4L, "Comédie"));
        genres.add(new Genre(5L, "Drame"));
    }

    @Override
    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public Genre getGenreById(long id) {
        for (Genre genre : genres) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        return null;
    }

    @Override
    public void addGenre(Genre genre) {
        genre.setId((long) idCourant++);
        genres.add(genre);
    }

    @Override
    public void deleteGenreById(long id) {
        // je parcours ma liste
        for (int i = 0; i < genres.size(); i++) {
            // si jamais hje trouve le genre d'id cherché
            if (genres.get(i).getId() == id) {
                // je le supprime
                genres.remove(i);
            }
        }
    }
}
