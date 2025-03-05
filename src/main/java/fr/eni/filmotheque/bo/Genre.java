package fr.eni.filmotheque.bo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    // on a une relation unidirectionnelle Genre - Film qui est portée par la classe Film
    // => pas besoin de :  private List<Film>  listeFilms;


    public Genre(String titre) {
        this.titre = titre;
    }
}
