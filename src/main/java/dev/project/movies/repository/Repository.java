package dev.project.movies.repository;

import dev.project.movies.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Repository extends JpaRepository<Filme, Integer> {

    boolean existsByTitulo(String titulo);
}
