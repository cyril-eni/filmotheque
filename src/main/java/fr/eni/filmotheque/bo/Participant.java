package fr.eni.filmotheque.bo;

import jakarta.persistence.Entity;

@Entity
public class Participant extends Personne   {

    // on a une relation unidirectionnelle Participant - Film qui est portée par la classe Film
    // => pas besoin de :  private List<Film>  listeFilms;

    /**
     * Lombok ne gère pas bien l'heritage
     * => on doit écrire manuellement le constructeur avec tous les arguments
     */
    public Participant(Long id, String nom, String prenom) {
        super(id, nom, prenom);
    }

    public Participant() {
    }

    public Participant(String nom, String prenom) {
        super(nom, prenom);
    }
}
