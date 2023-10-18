package com.ethos.avaliacaoapi.controller.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;


public record AvaliacaoRequest(
        @NotBlank
        String comentario,
        @NotNull
        @Min(1)
        @Max(5)
        Integer nota,
        LocalDate data,
        @NotNull
        UUID fkServico,
        @NotNull
        UUID fkEmpresa) {

}
