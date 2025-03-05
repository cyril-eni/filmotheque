package fr.eni.filmotheque.service.impl;

import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.repository.ParticipantRepository;
import fr.eni.filmotheque.service.ParticipantService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("prod") // cette classe sera utilisée comme implémentation uniquement si le profil actif est "dev"
@Service // On doit annoter notre classe de service avec @Service afin qu'elle soit dans le contexte Spring
public class ParticipantServiceJpaImpl implements ParticipantService {

    private ParticipantRepository participantRepository;

    /**
     * On injecte le genreRepository dans le constructeur
     */
    public ParticipantServiceJpaImpl(ParticipantRepository participantRepository) {

        this.participantRepository = participantRepository;

        /**
         * Lors du démarrage de l'application, si aucun participant n'est créé : on le fait
         */
        if (participantRepository.findAll().isEmpty()) {
            addParticipant(new Participant("Spielberg", "Steven"));
            addParticipant(new Participant("Cronenberg", "David"));
            addParticipant(new Participant("Boon", "Dany"));

            // 2 acteurs par film et l'un d'eux dans 2 films
            addParticipant(new Participant("Attenborough", "Richard"));
            addParticipant(new Participant("Goldblum", "Jeff"));
            addParticipant(new Participant("Davis", "Geena"));
            addParticipant(new Participant("Rylance", "Mark"));
            addParticipant(new Participant("Barnhill", "Ruby"));
            addParticipant(new Participant("Merad", "Kad"));
        }

    }

    @Override
    public List<Participant> getParticipants() {

        return participantRepository.findAll();
    }

    @Override
    public Participant getParticipantById(long id) {
        // ATTENTION : getReferenceById(id) retourne un proxy => ca peut provoquer des bugs
        // on retourne le genre correspondant à l'id si on le trouve, sinon null
        return participantRepository.findById(id).orElse(null);
    }

    @Override
    public void addParticipant(Participant participant) {
        participantRepository.save(participant);
    }

    @Override
    public void deleteParticipantById(long id) {
        participantRepository.deleteById(id);
    }
}
