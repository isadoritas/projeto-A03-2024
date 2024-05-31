package dev.project.movies.infra.exception;


// EXCEPTION
public class ValidacaoError extends RuntimeException {

    public ValidacaoError(String mensagem) {
        super(mensagem);
    }

}
