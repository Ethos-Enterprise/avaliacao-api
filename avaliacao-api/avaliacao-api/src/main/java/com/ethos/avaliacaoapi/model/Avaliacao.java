package com.ethos.avaliacaoapi.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

public record Avaliacao(String comentario, Integer nota, LocalDate data, UUID fkEmpresa, UUID fkPrestadoraServico) {

    @Builder(toBuilder = true)
    public Avaliacao(String comentario, Integer nota, LocalDate data, UUID fkEmpresa, UUID fkPrestadoraServico) {
        this.comentario = comentario;
        this.nota = nota;
        this.data = data;
        this.fkEmpresa = fkEmpresa;
        this.fkPrestadoraServico = fkPrestadoraServico;
    }
}
