package fr.eni.filmotheque.api;

import fr.eni.filmotheque.bo.Avis;
import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.dto.ParametresRecherche;
import fr.eni.filmotheque.security.UtilisateurSpringSecurity;
import fr.eni.filmotheque.service.FilmService;
import fr.eni.filmotheque.service.GenreService;
import fr.eni.filmotheque.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
@CrossOrigin
public class FilmRestController {

    //FilmController doit referencer filmService
    @Autowired
    private FilmService   filmService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ParticipantService participantService;


    @GetMapping
    public List<Film> films( ParametresRecherche parametresRecherche) {

        // si jamais j'ai des paramètres de recherche
        if (parametresRecherche.isNotEmpty()) {
            // alors je fait une recherche de films
            return filmService.rechercherFilms(parametresRecherche);
        }
        else {
            return filmService.consulterFilms();
        }
    }


    @GetMapping("/{id}")
    public Film film(@PathVariable int id) {
        return filmService.consulterFilmParId(id);
    }

    @PostMapping
    public void film(@RequestBody Film film) {
        filmService.creerFilm(film);
    }



    /***********************************************************
     * *************  Gestion avis **********************
     * Je vais toujours accéder à la création d'un avis via un film
     * donc je peux mettre les méthodes correspondantes dans le controiller de Film
     ************************************************************/

    /**
     * on utilise idFilm plutôt que id dans @PathVariable pour eviter un bug qui automatiquement ajoute l'id à avis
     */
    @PostMapping("/{idFilm}/avis")
    public void postAvis(@PathVariable int idFilm, @RequestBody Avis avis, @AuthenticationPrincipal UtilisateurSpringSecurity utilisateurConnecte) {

        // 1 - j'ajoute à l'avis recuperé le membre correspondant à l'utilisateur connecté
        avis.setMembre(utilisateurConnecte.getMembre());

        // 2 - j'ajoute l'avis au film via FilmService
        filmService.publierAvis(idFilm, avis);
    }


}
