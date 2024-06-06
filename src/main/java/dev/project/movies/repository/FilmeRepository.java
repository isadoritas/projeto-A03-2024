package dev.project.movies.repository;

import dev.project.movies.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// REPOSITORIO PARA ACESSAR O BANCO DE DADOS
public interface FilmeRepository extends JpaRepository<Filme, Integer> {

    Optional<Filme> findBySinopse(String titulo);

}
