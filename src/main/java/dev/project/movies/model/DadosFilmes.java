package dev.project.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFilmes(String Title,
                          String Year,
                          String Released,
                          String Runtime,
                          String Genre,
                          String imdbRating,
                          String Plot,
                          String Poster) {

}
