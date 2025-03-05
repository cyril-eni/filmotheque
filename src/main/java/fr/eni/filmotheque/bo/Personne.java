package fr.eni.filmotheque.bo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @MappedSuperclass : on écrit ca afin qu'il n'y ait pas de table de créée pour Personne
// mais une table separée pour ses classes filles (Membre et Participant)
@MappedSuperclass
public abstract class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String prenom;

    /**
     * En surchargeant toString() je fais en sorte qu'un partcipant s'affiche avec le format prenom Nom par défaut
     * @return
     */
    @Override
    public String toString() {
        return prenom + " " + nom;
    }

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
}
