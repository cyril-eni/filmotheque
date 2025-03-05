package fr.eni.filmotheque.controller;

import fr.eni.filmotheque.bo.Genre;
import fr.eni.filmotheque.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String getGenre(Model model) {
        // je mets dans mon modèle la liste des genres
        model.addAttribute("genres", genreService.getGenres());

        // je mets dans mon modèle un genre qui sera rempli par mon formulaire
        model.addAttribute("genre", new Genre());

        // je redirige sur le template "genres.html"
        return "genres";
    }

    @PostMapping
    public String addGenre(Genre genre) {
        genreService.addGenre(genre);
        return "redirect:/genres";
    }

    @PostMapping("/delete")
    public String deleteGenre(int idToDelete) {
        genreService.deleteGenreById(idToDelete);
        return "redirect:/genres";
    }
}
