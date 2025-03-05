package fr.eni.filmotheque.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Membre  extends Personne   {
    private String pseudo;
    @JsonIgnore // pour ne pas afficher le mot de passe dans le JSON (potentiellement pas très securisé)
    private String motDePasse;
    private boolean admin;

    // on a une relation unidirectionnelle Avis - Membre qui est portée par la classe Avis
    // => pas besoin de :  private List<Avis>  listeAvis;

    /**
     * Je crée manuellement un constructeur qui prend tous les attributs sauf 1
     * Lombok ne génère que les constructeurs avec tous / aucun attribut
     */
    public Membre(long id, String nom, String prenom, String pseudo, String motDePasse) {
        super(id, nom, prenom);
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public Membre(long id, String nom, String prenom, String pseudo, String motDePasse, boolean admin) {
        super(id, nom, prenom);
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.admin = admin;
    }

    public Membre(String nom, String prenom, String pseudo, String motDePasse, boolean admin) {
        super(nom, prenom);
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.admin = admin;
    }
}
