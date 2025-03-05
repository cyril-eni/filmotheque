package fr.eni.filmotheque.service.impl;

import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.service.ParticipantService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("dev") // cette classe sera utilisée comme implémentation uniquement si le profil actif est "dev"
@Service // On doit annoter notre classe de service avec @Service afin qu'elle soit dans le contexte Spring
public class ParticipantServiceDevImpl implements ParticipantService {

    // pour l'implémentation de Dev, on gère les participants en mémoire Java
    List<Participant> participants = new ArrayList<>();

    // pour simuler le compteur d'id, je crée une variable
    private int idCourant = 10;

    public ParticipantServiceDevImpl() {
        participants.add(new Participant(1L, "Spielberg", "Steven"));
        participants.add(new Participant(2L, "Cronenberg", "David"));
        participants.add(new Participant(3L, "Boon", "Dany"));

        // 2 acteurs par film et l'un d'eux dans 2 films
        participants.add(new Participant(4L, "Attenborough", "Richard"));
        participants.add(new Participant(5L, "Goldblum", "Jeff"));
        participants.add(new Participant(6L, "Davis", "Geena"));
        participants.add(new Participant(7L, "Rylance", "Mark"));
        participants.add(new Participant(8L, "Barnhill", "Ruby"));
        participants.add(new Participant(9L, "Merad", "Kad"));
    }

    @Override
    public List<Participant> getParticipants() {
        return participants;
    }

    @Override
    public Participant getParticipantById(long id) {
        for (Participant participant : participants) {
            if (participant.getId() == id) {
                return participant;
            }
        }
        return null;
    }

    @Override
    public void addParticipant(Participant participant) {
        participant.setId(idCourant++);
        participants.add(participant);
    }

    @Override
    public void deleteParticipantById(long id) {
        // je parcours ma liste
        for (int i = 0; i < participants.size(); i++) {
            // si jamais hje trouve le participant d'id cherché
            if (participants.get(i).getId() == id) {
                // je le supprime
                participants.remove(i);
            }
        }
    }
}
