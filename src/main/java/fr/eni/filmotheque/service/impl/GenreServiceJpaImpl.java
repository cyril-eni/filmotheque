package fr.eni.filmotheque.service.impl;

import fr.eni.filmotheque.bo.Genre;
import fr.eni.filmotheque.repository.GenreRepository;
import fr.eni.filmotheque.service.GenreService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("prod") // cette classe sera utilisée comme implémentation uniquement si le profil actif est "dev"
@Service // On doit annoter notre classe de service avec @Service afin qu'elle soit dans le contexte Spring
public class GenreServiceJpaImpl implements GenreService {


    private GenreRepository genreRepository;

    /**
     * On injecte le genreRepository dans le constructeur
     */
    public GenreServiceJpaImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;

        /**
         * Lors du démarrage de l'application, si aucun genre n'est créé : on le fait
         */
        if (genreRepository.findAll().isEmpty()) {
            addGenre(new Genre( "Science-Fiction"));
            addGenre(new Genre("Documentaire"));
            addGenre(new Genre("Action"));
            addGenre(new Genre( "Comédie"));
            addGenre(new Genre( "Drame"));
        }

    }

    @Override
    public List<Genre> getGenres() {

        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(long id) {
       // ATTENTION : getReferenceById(id) retourne un proxy => ca peut provoquer des bugs
       // on retourne le genre correspondant à l'id si on le trouve, sinon null
       return genreRepository.findById(id).orElse(null);
    }

    @Override
    public void addGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }
}
