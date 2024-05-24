package dev.project.movies.model;


import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(unique = true)
    String titulo;
    String classificacaoIndicativa;
    String sinopse;
    String dataDeLancamento;
    Double avaliacao;
    String poster;

    public Filme(String title, String adult, String overview, Double vote_average, String release_date, String backdrop_path) {
        this.titulo = title;
        this.classificacaoIndicativa = adult;
        this.sinopse = overview;
        this.avaliacao = vote_average;
        this.dataDeLancamento = release_date;
        this.poster = backdrop_path;
    }

    public Filme() {
    }

    @Override
    public String toString() {
        return "titulo='" + titulo +
                " classificacaoIndicativa='" + classificacaoIndicativa +
                " sinopse='" + sinopse +
                " dataDeLancamento='" + dataDeLancamento +
                " avaliacao='" + avaliacao +
                " poster='" + poster;
    }
}
