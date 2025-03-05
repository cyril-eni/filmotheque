package fr.eni.filmotheque.controller;

import fr.eni.filmotheque.bo.Membre;
import fr.eni.filmotheque.service.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/membres")
public class MembreController {

    @Autowired
    private MembreService membreService;

    @GetMapping
    public String getMembre(Model model) {
        // je mets dans mon modèle la liste des membres
        model.addAttribute("membres", membreService.getMembres());

        // je mets dans mon modèle un membre qui sera rempli par mon formulaire
        model.addAttribute("membre", new Membre());

        // je redirige sur le template "membres.html"
        return "membres";
    }

    @PostMapping
    public String addMembre(Membre membre) {
        membreService.addMembre(membre);
        return "redirect:/membres";
    }

    @PostMapping("/delete")
    public String deleteMembre(int idToDelete) {
        membreService.deleteMembreById(idToDelete);
        return "redirect:/membres";
    }
}
