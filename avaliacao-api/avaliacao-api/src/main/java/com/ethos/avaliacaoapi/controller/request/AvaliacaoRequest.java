package com.ethos.avaliacaoapi.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;


public record AvaliacaoRequest(
       String comentario,
        Integer nota,
        LocalDate data,
        UUID fkServico,
        UUID fkEmpresa) {

}
