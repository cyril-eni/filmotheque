package fr.eni.filmotheque.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe à compléter avec création classes Membre, Personne, Genre etc....
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String titre;
    @Min(1900)
    private Integer annee;
    @Min(0)
    private Integer duree;
    @Size(min = 5, max = 500, message = "vous devez mettre entre 5 et 500 caractères pour la description")
    private String synopsis;

    /**
     * Je crée manuellement un constructeur qui prend une partie seulement des arguments
     * Lombok ne génère que les constructeurs avec tous / aucun attribut
     */
    public Film(Long id, String titre, Integer annee, Integer duree, String synopsis) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.duree = duree;
        this.synopsis = synopsis;
    }

    public Film(String titre, Integer annee, Integer duree, String synopsis) {
        this.titre = titre;
        this.annee = annee;
        this.duree = duree;
        this.synopsis = synopsis;
    }

    /**
     * Représentation des associations
     */

    @ManyToOne
    private Genre genre;

    // lorsqu'on supprime un film, ca supprime les avis correspondant en base de donnée
    // => on rajoute cascade = CascadeType.REMOVE
    @OneToMany(cascade = CascadeType.REMOVE)
    // il n'y a pas de table de jointure mais une colonne de jointure pour l'association Film -> Avis
    @JoinColumn(name = "film_id")
    private List<Avis> avis = new ArrayList<>();

    @ManyToOne
    private Participant realisateur;

    @ManyToMany
    private List<Participant> acteurs =  new ArrayList<>();
}
