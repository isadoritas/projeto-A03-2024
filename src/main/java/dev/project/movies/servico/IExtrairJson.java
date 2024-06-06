package dev.project.movies.servico;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;


public interface IExtrairJson {

    <T> T extrairDados(String json, Class<T> classe) throws JsonProcessingException;

    <T> List<T> obterLista(String json, Class<T> classe);
}
