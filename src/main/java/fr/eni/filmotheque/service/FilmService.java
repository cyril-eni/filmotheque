package fr.eni.filmotheque.service;

import fr.eni.filmotheque.bo.Avis;
import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.dto.ParametresRecherche;

import java.util.List;

public interface FilmService {


    public List<Film> consulterFilms();

    public Film consulterFilmParId(long id);

    public void creerFilm(Film film) ;

    public void publierAvis(long filmId, Avis avis) ;

    public List<Film>  rechercherFilms(ParametresRecherche parametresRecherche);
}
