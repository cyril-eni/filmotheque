package fr.eni.filmotheque.api;

import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@CrossOrigin
public class ParticipantRestController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public List<Participant> getParticipant() {
        return participantService.getParticipants();
    }

    @PostMapping
    public void addParticipant(@RequestBody Participant participant) {
        participantService.addParticipant(participant);
    }

    @DeleteMapping("/{idToDelete}")
    public void deleteParticipant(@PathVariable int idToDelete) {
        participantService.deleteParticipantById(idToDelete);
    }
}
