package fr.eni.filmotheque.repository;

import fr.eni.filmotheque.bo.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
