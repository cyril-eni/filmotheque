package fr.eni.filmotheque.dto;

import fr.eni.filmotheque.bo.Personne;
import lombok.Data;

/**
 * Structure qui permet de passer des membres avec @requestBody
 * Car comme y'a JSONIgnore sur l'attribut motDePasse de Membre, le @RequestBody ne va pas bien fonctionner
 */
@Data
public class MembreDto extends Personne {
    private String pseudo;
    private String motDePasse;
}
