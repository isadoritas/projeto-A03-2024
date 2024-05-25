package dev.project.movies.repository;

import dev.project.movies.model.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;


public interface FilmeRepository extends JpaRepository<Filme, Integer> {

    boolean existsByTitulo(String titulo);

}
