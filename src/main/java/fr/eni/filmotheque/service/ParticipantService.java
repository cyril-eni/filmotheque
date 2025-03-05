package fr.eni.filmotheque.service;

import fr.eni.filmotheque.bo.Participant;

import java.util.List;

/**
 * Fonctionnalités que doivent implémenter mes services
 */
public interface ParticipantService {
    public List<Participant> getParticipants();

    public Participant getParticipantById(long id);

    public void addParticipant(Participant participanrt);

    public void deleteParticipantById(long id);
}
