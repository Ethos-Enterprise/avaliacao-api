package com.ethos.avaliacaoapi.exception;

public class AvaliacaoNaoExisteException extends RuntimeException {
    public AvaliacaoNaoExisteException(String message) {
        super(message);
    }
}
