package fr.eni.filmotheque.repository;

import fr.eni.filmotheque.bo.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisRepository extends JpaRepository<Avis, Long> {
}
