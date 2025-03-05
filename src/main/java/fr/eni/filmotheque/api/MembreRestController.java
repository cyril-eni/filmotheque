package fr.eni.filmotheque.api;

import fr.eni.filmotheque.bo.Membre;
import fr.eni.filmotheque.service.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membres")
@CrossOrigin
public class MembreRestController {

    @Autowired
    private MembreService membreService;

    @GetMapping
    public List<Membre> getMembre() {
        return membreService.getMembres();
    }

    @PostMapping
    public void addMembre(@RequestBody Membre membre) {
        membreService.addMembre(membre);
    }

    @DeleteMapping("/{idToDelete}")
    public void deleteMembre(@PathVariable long idToDelete) {
        membreService.deleteMembreById(idToDelete);
    }
}
