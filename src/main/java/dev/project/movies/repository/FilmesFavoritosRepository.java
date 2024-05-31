package dev.project.movies.repository;

import dev.project.movies.model.FilmesFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmesFavoritosRepository extends JpaRepository<FilmesFavoritos, Integer> {

    List<FilmesFavoritos> findByUsuarioId(Integer usuarioId);
}
