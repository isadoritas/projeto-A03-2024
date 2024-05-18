package dev.project.movies.servico;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IExtrairJson {

    <T> T extrairDados(String json, Class<T> classe) throws JsonProcessingException;
}
