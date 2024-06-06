package dev.project.movies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "filmes_favoritos")
public class FilmesFavoritos {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    String titulo;
    String classificacaoIndicativa;

    @Column(unique = true)
    String sinopse;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    String dataDeLancamento;
    Double avaliacao;
    String poster;


    // CONSTRUTOR
    public FilmesFavoritos (Filme filme) {
        this.titulo = filme.getTitulo();
        this.classificacaoIndicativa = filme.getClassificacaoIndicativa();
        this.sinopse = filme.getSinopse();
        this.avaliacao = filme.getAvaliacao();
        this.dataDeLancamento = filme.getDataDeLancamento();
        this.poster = filme.getPoster();
    }

}
