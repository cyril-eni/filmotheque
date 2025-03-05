package fr.eni.filmotheque.repository;

import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.dto.ParametresRecherche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    /**
     * Requête JPQL qui va effectuer la recherche selon les critères de
     * l'objet : parametresRecherche
     */
    @Query("select f from Film f " +
            "left join f.acteurs a " +

            // je m'occupe de la partie barre de recherche (qui recherche le terme dans titre / genre / realisateur / acteurs)
            "where (" +
                ":#{#param.recherche} is NULL " +
                "OR f.titre like :#{#param.recherche}% " +
                "OR f.genre.titre like :#{#param.recherche}% " +
                "OR f.realisateur.prenom like :#{#param.recherche}% " +
                "OR f.realisateur.nom like :#{#param.recherche}% " +
                "OR a.prenom like :#{#param.recherche}% " +
                "OR a.nom like :#{#param.recherche}% " +
            ") " +
            // je m'occupe de la partie "filtres" (par genre / realisateur / duree min/max / annee min/max)

            // si jamais un genre est specifié, il faut le rajouter en conditions supplémentaires
            // critère supplémentaire (AND) => soit le genre n'est pas sélectionné soit (OR) le film correspond au genre sélectionné
            "AND (:#{#param.genre} is NULL OR :#{#param.genre} = f.genre.id) " +
            // pareil pour tous les autres critères
            "AND (:#{#param.realisateur} is NULL OR :#{#param.realisateur} = f.realisateur.id) " +
            "AND (:#{#param.dureeMin} is NULL OR :#{#param.dureeMin} <= f.duree) " +
            "AND (:#{#param.dureeMax} is NULL OR :#{#param.dureeMax} >= f.duree) " +
            "AND (:#{#param.anneeMin} is NULL OR :#{#param.anneeMin} <= f.annee) " +
            "AND (:#{#param.anneeMax} is NULL OR :#{#param.anneeMax} >= f.annee) "
            )
    List<Film> rechercherFilms(ParametresRecherche param);
}
