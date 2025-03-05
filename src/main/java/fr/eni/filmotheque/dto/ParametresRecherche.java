package fr.eni.filmotheque.dto;

import lombok.Data;

/**
 * Classe de mapping pour recupérer dans un seul objet tous les paramètres de la recherche de film
 */
@Data
public class ParametresRecherche {
    private String recherche;
    private Long genre;
    private Long realisateur;
    private Integer dureeMin;
    private Integer dureeMax;
    private Integer anneeMin;
    private Integer anneeMax;

    /**
     * Méthode utilitaire pour savoir si il y a des paramètres de recherche ou non
     */
    public boolean isNotEmpty() {
        return (recherche != null && !recherche.isEmpty()) ||
                genre != null ||
                realisateur != null ||
                dureeMin != null ||
                dureeMax != null ||
                anneeMin != null ||
                anneeMax != null ;
    }
}
