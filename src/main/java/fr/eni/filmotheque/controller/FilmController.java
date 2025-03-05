package fr.eni.filmotheque.controller;

import fr.eni.filmotheque.bo.Avis;
import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.bo.Genre;
import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.dto.ParametresRecherche;
import fr.eni.filmotheque.security.UtilisateurSpringSecurity;
import fr.eni.filmotheque.service.FilmService;
import fr.eni.filmotheque.service.GenreService;
import fr.eni.filmotheque.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/films")
public class FilmController {

    //FilmController doit referencer filmService
    @Autowired
    private FilmService   filmService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ParticipantService participantService;

    /*
     * Attributs du modèle partagés par toutes les méthodes
     */
    // on va définir un attribut de modèle "genres" pour toutes les méthodes de mon controller
    @ModelAttribute("genres")public List<Genre> getListeGenres(){
        return genreService.getGenres();
    }
    // on va définir un attribut de modèle "participants" pour toutes les méthodes de mon controller
    @ModelAttribute("participants")public List<Participant> getListeParticipants(){
        return participantService.getParticipants();
    }


    @GetMapping
    public String films(ParametresRecherche parametresRecherche, Model model) {
        // j'ajoute dans le modèle la liste des films
        model.addAttribute("films", filmService.consulterFilms());

        // si jamais j'ai des paramètres de recherche
        if (parametresRecherche.isNotEmpty()) {
            // alors je fait une recherche de films
            model.addAttribute("films", filmService.rechercherFilms(parametresRecherche));
        }

        // j'ajoute parametresRecherche au model pour pouvoir utiliser th:object et th:field
        model.addAttribute("parametresRecherche",parametresRecherche);

        // je redirige sur le template "films.html"
        return "films";
    }

    @GetMapping("/nouveau")
    public String film(Model model) {
        // je mets dans mon modèle un film qui sera rempli par mon formulaire
        model.addAttribute("film", new Film());

        // je redirige sur le template "film.html" (qui va devoir être génerique pour être utilisé sur la page de détail et de création de film)
        return "film";
    }


    @GetMapping("/{id}")
    public String film(@PathVariable int id, Model model) {
        // j'ajoute au modèle le film d'id correspondant
        model.addAttribute("film", filmService.consulterFilmParId(id));
        // je redirige sur le template "film.html" (qui va devoir être génerique pour être utilisé sur la page de détail et de création de film)
        return "film";
    }

    @PostMapping
    public String film(@Valid Film film, BindingResult result, Model model) {

        // Validation
        if (result.hasErrors()) {
            return "film";
        }
        filmService.creerFilm(film);
        // si création Ok, je redirige sur la page des films en GET
        return "redirect:/films";
    }



    /***********************************************************
     * *************  Gestion avis **********************
     * Je vais toujours accéder à la création d'un avis via un film
     * donc je peux mettre les méthodes correspondantes dans le controiller de Film
     ************************************************************/


    @GetMapping("/{id}/avis")
    public String getAvis(@PathVariable int id, Model model) {
        // j'ajoute au modèle le film d'id correspondant
        model.addAttribute("film", filmService.consulterFilmParId(id));
        // je rajoute au modèle un avis vide qui sera rempli par le formulaire (th:object)
        model.addAttribute("avis", new Avis());
        // je redirige sur le template "avis.html"
        return "avis";
    }


    /**
     * on utilise idFilm plutôt que id dans @PathVariable pour eviter un bug qui automatiquement ajoute l'id à avis
     */
    @PostMapping("/{idFilm}/avis")
    public String postAvis(@PathVariable int idFilm, Avis avis, @AuthenticationPrincipal UtilisateurSpringSecurity utilisateurConnecte) {

        // 1 - j'ajoute à l'avis recuperé le membre correspondant à l'utilisateur connecté
        avis.setMembre(utilisateurConnecte.getMembre());

        // 2 - j'ajoute l'avis au film via FilmService
        filmService.publierAvis(idFilm, avis);

        // je fais un redirect sur la page de détail du film (où on va pouvoir voir les avis)
        return "redirect:/films/" + idFilm;
    }


}
