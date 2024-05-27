package dev.project.movies.repository;

import dev.project.movies.model.FilmesFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmesFavoritosRepository extends JpaRepository<FilmesFavoritos, Integer> {
}
