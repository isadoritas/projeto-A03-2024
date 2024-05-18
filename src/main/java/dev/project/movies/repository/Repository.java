package dev.project.movies.repository;

import dev.project.movies.model.Filmes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Filmes, Long> {
}
