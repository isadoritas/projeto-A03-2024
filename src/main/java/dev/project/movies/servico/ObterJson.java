package dev.project.movies.servico;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ObterJson {

    public String obterDados(String endereco) throws IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = null;

            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            return json;
        } catch (Exception e) {
            throw new IOException("Erro ao tentar capturar a API");
        }
    }
}
