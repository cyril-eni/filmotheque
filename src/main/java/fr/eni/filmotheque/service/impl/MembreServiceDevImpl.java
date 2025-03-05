package fr.eni.filmotheque.service.impl;

import fr.eni.filmotheque.bo.Membre;
import fr.eni.filmotheque.service.MembreService;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("dev") // cette classe sera utilisée comme implémentation uniquement si le profil actif est "dev"
@Service // On doit annoter notre classe de service avec @Service afin qu'elle soit dans le contexte Spring
public class MembreServiceDevImpl implements MembreService {

    PasswordEncoder passwordEncoder;

    // pour l'implémentation de Dev, on gère les membres en mémoire Java
    List<Membre> membres = new ArrayList<>();

    // pour simuler le compteur d'id, je crée une variable
    private int idCourant = 2;

    // on injecte le password encoder dans le constructeur
    public MembreServiceDevImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        // on ajoute un membre par défaut
        membres.add(new Membre(1L, "Cyril", "Mace", "cyrilmace", passwordEncoder.encode("Pa$$w0rd"), true));
    }

    @Override
    public List<Membre> getMembres() {
        return membres;
    }

    @Override
    public Membre getMembreById(long id) {
        for (Membre membre : membres) {
            if (membre.getId() == id) {
                return membre;
            }
        }
        return null;
    }

    @Override
    public Membre getMembreByPseudo(String pseudo) {
        for (Membre membre : membres) {
            if (membre.getPseudo().equals(pseudo)) {
                return membre;
            }
        }
        return null;
    }

    @Override
    public void addMembre(Membre membre) {
        membre.setId(idCourant++);

        // ATTENTION ! il faut bien faire attention à encoder le mot de passe avant d'jouter le membre
        membre.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));
        membres.add(membre);
    }

    @Override
    public void deleteMembreById(long id) {
        // je parcours ma liste
        for (int i = 0; i < membres.size(); i++) {
            // si jamais hje trouve le membre d'id cherché
            if (membres.get(i).getId() == id) {
                // je le supprime
                membres.remove(i);
            }
        }
    }
}
