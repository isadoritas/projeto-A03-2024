package dev.project.movies.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFilmes(@JsonAlias("title") String titulo,
                          @JsonAlias("adult") String paraMaiores,
                          @JsonAlias("overview") String sinopse,
                          @JsonAlias("release_date") String dataDeLancamento,
                          @JsonAlias("vote_average") Double avaliacao,
                          @JsonAlias("backdrop_path") String poster) {

}
