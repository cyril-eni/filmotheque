package fr.eni.filmotheque.controller;

import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public String getParticipant(Model model) {
        // je mets dans mon modèle la liste des participants
        model.addAttribute("participants", participantService.getParticipants());

        // je mets dans mon modèle un participant qui sera rempli par mon formulaire
        model.addAttribute("participant", new Participant());

        // je redirige sur le template "participants.html"
        return "participants";
    }

    @PostMapping
    public String addParticipant(Participant participant) {
        participantService.addParticipant(participant);
        return "redirect:/participants";
    }

    @PostMapping("/delete")
    public String deleteParticipant(int idToDelete) {
        participantService.deleteParticipantById(idToDelete);
        return "redirect:/participants";
    }
}
