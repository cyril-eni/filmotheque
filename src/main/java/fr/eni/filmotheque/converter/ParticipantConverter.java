package fr.eni.filmotheque.converter;


import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * CONVERTER
 *
 * Va être appelé AUTOMATIQUEMENT quand Spring a besoin de convertir uune information depuis String -> Genre
 */

@Component // attention à bien mettre le converter dans le contexte Spring
public class ParticipantConverter implements Converter<String, Participant> {

    @Autowired
    private ParticipantService participantService;

   /*
   * Comment je passe d'un paramètre d'entrée au format texte jusqu'à un objet Participant?
    */
    @Override
    public Participant convert(String idAuFormatTexte) {
        // on doit utiliser Integer.parseInt() pour recupérer l'entier depuis le texte (car HTTP envoie des paramètres au format texte)
        return participantService.getParticipantById(Integer.parseInt(idAuFormatTexte));
    }
}
