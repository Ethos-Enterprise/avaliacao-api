package com.ethos.avaliacaoapi.exceptionhandler;

import com.ethos.avaliacaoapi.exception.AvaliacaoJaExisteException;
import com.ethos.avaliacaoapi.exception.AvaliacaoNaoExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class AvaliacaoExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        if (exception.getMessage().contains("Id inválido")) {
            problemDetail.setDetail("O Id informado é inválido");
        }
        if (exception.getMessage().contains("branco")) {
            switch (exception.getFieldError().getField()) {
                case "comentario" -> problemDetail.setDetail("O campo comentario não pode ser branco");
                case "nota" -> problemDetail.setDetail("O campo nota não pode ser branco");
                case "data" -> problemDetail.setDetail("O campo data não pode ser branco");
                case "fkEmpresa" -> problemDetail.setDetail("O campo fkEmpresa não pode ser branco");
                case "fkServico" ->
                        problemDetail.setDetail("O campo fkServico não pode ser branco");

            }
        } else if (exception.getMessage().contains("nulo") || exception.getMessage().contains("nula")) {
            switch (exception.getMessage()) {
                case "comentario" -> problemDetail.setDetail("O campo comentario social não pode ser nulo");
                case "nota" -> problemDetail.setDetail("O campo nota não pode ser nulo");
                case "data" -> problemDetail.setDetail("O campo data não pode ser nulo");
                case "fkEmpresa" -> problemDetail.setDetail("O campo fkEmpresa não pode ser nulo");
                case "fkServico" -> problemDetail.setDetail("O campo fkServico não pode ser nulo");
            }
        }
        problemDetail.setTitle("Corpo da requisição inválida");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }


    @ExceptionHandler(AvaliacaoNaoExisteException.class)
    public ProblemDetail avaliacaoNaoExisteException(AvaliacaoNaoExisteException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setTitle("Avaliação não encontrado");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(AvaliacaoJaExisteException.class)
    public ProblemDetail avaliacaoJaExisteException(AvaliacaoJaExisteException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setTitle("Avaliação já cadastrado");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}