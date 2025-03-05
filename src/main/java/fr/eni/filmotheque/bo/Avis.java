package fr.eni.filmotheque.bo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int note;
    private String commentaire;

    /**
     * Représentation des associations
     */
    @ManyToOne
    private Membre membre;

    // on a une relation unidirectionnelle Avis - Film qui est portée par la classe Film
    // => pas besoin de :  private List<Film>  listeFilms;
}
