package com.ethos.avaliacaoapi.exception;

public class EmpresaNaoExisteException extends RuntimeException{
    public EmpresaNaoExisteException(String message) {
        super(message);
    }
}
