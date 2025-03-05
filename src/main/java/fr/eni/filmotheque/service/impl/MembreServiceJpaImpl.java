package fr.eni.filmotheque.service.impl;

import fr.eni.filmotheque.bo.Membre;
import fr.eni.filmotheque.repository.MembreRepository;
import fr.eni.filmotheque.service.MembreService;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("prod") // cette classe sera utilisée comme implémentation uniquement si le profil actif est "dev"
@Service // On doit annoter notre classe de service avec @Service afin qu'elle soit dans le contexte Spring
public class MembreServiceJpaImpl implements MembreService {


    private MembreRepository membreRepository;
    private PasswordEncoder passwordEncoder;

    // on injecte le password encoder dans le constructeur
    public MembreServiceJpaImpl(MembreRepository membreRepository, PasswordEncoder passwordEncoder) {
        this.membreRepository = membreRepository;
        this.passwordEncoder = passwordEncoder;

        if (membreRepository.findAll().isEmpty()) {
            // on ajoute un membre par défaut
            addMembre(new Membre( "Cyril", "Mace", "cyrilmace", "Pa$$w0rd", true));
        }

    }


    @Override
    public List<Membre> getMembres() {
        return membreRepository.findAll();
    }

    @Override
    public Membre getMembreById(long id) {
        // ATTENTION : getReferenceById(id) retourne un proxy => ca peut provoquer des bugs
        // on retourne le genre correspondant à l'id si on le trouve, sinon null
        return membreRepository.findById(id).orElse(null);
    }

    @Override
    public Membre getMembreByPseudo(String pseudo) {
        return membreRepository.getMembreByPseudo(pseudo);
    }

    @Override
    public void addMembre(Membre membre) {
        membre.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));
        membreRepository.save(membre);
    }

    @Override
    public void deleteMembreById(long id) {
        membreRepository.deleteById(id);
    }
}
