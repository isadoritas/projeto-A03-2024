package dev.project.movies.servico;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ExtrairJson implements IExtrairJson{

    private ObjectMapper mapper = new ObjectMapper();

    // TRANSFORMAR O JSON EM UMA CLASSE
    @Override
    public <T> T extrairDados(String json, Class<T> classe) throws JsonProcessingException {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao tentar converter Json em Objeto");
        }
    }

    // TRANSFORMAR O JSON EM UMA LISTA DE OBJETOS
    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {

        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);  // COLETAR E FORMAR UMA LISTA COM OS DADOS
        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao tentar converter Json em Objeto");
        }
    }


}
