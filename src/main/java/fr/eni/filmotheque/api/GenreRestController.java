package fr.eni.filmotheque.api;

import fr.eni.filmotheque.bo.Genre;
import fr.eni.filmotheque.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin
public class GenreRestController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public List<Genre> getGenre() {
        return genreService.getGenres();
    }

    @PostMapping
    public void addGenre(@RequestBody Genre genre) {
        genreService.addGenre(genre);
    }

    @DeleteMapping("/{idToDelete}")
    public void deleteGenre(@PathVariable long idToDelete) {
        genreService.deleteGenreById(idToDelete);
    }
}
