package dev.project.movies.exception;


// Exception modelada
public class ValidacaoError extends RuntimeException {

    public ValidacaoError(String mensagem) {
        super(mensagem);
    }

}
