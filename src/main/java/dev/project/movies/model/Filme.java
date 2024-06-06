package dev.project.movies.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String titulo;
    private String classificacaoIndicativa;

    @Column(unique = true)
    private String sinopse;

    private String dataDeLancamento;
    private Double avaliacao;
    private String poster;

    // CONSTRUTOR
    public Filme(String title, String adult, String overview, Double vote_average, String release_date, String backdrop_path) {
        this.titulo = title;
        this.classificacaoIndicativa = adult;
        this.sinopse = overview;
        this.avaliacao = vote_average;
        this.dataDeLancamento = release_date;
        this.poster = backdrop_path;
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
