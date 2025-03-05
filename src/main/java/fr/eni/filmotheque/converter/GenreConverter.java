package fr.eni.filmotheque.converter;


import fr.eni.filmotheque.bo.Genre;
import fr.eni.filmotheque.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * CONVERTER
 *
 * Va être appelé AUTOMATIQUEMENT quand Spring a besoin de convertir uune information depuis String -> Genre
 */

@Component // attention à bien mettre le converter dans le contexte Spring
public class GenreConverter implements Converter<String, Genre> {

    @Autowired
    private GenreService genreService;

   /*
   * Comment je passe d'un paramètre d'entrée au format texte jusqu'à un objet Genre?
    */
    @Override
    public Genre convert(String idAuFormatTexte) {
        // on doit utiliser Integer.parseInt() pour recupérer l'entier depuis le texte (car HTTP envoie des paramètres au format texte)
        return genreService.getGenreById(Integer.parseInt(idAuFormatTexte));
    }
}
