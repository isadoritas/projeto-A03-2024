package dev.project.movies.servico;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExtrairJson implements IExtrairJson{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T extrairDados(String json, Class<T> classe) throws JsonProcessingException {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
