package fr.eni.filmotheque.service;

import fr.eni.filmotheque.bo.Membre;

import java.util.List;

/**
 * Fonctionnalités que doivent implémenter mes services
 */
public interface MembreService {
    public List<Membre> getMembres();

    public Membre getMembreById(long id);

    public Membre getMembreByPseudo(String pseudo);

    public void addMembre(Membre participanrt);

    public void deleteMembreById(long id);
}
