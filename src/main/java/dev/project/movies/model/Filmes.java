package dev.project.movies.model;
import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
@Table(name = "filmes")
public class Filmes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String Title;

    private String Year;
    private String Released;
    private String Runtime;
    private String Genre;
    private String imdbRating;
    private String Plot;
    private String Poster;


    public Filmes() {
    }




}
